package app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CitiesController {
    Logger log = LoggerFactory.getLogger(CitiesController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @RequestMapping(value = "/getCities", method = RequestMethod.GET)
    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        jdbcTemplate.query("SELECT departure_city from buses", rs -> {
            cities.add(rs.getString("departure_city"));
        });
        return cities;
    }
}
