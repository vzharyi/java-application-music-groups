package application;

/**
 * Клас, що представляє інформацію про гастрольну поїздку.
 */
public class TouringTrip implements Comparable<TouringTrip>{
    private String city;
    private int year;
    private int numberOfConcerts;
    /**
     * Конструктор для створення об'єкту гастрольної поїздки без параметрів.
     */
    public TouringTrip() {
    }
    /**
     * Конструктор для створення об'єкту гастрольної поїздки з параметрами.
     *
     * @param city             Місто, де відбудется гастрольна поїздка.
     * @param year             Рік гастрольної поїздки.
     * @param numberOfConcerts Кількість концертів під час поїздки.
     */
    public TouringTrip(String city, int year, int numberOfConcerts) {
        this.city = city;
        this.year = year;
        this.numberOfConcerts = numberOfConcerts;
    }
    /**
     * Повертає місто гастрольної поїздки.
     *
     * @return Місто гастрольної поїздки у вигляді рядка.
     */
    public String getCity() {
        return city;
    }
    /**
     * Встановлює місто гастрольної поїздки.
     *
     * @param city Місто гастрольної поїздки у вигляді рядка.
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Повертає рік гастрольної поїздки.
     *
     * @return Рік гастрольної поїздки у вигляді цілого значення.
     */
    public int getYear() {
        return year;
    }
    /**
     * Встановлює рік гастрольної поїздки.
     *
     * @param year Рік гастрольної поїздки у вигляді цілого значення.
     */
    public void setYear(int year) {
        this.year = year;
    }
    /**
     * Повертає кількість концертів під час гастрольної поїздки.
     *
     * @return Кількість концертів у вигляді цілого значення.
     */
    public int getNumberOfConcerts() {
        return numberOfConcerts;
    }
    /**
     * Встановлює кількість концертів під час гастрольної поїздки.
     *
     * @param numberOfConcerts Кількість концертів у вигляді цілого значення.
     */
    public void setNumberOfConcerts(int numberOfConcerts) {
        this.numberOfConcerts = numberOfConcerts;
    }
    /**
     * Перевизначений метод toString, що представляє об'єкт у рядковому вигляді.
     *
     * @return Рядкове представлення гастрольної поїздки.
     */
    @Override
    public String toString() {
        return "Місто: " + getCity() + "\tРік: " + getYear() +
                "\tКількість концертів: " + getNumberOfConcerts();
    }
    /**
     * Перевіряє, чи об'єкт гастрольної поїздки рівний іншому об'єкту.
     *
     * @param obj Інший об'єкт для порівняння.
     * @return true, якщо об'єкти рівні; false, якщо об'єкти відрізняються.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TouringTrip)) {
            return false;
        }
        TouringTrip t = (TouringTrip) obj;
        return city.equals(t.city) &&
                year == t.year && getNumberOfConcerts() == t.getNumberOfConcerts();
    }
    /**
     * Обчислює хеш-код об'єкта гастрольної поїздки.
     *
     * @return Хеш-код об'єкта.
     */
    @Override
    public int hashCode() {
        return city.hashCode() + year + getNumberOfConcerts();
    }
    /**
     * Метод для порівняння гастрольних поїздок за кількістю концертів.
     *
     * @param t Інша гастрольна поїздка для порівняння.
     * @return Від'ємне число, якщо поточна поїздка менше за іншу, додатне - якщо більше,
     *         0 - якщо рівні за кількістю концертів.
     */
    @Override
    public int compareTo(TouringTrip t) {
        return Integer.compare(getNumberOfConcerts(), t.getNumberOfConcerts());
    }
    /**
     * Метод для тестування класу TouringTrip.
     */
    public static void testTouringTrip() {
        TouringTrip touringTrip1 = new TouringTrip();
        TouringTrip touringTrip2 = new TouringTrip("Київ", 2023, 10);
        touringTrip1.setCity("Харків");
        touringTrip1.setYear(2023);
        touringTrip1.setNumberOfConcerts(5);


        System.out.println(touringTrip1);
        System.out.println(touringTrip2);
    }
    /**
     * Метод для тестування порівняння гастрольних поїздок.
     */
    public static void testEquals() {
        TouringTrip touringTrip1 = new TouringTrip("Суми", 2023, 3);
        TouringTrip touringTrip2 = new TouringTrip("Київ", 2024, 5);
        TouringTrip touringTrip3 = new TouringTrip("Суми", 2023, 3);
        System.out.println(touringTrip1);
        System.out.println(touringTrip2);
        System.out.println(touringTrip3);
        System.out.println("Гастрольна поїздка(1) рівна гастрольній поїздкі(2): " +
                touringTrip1.equals(touringTrip2));
        System.out.println("Гастрольна поїздка(1) рівна гастрольній поїздкі(3): " +
                touringTrip1.equals(touringTrip3));
        System.out.println("Гастрольна поїздка(2) рівна гастрольній поїздкі(3): " +
                touringTrip2.equals(touringTrip3));
    }
    /**
     * Метод для тестування обчислення хеш-коду гастрольної поїздки.
     */
    public static void testHashCode() {
        TouringTrip touringTrip1 = new TouringTrip("Київ", 2023, 10);
        TouringTrip touringTrip2 = new TouringTrip("Харків", 2024, 5);
        TouringTrip touringTrip3 = new TouringTrip("Київ", 2023, 10);
        TouringTrip touringTrip4 = new TouringTrip("Львів", 2023, 8);
        System.out.println(touringTrip1);
        System.out.println(touringTrip2);
        System.out.println(touringTrip3);
        System.out.println(touringTrip4);
        System.out.println("Хеш-код гастрольной поїздки(1): " + touringTrip1.hashCode());
        System.out.println("Хеш-код гастрольной поїздки(2): " + touringTrip2.hashCode());
        System.out.println("Хеш-код гастрольной поїздки(3): " + touringTrip3.hashCode());
        System.out.println("Хеш-код гастрольной поїздки(4): " + touringTrip4.hashCode());
    }
    /**
     * Метод для тестування порівняння гастрольних поїздок за кількістю концертів.
     */
    public static void testCompareTo () {
        TouringTrip touringTrip1 = new TouringTrip("Київ", 2024, 7);
        TouringTrip touringTrip2 = new TouringTrip("Харків", 2024, 1);
        TouringTrip touringTrip3 = new TouringTrip("Полтава", 2023, 3);
        System.out.println(touringTrip1);
        System.out.println(touringTrip2);
        System.out.println(touringTrip3);
        System.out.println("Гастрольну поїздку(1) порівнюємо з гастрольной поїздкой(2): " +
                touringTrip1.compareTo(touringTrip2));
        System.out.println("Гастрольну поїздку(1) порівнюємо з гастрольной поїздкой(3): " +
                touringTrip1.compareTo(touringTrip3));
        System.out.println("Гастрольну поїздку(2) порівнюємо з гастрольной поїздкой(3): " +
                touringTrip2.compareTo(touringTrip3));
    }

    public static void main(String[] args) {
        System.out.println("Тестування конструктора без параметрів та с параметрами " +
                "класу TouringTrip");
        testTouringTrip();

        System.out.println("\nТестування функції, яка порівнює гастрольні поїздки");
        testEquals();

        System.out.println("\nТестування функції, яка порівнює хеш-коди");
        testHashCode();

        System.out.println("\nТестування функції, яка визначає порядок сортування для гастрольних " +
                "поїздок за збільшенням кількості концертів");
        testCompareTo();
    }
}
