package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.data.MealData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealDTO;
import ru.javawebinar.topjava.repositories.MealRepository;
import ru.javawebinar.topjava.repositories.api.Repository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final int CALORIES_THRESHOLD = 2000;
    private static final String CREATE_ACTION = "create";
    private static final String DELETE_ACTION = "delete";
    private static final String UPGRADE_ACTION = "upgrade";
    private static final String CREATE_UPGRADE_MEAL_PATH = "/mealCreateUpgrade.jsp";
    private static final String MEAL_LIST_PATH = "/meals.jsp";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    private Repository<Meal> mealRepository;

    @Override
    public void init() throws ServletException {
        mealRepository = new MealRepository();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            req.setAttribute("meals", MealsUtil.convertMealsToDto( MealData.getMeals(), CALORIES_THRESHOLD));
            req.getRequestDispatcher(MEAL_LIST_PATH).forward(req, resp);
        } else {
            RequestDispatcher requestDispatcher = null;
            switch (action){
                case CREATE_ACTION:
                    requestDispatcher = req.getRequestDispatcher(CREATE_UPGRADE_MEAL_PATH);
                    break;
                case UPGRADE_ACTION:
                    requestDispatcher = req.getRequestDispatcher(CREATE_UPGRADE_MEAL_PATH);
                    break;
                case DELETE_ACTION:
                    requestDispatcher = req.getRequestDispatcher(MEAL_LIST_PATH);
                    int mealId = Integer.parseInt(req.getParameter("mealId"));
                    mealRepository.deleteById(mealId);
                    req.setAttribute("meals", MealsUtil.convertMealsToDto(mealRepository.getAll(), CALORIES_THRESHOLD));
                    break;
                default:
                    break;
            }
            requestDispatcher.forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mealRepository.create(new Meal(
                MealRepository.mealCounter++,
                LocalDateTime.parse(req.getParameter("dateTime"), formatter),
                req.getParameter("description"), Integer.parseInt(req.getParameter("calories"))));
        req.setAttribute("meals", MealsUtil.convertMealsToDto(mealRepository.getAll(), CALORIES_THRESHOLD));
        req.getRequestDispatcher(MEAL_LIST_PATH).forward(req, resp);
    }
}
