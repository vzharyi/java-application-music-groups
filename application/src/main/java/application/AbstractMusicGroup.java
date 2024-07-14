package application;

import java.util.Arrays;

/**
 * Абстрактний клас, що представляє музичну групу.
 */
public abstract class AbstractMusicGroup {
    private String name;
    private String surnameLeader;

    public AbstractMusicGroup() {
    }

    public AbstractMusicGroup(String name, String surnameLeader) {
        this.name = name;
        this.surnameLeader = surnameLeader;
    }

    /**
     * Повертає назву музичної групи.
     *
     * @return Назва музичної групи у вигляді рядка.
     */
    public String getName() {
        return name;
    }
    /**
     * Встановлює назву музичної групи.
     *
     * @param name Назва музичної групи у вигляді рядка.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Повертає прізвище керівника музичної групи.
     *
     * @return Прізвище керівника музичної групи у вигляді рядка.
     */
    public String getSurnameLeader() {
        return surnameLeader;
    }
    /**
     * Встановлює прізвище керівника музичної групи.
     *
     * @param surnameLeader Прізвище керівника музичної групи у вигляді рядка.
     */
    public void setSurnameLeader(String surnameLeader) {
        this.surnameLeader = surnameLeader;
    }
    /**
     * Абстрактний метод для отримання гастрольної поїздки за індексом.
     *
     * @param i Індекс гастрольної поїздки.
     * @return Гастрольна поїздка.
     */
    public abstract TouringTrip getTouringTrip(int i);
    /**
     * Абстрактний метод для встановлення гастрольної поїздки за індексом.
     *
     * @param i           Індекс гастрольної поїздки.
     * @param touringTrip Гастрольна поїздка.
     */
    public abstract void setTouringTrip(int i, TouringTrip touringTrip);
    /**
     * Абстрактний метод для додавання гастрольної поїздки.
     *
     * @param touringTrip Гастрольна поїздка для додавання.
     * @return true, якщо гастрольна поїздка була успішно додана; false, якщо ні.
     */
    public abstract boolean addTouringTrip(TouringTrip touringTrip);
    /**
     * Абстрактний метод для додавання гастрольної поїздки з параметрами.
     *
     * @param city            Місто гастрольної поїздки.
     * @param year            Рік гастрольної поїздки.
     * @param numberOfConcerts Кількість концертів під час поїздки.
     * @return true, якщо гастрольна поїздка була успішно додана; false, якщо ні.
     */
    public abstract boolean addTouringTrip(String city, int year, int numberOfConcerts);
    /**
     * Абстрактний метод для отримання кількості гастрольних поїздок.
     *
     * @return Кількість гастрольних поїздок.
     */
    public abstract int touringTripsCount();
    /**
     * Абстрактний метод для очищення гастрольних поїздок.
     */
    public abstract void clearTouringTrips();
    /**
     * Абстрактний метод для отримання масиву гастрольних поїздок.
     *
     * @return Масив гастрольних поїздок.
     */
    public abstract TouringTrip[] getTouringTrips();
    /**
     * Абстрактний метод для встановлення масиву гастрольних поїздок.
     *
     * @param touringTrips Масив гастрольних поїздок.
     */
    public abstract void setTouringTrips(TouringTrip[] touringTrips);
    /**
     * Додає гастрольну поїздку до масиву.
     *
     * @param arr  Початковий масив гастрольних поїздок.
     * @param item Гастрольна поїздка для додавання.
     * @return Новий масив, що містить усі гастрольні поїздки, включаючи нову.
     */
    public static TouringTrip[] addToArray(TouringTrip[] arr, TouringTrip item) {
        TouringTrip[] newArr;
        if (arr != null) {
            newArr = new TouringTrip[arr.length + 1];
            System.arraycopy(arr, 0, newArr, 0, arr.length);
        } else {
            newArr = new TouringTrip[1];
        }
        newArr[newArr.length - 1] = item;
        return newArr;
    }
    /**
     * Перевизначений метод для отримання рядкового представлення об'єкта.
     *
     * @return Рядкове представлення музичної групи та її гастрольних поїздок.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\nНазва: " + name + "Прізвище керівника: " + surnameLeader);
        for (int i = 0; i < touringTripsCount(); i++) {
            result.append("\n").append(getTouringTrip(i));
        }
        return result + "";
    }
    /**
     * Перевизначений метод для порівняння музичних груп.
     *
     * @param obj Об'єкт для порівняння.
     * @return true, якщо музичні групи ідентичні; false, якщо ні.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractMusicGroup a)) {
            return false;
        }
        return a.getName().equals(getName()) && a.getSurnameLeader().equals(getSurnameLeader());
    }
    /**
     * Перевизначений метод для обчислення хеш-коду об'єкта.
     *
     * @return Хеш-код об'єкта.
     */
    @Override
    public int hashCode() {
        return getName().hashCode() + getSurnameLeader().hashCode() + Arrays.hashCode(getTouringTrips());
    }
    /**
     * Сортує гастрольні поїздки за збільшенням кількості концертів (бульбашкове сортування).
     */
    public void bubbleSortByConcerts() {
        int n = touringTripsCount();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (getTouringTrip(j).getNumberOfConcerts() > getTouringTrip(j + 1).getNumberOfConcerts()) {
                    TouringTrip temp = getTouringTrip(j);
                    setTouringTrip(j, getTouringTrip(j + 1));
                    setTouringTrip(j + 1, temp);
                }
            }
        }
    }
    /**
     * Сортує гастрольні поїздки за алфавітом міста (сортування включенням).
     */
    public void insertionSortByCity() {
        int n = touringTripsCount();
        for (int i = 1; i < n; i++) {
            TouringTrip key = getTouringTrip(i);
            int j = i - 1;
            while (j >= 0 && getTouringTrip(j).getCity().compareTo(key.getCity()) > 0) {
                setTouringTrip(j + 1, getTouringTrip(j));
                j--;
            }
            setTouringTrip(j + 1, key);
        }
    }
    /**
     * Знаходить гастрольну поїздку з найбільшою кількістю концертів.
     *
     * @return Гастрольна поїздка з найбільшою кількістю концертів.
     */
    public TouringTrip findMaxConcertsTouringTrips() {
        TouringTrip touringTrip = getTouringTrip(0);
        for (int i = 1; i < touringTripsCount(); i++) {
            if (touringTrip.getNumberOfConcerts() < getTouringTrip(i).getNumberOfConcerts()) {
                touringTrip = getTouringTrip(i);
            }
        }
        return touringTrip;
    }
    /**
     * Знаходить гастрольні поїздки до вказаного міста.
     *
     * @param targetCity Місто, до якого потрібно знайти гастрольні поїздки.
     * @return Масив гастрольних поїздок до вказаного міста.
     */
    public TouringTrip[] findTouringTripsInCity(String targetCity) {
        TouringTrip[] result = null;
        for (TouringTrip trip : getTouringTrips()) {
            if (trip.getCity().equals(targetCity)) {
                result = addToArray(result, trip);
            }
        }
        return result;
    }
    /**
     * Виводить список гастрольних поїздок до вказаного міста на екран.
     *
     * @param city Назва міста, для якого виводиться список гастрольних поїздок.
     */
    private void printWord(String city) {
        TouringTrip[] result = findTouringTripsInCity(city);
        if (result == null) {
            System.out.println("\nСписок гастрольних поїздок у місто (" + city + ") - немає даних");
        }
        else {
            System.out.println("\nСписок гастрольних поїздок у місто (" + city + "): ");
            for (TouringTrip t : result) {
                System.out.println(t);
            }
        }
    }
    /**
     * Знаходить останню букву в прізвищі керівника групи.
     *
     * @return Остання буква в прізвищі керівника групи або null, якщо прізвище порожнє.
     */
    public Character findLastSurnameLetterForGroup() {
        String surnameLeader = getSurnameLeader();
        if (surnameLeader != null && !surnameLeader.isEmpty()) {
            return surnameLeader.charAt(surnameLeader.length() - 1);
        } else {
            return null;
        }
    }
    /**
     * Допоміжна функція створення нового об'єкта "Музикальна група"
     * @return посилання на новий об'єкт "Музикальна група"
     */
    public AbstractMusicGroup createMusicGroup() {
        setName("Сонячні акорди\n");
        setSurnameLeader("Чередниченко");
        System.out.println(addTouringTrip("Харків", 2022, 5));
        System.out.println(addTouringTrip("Одеса", 2023, 2));
        System.out.println(addTouringTrip("Полтава", 2024, 10));
        System.out.println(addTouringTrip("Львів", 2026, 4));
        System.out.println(addTouringTrip("Житомир", 2025, 15));
        return this;
    }
    /**
     * Здійснює тестування методів класу
     */
    public void testMusicGroup() {
        System.out.println("\nГастрольна поїздка з макс кількістю концертів: \n" + findMaxConcertsTouringTrips());
        printWord("Харків");

        bubbleSortByConcerts();
        System.out.println("\nСортування за збільшенням кількості концертів: ");
        System.out.print("Остання буква керівника групи: " + findLastSurnameLetterForGroup());
        System.out.println(this);

        insertionSortByCity();
        System.out.println("\nСортування за Алфавітом міста: ");
        System.out.print("Остання буква керівника групи: " + findLastSurnameLetterForGroup());
        System.out.println(this);
    }
}
