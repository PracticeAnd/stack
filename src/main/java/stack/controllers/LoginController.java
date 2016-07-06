package stack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import stack.model.User;
import stack.dao.UserDAO;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/")
public class LoginController {
//
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String showLogin(User user) {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String checkInputLoginDates(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginForm(@Valid User user,
                            final BindingResult errors) {
        if (errors.hasErrors()) {
            return "login";
        }

        if(checkLogin(user.getLogin())) {
            return "index";
        } else
            return "error";
    }

    public boolean checkLogin(String name) {
        List<stack.model.User> list;

        list = userDAO.listOfUser();

        for(stack.model.User user : list) {
            System.out.println(user.getId());
            if(user.getLogin().equals(name))
                return true;
        }
        return false;
    }
}
