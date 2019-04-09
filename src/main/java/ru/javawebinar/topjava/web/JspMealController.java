package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping(value = {"", "/", "/all"})
    public String getAll(Model model) {
        model.addAttribute("meals", mealService.getAll(SecurityUtil.authUserId()));
        return "meals";
    }

    @PostMapping("/upgrade")
    public String upgrade() {

        return "meals";
    }

    @PostMapping("/create")
    public String create() {
        return "meals";
    }

    @PostMapping("/filter")
    public String filter(HttpServletRequest req) {
        return "meals";
    }

    public String delete() {
        return "meals";
    }



}
