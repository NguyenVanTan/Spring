package tannv.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by nguyen.van.tan on 9/1/16.
 */
@Controller
public class ViewBookingController {


    @RequestMapping("/")
    public  String index(Model model)
    {   model.addAttribute("datetime",new Date() );
        model.addAttribute("username","David black");

        return "index";
    }

}
