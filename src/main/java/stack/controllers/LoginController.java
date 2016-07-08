package stack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import stack.model.User;
import stack.dao.UserDAO;

import javax.validation.Valid;
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

    @RequestMapping(value="/", method = RequestMethod.POST)
    public String checkInputLoginDates(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        } else
        return "redirect:/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginForm(@Valid User user,
                            final BindingResult errors) {
//        if (!errors.hasErrors()) {
//            return "login";
//        }

        if(checkLoginAndPassword(user.getLogin(), user.getPassword())) {
            return "index";
        } else
            return "error";
    }

    public boolean checkLoginAndPassword(String name, String password) {
        List<User> list;

        list = userDAO.listOfUser();

        for(User user : list) {
            if((user.getLogin().equals(name)) && (user.getPassword().equals(password)))
                return true;
        }
        return false;
    }
}
