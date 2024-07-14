package application;

import static application.DbUtils.*;

public class DBProgram {
    public static void main(String[] args) {
        MusicGroups musicGroups = createMusicGroups();
        exportToJSON(musicGroups, "src/main/resources/application/MusicGroups.json");
        musicGroups = importFromJSON("src/main/resources/application/MusicGroups.json");
        createConnection();
        if (createDatabase()) {
            addAll(musicGroups);
            showAll();
            System.out.println("\nМузичні групи за зростанням року:");
            showSortedByYear("Сонячні акорди ");
            addMusicGroup(new MusicGroupForDB("Місячні", "Жарий"));
            addTouringTrip("Місячні", new TouringTripForDB("Київ", 2024, 20));
            showAll();
            exportToJSON("src/main/resources/application/MusicGroupsFromDB.json");
            closeConnection();
        }
    }

    static MusicGroups createMusicGroups() {
        MusicGroupForDB musicGroupForDB = new MusicGroupForDB();
        musicGroupForDB.setName("Сонячні акорди ");
        musicGroupForDB.setSurnameLeader("Жарий");
        musicGroupForDB.addTouringTrip(new TouringTripForDB("Харків", 2022, 5));
        musicGroupForDB.addTouringTrip(new TouringTripForDB("Одеса", 2023, 2));
        musicGroupForDB.addTouringTrip(new TouringTripForDB("Полтава", 2024, 10));
        musicGroupForDB.addTouringTrip(new TouringTripForDB("Львів", 2025, 4));
        musicGroupForDB.addTouringTrip(new TouringTripForDB("Житомир", 2026, 15));
        MusicGroups musicGroups = new MusicGroups();
        musicGroups.getList().add(musicGroupForDB);
        return musicGroups;
    }
}
