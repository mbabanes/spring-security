package pl.javastart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController
{
    @RequestMapping("/")
    public String home()
    {
        return "index";
    }

    @RequestMapping("/secured")
    public String secret()
    {
        return "secured";
    }

}
