package app;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import app.beans.TrainBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Configuration
public class Application implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final String BUS_TABLE = "buses";

    private List<TrainBean> busses = new ArrayList<>();
    private AtomicInteger id = new AtomicInteger(0);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        initDB();
    }

    private static List<Object[]> busListAsObjects(Collection<TrainBean> busses) {
        return busses.stream().map(
                bus -> new Object[]{bus.getId(), bus.getName(), bus.getDepartureCity(), bus.getArrivalCity(), Date.valueOf(bus.getDate()),
                        Time.valueOf(bus.getDepartureTime()), bus.getNrSeats(), bus.getNrSeatsAvailable(), bus.getPrice()})
                .collect(Collectors.toList());
    }

    private void initDB() {
        TrainBean busBucurestiToBacau = new TrainBean(id.getAndIncrement(), "InterCity", "Bucuresti", "Bacau", LocalDate.of(2017, 9, 8), LocalTime.of(18, 25), 100, 100, 78);
        TrainBean busBacauToBucuresti = new TrainBean(id.getAndIncrement(), "InterCity", "Bacau", "Bucuresti", LocalDate.of(2017, 9, 10), LocalTime.of(18, 0), 100, 100, 78);
        TrainBean busBucurestiToConstanta = new TrainBean(id.getAndIncrement(), "InterCity", "Bucuresti", "Constanta", LocalDate.of(2017, 9, 10), LocalTime.of(18, 0), 100, 100, 78);
        TrainBean busConstantaToBucuresti = new TrainBean(id.getAndIncrement(), "InterCity", "Constanta", "Bucuresti", LocalDate.of(2017, 9, 10), LocalTime.of(18, 0), 100, 100, 78);
        busses.add(busBacauToBucuresti);
        busses.add(busBucurestiToBacau);
        busses.add(busConstantaToBucuresti);
        busses.add(busBucurestiToConstanta);

        log.info("Creating tables");

        //create the buses table
        jdbcTemplate.execute("DROP TABLE " + BUS_TABLE + " IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE " + BUS_TABLE + " (" +
                "id SERIAL, " +
                "name VARCHAR(255), " +
                "departure_city VARCHAR(255), " +
                "arrival_city VARCHAR(255), " +
                "date DATE, " +
                "departure_time TIME, " +
                "total_seats INT, " +
                "available_seats INT, " +
                "price FLOAT" +
                ")");

        //add 2 lines for now

        jdbcTemplate.batchUpdate("INSERT INTO " + BUS_TABLE +
                        " (id, name, departure_city, arrival_city, date, departure_time, total_seats, available_seats, price) VALUES (?,?,?,?,?,?,?,?,?)",
                busListAsObjects(busses));

        jdbcTemplate.query("SELECT id ,name, date, departure_time from " + BUS_TABLE,
                rs -> {
                    log.info("Here is your " + rs.getRow() + "entry found: " + rs.getInt("id") + " " + rs.getString("name") + " " + rs.getDate("date") + " " + rs.getTime("departure_time"));
                });
    }

}