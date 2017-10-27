package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientInfoController {


    @RequestMapping(value = "/sendClientInfo", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String getClientInfo( Object objArray) {
        System.out.println("intra in restcall");
        return "";
    }
}
