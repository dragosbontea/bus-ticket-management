package app.beans;

import java.time.LocalDate;
import java.time.LocalTime;

public class BusBean {
    private int id;
    private String name;
    private String departureCity;
    private String arrivalCity;
    private LocalDate date;
    private LocalTime departureTime;
    private int nrSeats;
    private int nrSeatsAvailable;
    private float price;

    public BusBean(int id, String name, String departureCity, String arrivalCity, LocalDate date, LocalTime departureTime, int nrSeats, int nrSeatsAvailable, float price) {
        this.id = id;
        this.name = name;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.date = date;
        this.departureTime = departureTime;
        this.nrSeats = nrSeats;
        this.nrSeatsAvailable = nrSeatsAvailable;
        this.price = price;
    }
    public BusBean(int id, String name, String departureCity) {
        this.id = id;
        this.name = name;
        this.departureCity = departureCity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public int getNrSeats() {
        return nrSeats;
    }

    public int getNrSeatsAvailable() {
        return nrSeatsAvailable;
    }

    public float getPrice() {
        return price;
    }
}
