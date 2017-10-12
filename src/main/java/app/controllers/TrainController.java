package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.beans.TrainBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrainController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @RequestMapping(value = "/getTrainsPerCity", method = RequestMethod.GET)
    public List<TrainBean> getCities(@RequestParam(value="city") String city) {
        List<TrainBean> trainsFromCertainCity = new ArrayList<>();
        //public TrainBean(String name, String departureCity, String arrivalCity, LocalDate date, LocalTime departureTime, float price ) {
        jdbcTemplate.query("SELECT * from buses WHERE departure_city = '" + city +"';", rs -> {
            TrainBean train = new TrainBean(
                    rs.getString("name"),
                    rs.getString("departure_city"),
                    rs.getString("arrival_city"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("departure_time").toLocalTime(),
                    rs.getFloat("price")
            );
            trainsFromCertainCity.add(train);
        });
        return trainsFromCertainCity;
    }
}
