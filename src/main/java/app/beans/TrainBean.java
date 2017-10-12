package app.beans;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class TrainBean {
    private int id;
    private String name;
    private String departureCity;
    private String arrivalCity;
    private Date date;
    private Time departureTime;
    private int nrSeats;
    private int nrSeatsAvailable;
    private float price;

    public TrainBean(int id, String name, String departureCity, String arrivalCity, LocalDate date, LocalTime departureTime, int nrSeats, int nrSeatsAvailable, float price) {
        this.id = id;
        this.name = name;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.date = Date.valueOf(date);
        this.departureTime = Time.valueOf(departureTime);
        this.nrSeats = nrSeats;
        this.nrSeatsAvailable = nrSeatsAvailable;
        this.price = price;
    }
    public TrainBean(String name, String departureCity, String arrivalCity, LocalDate date, LocalTime departureTime, float price ) {
        this.name = name;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.date = Date.valueOf(date);
        this.departureTime = Time.valueOf(departureTime);
        this.price = price;
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
        return date.toLocalDate();
    }

    public LocalTime getDepartureTime() {
        return departureTime.toLocalTime();
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
