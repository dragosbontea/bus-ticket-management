package app.controllers;

import app.models.Train;
import app.models.TrainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainController {

    @Autowired
    TrainDao trainDao;

    @RequestMapping(value = "/testDBConnection")
    public void testDB() {
        Train train = new Train("dragos", "Bucuresti");
        trainDao.create(train);
    }

    @RequestMapping(value = "/train")
    @ResponseBody
    public String getTrain() {
        Train byName = trainDao.getByName("dragos");
        return byName.toString();
    }


}
