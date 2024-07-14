package application;

import java.util.ArrayList;
import java.util.List;

public class MusicGroups {
    private List<MusicGroupForDB> list = new ArrayList<>();

    public List<MusicGroupForDB> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
