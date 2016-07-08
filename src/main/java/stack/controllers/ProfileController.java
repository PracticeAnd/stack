package stack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import stack.dao.UserDAO;
import stack.model.User;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Оля on 07.07.2016.
 */
@Controller
@RequestMapping(value = "/user")
public class ProfileController {

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/{login}", method = GET)
    public String showUserProfile(
            @PathVariable String login,
            Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute(
                    userDAO.getUserByLogin(login));
        }
        return "profile";
    }

    @RequestMapping(value = "/{login}", method = POST)
    public String saveProfile(
            @Valid User user,
            Errors errors) {
        if (errors.hasErrors()) {
            return "profile";
        }

        if (!isPasswordsEmpty(user)){
            if(!checkPasswordsUser(user)){
                return "profile";
            }
        }
            userDAO.addOrUpdateUser(user);
        return "redirect:/index";
    }

    private boolean isPasswordsEmpty(User user) {
        String newPassword = user.getNewPassword();
        String confirmPassword = user.getConfirmPassword();

        newPassword = newPassword.trim();
        confirmPassword = confirmPassword.trim();

        if (!newPassword.equals("") && !confirmPassword.equals("")) {
            return false;
        } else
            return true;
    }

    private boolean checkPasswordsUser(User user) {

        String newPassword = user.getNewPassword();
        String confirmPassword = user.getConfirmPassword();

        newPassword = newPassword.trim();
        confirmPassword = confirmPassword.trim();

        if (newPassword.equals(confirmPassword)) {
            user.setPassword(newPassword);
            user.setNewPassword("");
            user.setConfirmPassword("");
            return true;
        }
        return false;
    }

}
