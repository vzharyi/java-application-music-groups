package application;

public class TouringTripForDB extends TouringTrip{
    private long id = -1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TouringTripForDB() {
    }

    public TouringTripForDB(String city, int year, int numberOfConcerts) {
        super(city, year, numberOfConcerts);
    }
}
