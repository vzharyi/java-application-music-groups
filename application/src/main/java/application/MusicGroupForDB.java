package application;

public class MusicGroupForDB extends MusicGroupWithStreams{
    private long id = -1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MusicGroupForDB() {
    }

    public MusicGroupForDB(String name, String surnameLeader) {
        setName(name);
        setSurnameLeader(surnameLeader);
    }
}
