package application;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class DbUtils {
    // Enum for sorting options
    public enum Show { SORTED, UNSORTED }

    // SQL queries
    public static final String DROP_TABLES = "DROP TABLE IF EXISTS touringTrips, musicGroups";
    public static final String DROP_DATABASE = "DROP DATABASE IF EXISTS musicGroupsDB";
    public static final String CREATE_DATABASE = "CREATE DATABASE musicGroupsDB";
    public static final String CREATE_TABLE_MUSIC_GROUPS = """
            CREATE TABLE musicGroupsDB.musicGroups (
              MusicGroupID INT NOT NULL AUTO_INCREMENT,
              Name VARCHAR(128) NULL,
              SurnameLeader VARCHAR(128) NULL,
              PRIMARY KEY (MusicGroupID));
            """;
    public static final String CREATE_TABLE_TOURING_TRIPS = """
            CREATE TABLE musicGroupsDB.touringTrips (
              TouringTripID INT NOT NULL AUTO_INCREMENT,
              City VARCHAR(256) NULL,
              Year INT NULL,
              MusicGroupID INT NULL,
              NumberOfConcerts INT NULL,
              PRIMARY KEY (TouringTripID),
              INDEX MusicGroupID_idx (MusicGroupID ASC) VISIBLE,
              CONSTRAINT MusicGroupID
                FOREIGN KEY (MusicGroupID)
                REFERENCES musicGroupsDB.musicGroups (MusicGroupID)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION);
            """;
    private static final String INSERT_INTO_MUSIC_GROUPS = """
        INSERT INTO musicGroupsDB.musicGroups (Name, SurnameLeader) VALUES (?, ?);
        """;
    private static final String INSERT_INTO_TOURING_TRIPS = """
        INSERT INTO musicGroupsDB.touringTrips (City, Year, MusicGroupID, NumberOfConcerts) VALUES (?, ?, ?, ?);
        """;
    private static final String SELECT_BY_NAME = "SELECT * FROM musicGroupsDB.musicGroups WHERE Name = ?";
    private static final String SELECT_ALL_MUSIC_GROUPS = "SELECT * FROM musicGroupsDB.musicGroups";
    private static final String SELECT_FROM_TOURING_TRIPS = "SELECT * FROM musicGroupsDB.touringTrips WHERE MusicGroupID = ?";
    private static final String SELECT_FROM_TOURING_TRIPS_ORDER_BY_YEAR =
            "SELECT * FROM musicGroupsDB.touringTrips WHERE MusicGroupID = ? ORDER BY Year";

    private static final String DELETE_BY_YEAR = "DELETE FROM musicGroupsDB.touringTrips WHERE MusicGroupID=? AND Year=?";

    private static Connection connection;

    public static MusicGroups importFromJSON(String fileName) {
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.addPermission(AnyTypePermission.ANY);
            xStream.alias("musicGroups", MusicGroups.class);
            xStream.alias("musicGroup", MusicGroupForDB.class);
            xStream.alias("touringTrip", TouringTripForDB.class);
            return (MusicGroups) xStream.fromXML(new File(fileName));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void exportToJSON(MusicGroups musicGroups, String fileName) {
        XStream xStream = new XStream(new JettisonMappedXmlDriver());
        xStream.alias("musicGroups", MusicGroups.class);
        xStream.alias("musicGroup", MusicGroupForDB.class);
        xStream.alias("touringTrip", TouringTripForDB.class);
        String xml = xStream.toXML(musicGroups);
        try (FileWriter fw = new FileWriter(fileName); PrintWriter out = new PrintWriter(fw)) {
            out.println(xml);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void exportToJSON(String fileName) {
        MusicGroups musicGroups = getMusicGroupsFromBD();
        exportToJSON(musicGroups, fileName);
    }

    public static void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user=root&password=Misterlom177");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createDatabase() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DROP_TABLES);
            statement.executeUpdate(DROP_DATABASE);
            statement.executeUpdate(CREATE_DATABASE);
            statement.executeUpdate(CREATE_TABLE_MUSIC_GROUPS);
            statement.executeUpdate(CREATE_TABLE_TOURING_TRIPS);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public static void addAll(MusicGroups musicGroups) {
        for (MusicGroupForDB c : musicGroups.getList()) {
            addMusicGroup(c);
        }
    }

    public static void addMusicGroup(MusicGroupForDB musicGroupForDB) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_MUSIC_GROUPS, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, musicGroupForDB.getName());
            preparedStatement.setString(2, musicGroupForDB.getSurnameLeader());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int musicGroupID = generatedKeys.getInt(1);
                musicGroupForDB.setId(musicGroupID);

                for (int i = 0; i < musicGroupForDB.touringTripsCount(); i++) {
                    addTouringTrip(musicGroupID, (TouringTripForDB) musicGroupForDB.getTouringTrip(i));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MusicGroups getMusicGroupsFromBD() {
        try {
            MusicGroups musicGroups = new MusicGroups();
            Statement statement = connection.createStatement();
            ResultSet setOfMusicGroups = statement.executeQuery(SELECT_ALL_MUSIC_GROUPS);
            while (setOfMusicGroups.next()) {
                musicGroups.getList().add(getMusicGroupFromDB(setOfMusicGroups));
            }
            return musicGroups;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MusicGroupForDB getMusicGroupByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet setOfMusicGroups = preparedStatement.executeQuery();
            if (setOfMusicGroups.next()) {
                return getMusicGroupFromDB(setOfMusicGroups);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MusicGroupForDB getMusicGroupFromDB(ResultSet setOfMusicGroup) throws SQLException {
        MusicGroupForDB musicGroupForDB = new MusicGroupForDB(setOfMusicGroup.getString("Name"), setOfMusicGroup.getString("SurnameLeader"));
        int id = setOfMusicGroup.getInt("MusicGroupID");
        musicGroupForDB.setId(id);

        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_TOURING_TRIPS);
        preparedStatement.setInt(1, id);
        ResultSet setOfTouringTrips = preparedStatement.executeQuery();
        while (setOfTouringTrips.next()) {
            TouringTripForDB touringTripForDB = new TouringTripForDB(setOfTouringTrips.getString("City"),
                    setOfTouringTrips.getInt("Year"), setOfTouringTrips.getInt("NumberOfConcerts"));
            touringTripForDB.setId(setOfTouringTrips.getInt("TouringTripID"));
            musicGroupForDB.addTouringTrip(touringTripForDB);
        }
        return musicGroupForDB;
    }

    public static int getIdByName(String musicGroupName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, musicGroupName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("MusicGroupID");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MUSIC_GROUPS);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> names = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                names.add(name);
            }
            resultSet.close();
            for (String name : names) {
                showMusicGroup(name, Show.UNSORTED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showMusicGroup(String musicGroupName, Show byYear) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, musicGroupName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MusicGroupForDB musicGroup = getMusicGroupFromDB(resultSet);
                System.out.println(musicGroup.toString());

                if (byYear == Show.SORTED) {
                    preparedStatement = connection.prepareStatement(SELECT_FROM_TOURING_TRIPS_ORDER_BY_YEAR);
                } else {
                    preparedStatement = connection.prepareStatement(SELECT_FROM_TOURING_TRIPS);
                }
                preparedStatement.setLong(1, musicGroup.getId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    TouringTripForDB touringTripForDB = new TouringTripForDB(resultSet.getString("City"), resultSet.getInt("Year"),
                            resultSet.getInt("NumberOfConcerts"));
                    touringTripForDB.setId(resultSet.getInt("TouringTripID"));
                    System.out.println(touringTripForDB.toString());
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void showSortedByYear(String musicGroupName) {
        showMusicGroup(musicGroupName, Show.SORTED);
    }

    public static void addTouringTrip(String musicGroupName, TouringTripForDB touringTripForDB) {
        int musicGroupID = getIdByName(musicGroupName);
        addTouringTrip(musicGroupID, touringTripForDB);
    }

    public static void addTouringTrip(int musicGroupID, TouringTripForDB touringTripForDB) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TOURING_TRIPS);
            preparedStatement.setString(1, touringTripForDB.getCity());
            preparedStatement.setInt(2, touringTripForDB.getYear());
            preparedStatement.setInt(3, musicGroupID);
            preparedStatement.setInt(4, touringTripForDB.getNumberOfConcerts());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeTouringTrip(String musicGroupName, int year) {
        int musicGroupID = getIdByName(musicGroupName);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_YEAR);
            preparedStatement.setInt(1, musicGroupID);
            preparedStatement.setInt(2, year);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
