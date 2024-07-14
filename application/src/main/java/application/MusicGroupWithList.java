package application;

import java.util.*;

/**
 * Клас `MusicGroupWithList` розширює абстрактний клас `AbstractMusicGroup` і
 * використовує список для зберігання гастрольних поїздок.
 */
public class MusicGroupWithList extends AbstractMusicGroup {
    private List<TouringTrip> list = new ArrayList<>();
    public MusicGroupWithList() {
    }

    public MusicGroupWithList(String name, String surnameLeader) {
        super(name, surnameLeader);
    }

    /**
     * Повертає гастрольну поїздку за індексом у списку гастрольних поїздок.
     *
     * @param i Індекс гастрольної поїздки.
     * @return Гастрольна поїздка за вказаним індексом.
     */
    @Override
    public TouringTrip getTouringTrip(int i) {
        return list.get(i);
    }
    /**
     * Встановлює гастрольну поїздку за індексом у списку гастрольних поїздок.
     *
     * @param i           Індекс гастрольної поїздки.
     * @param touringTrip Гастрольна поїздка для встановлення.
     */
    @Override
    public void setTouringTrip(int i, TouringTrip touringTrip) {
        list.set(i, touringTrip);
    }

    public List<TouringTrip> getList() {
        return list;
    }

    protected void setList(List<TouringTrip> list) {
        this.list = list;
    }

    /**
     * Додає гастрольну поїздку до списку гастрольних поїздок.
     * Перевіряє, чи гастрольна поїздка вже існує в списку.
     *
     * @param touringTrip Гастрольна поїздка для додавання.
     * @return true, якщо гастрольна поїздка була успішно додана; false, якщо вона вже існує у списку.
     */
    @Override
    public boolean addTouringTrip(TouringTrip touringTrip) {
        if (list.contains(touringTrip)) {
            return false;
        }
        return list.add(touringTrip);
    }
    /**
     * Додає гастрольну поїздку з параметрами до списку гастрольних поїздок.
     *
     * @param city            Місто гастрольної поїздки.
     * @param year            Рік гастрольної поїздки.
     * @param numberOfConcerts Кількість концертів під час поїздки.
     * @return true, якщо гастрольна поїздка була успішно додана; false, якщо ні.
     */
    @Override
    public boolean addTouringTrip(String city, int year, int numberOfConcerts) {
        return addTouringTrip(new TouringTrip(city, year, numberOfConcerts));
    }
    /**
     * Повертає кількість гастрольних поїздок у списку.
     *
     * @return Кількість гастрольних поїздок.
     */
    @Override
    public int touringTripsCount() {
        return list.size();
    }
    /**
     * Очищує список гастрольних поїздок.
     */
    @Override
    public void clearTouringTrips() {
        list.clear();
    }
    /**
     * Сортує гастрольні поїздки за збільшенням кількості концертів
     * (сортування методом "бульбашки").
     */
    @Override
    public void bubbleSortByConcerts() {
        Collections.sort(list);
    }
    /**
     * Сортує гастрольні поїздки за алфавітом міста (сортування включенням).
     */
    @Override
    public void insertionSortByCity() {
        list.sort(Comparator.comparing(TouringTrip::getCity));
    }
    /**
     * Повертає масив гастрольних поїздок.
     *
     * @return Масив гастрольних поїздок.
     */
    @Override
    public TouringTrip[] getTouringTrips() {
        TouringTrip[] trips = list.toArray(new TouringTrip[0]);

        // Вывод информации о каждой поездке для проверки
        for (TouringTrip trip : trips) {
            System.out.println("Город: " + trip.getCity() + ", Год: " + trip.getYear() + ", Количество концертов: " + trip.getNumberOfConcerts());
        }

        return trips;
    }

    /**
     * Встановлює список гастрольних поїздок.
     *
     * @param touringTrips Масив гастрольних поїздок для встановлення.
     */
    @Override
    public void setTouringTrips(TouringTrip[] touringTrips) {
        list = new ArrayList<>(Arrays.asList(touringTrips));
    }
    public static void main(String[] args) {
        MusicGroupWithList musicGroup = new MusicGroupWithList();
        musicGroup.createMusicGroup();
        musicGroup.testMusicGroup();
    }
}
