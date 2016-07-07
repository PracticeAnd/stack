package stack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import stack.dao.UserDAO;
import stack.model.User;

/**
 * Created by Оля on 07.07.2016.
 */
@Controller
public class IndexController {

    @Autowired
    UserDAO userDAO;
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showIndex(User user, Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute(
                    userDAO.getUserByLogin(user.getLogin()));
        }
        return "index";
    }
}
