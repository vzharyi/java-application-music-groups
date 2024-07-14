package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MusicGroupWithStreams extends MusicGroupWithList {
    public MusicGroupWithStreams() {
    }

    public MusicGroupWithStreams(String name, String surnameLeader, List<TouringTrip> list) {
        super(name, surnameLeader);
        setList(list);
    }

    @Override
    public void setList(List<TouringTrip> list) {
        super.setList(list.stream()
                .distinct()
                .collect(Collectors.toList()));
    }

    @Override
    public void bubbleSortByConcerts() {
        setList(getList().stream()
                .sorted(Comparator.comparing(TouringTrip::getNumberOfConcerts))
                .collect(Collectors.toList()));
    }

    @Override
    public void insertionSortByCity() {
        setList(getList().stream()
                .sorted(Comparator.comparing(TouringTrip::getCity))
                .collect(Collectors.toList()));
    }

    public List<String> toListOfStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getName() + " " + getSurnameLeader());
        Arrays.stream(getTouringTrips()).forEach(t -> list.add(
                t.getCity() + " " + t.getYear() + " " + t.getNumberOfConcerts()));
        return list;
    }

    public void fromListOfStrings(List<String> list) {
        String[] words = list.get(0).split("\\s+");
        setName(words[0]);
        setSurnameLeader(words[1]);
        list.remove(0);
        list.stream().forEach(s -> { String[] line = s.split("\s");
            addTouringTrip(line[0], Integer.parseInt(line[1]),
                    Integer.parseInt(line[2])); });
    }

    public static MusicGroupWithStreams createMusicGroupWithStreams() {
        MusicGroupWithStreams musicGroupWithStreams = new MusicGroupWithStreams();
        musicGroupWithStreams.setName("Сонячні");
        musicGroupWithStreams.setSurnameLeader("Чередниченко");
        musicGroupWithStreams.addTouringTrip("Харків", 2022, 5);
        musicGroupWithStreams.addTouringTrip("Одеса", 2023, 2);
        musicGroupWithStreams.addTouringTrip("Полтава", 2024, 10);
        musicGroupWithStreams.addTouringTrip("Львів", 2026, 4);
        musicGroupWithStreams.addTouringTrip("Житомир", 2025, 15);
        return musicGroupWithStreams;
    }
}
