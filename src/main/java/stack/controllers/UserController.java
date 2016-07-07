package stack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import stack.dao.UserDAO;
import stack.model.User;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Оля on 05.07.2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute(new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = POST)
    public String processRegistration(
            @Valid User user,
            Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }

        if(checkRegister(user.getLogin(), user.getPassword(), user.getConfirmPassword())) {
            userDAO.addOrUpdateUser(new User(user.getLogin(), user.getPassword()));
            return "index";
        } else
            return "error";

//        return "index";
        //userRepository.save(user);
        //return "redirect:/user/" + user.getUsername();
    }

    private boolean checkRegister(String login, String password, String confirmPassword) {
        List<User> list;

        list = userDAO.listOfUser();

        for(stack.model.User user : list) {
            System.out.println(user.getId());
            if((user.getLogin().equals(login)))
                return false;
        }

        if(!(password.equals(confirmPassword)))
            return false;

        return true;
    }
}
