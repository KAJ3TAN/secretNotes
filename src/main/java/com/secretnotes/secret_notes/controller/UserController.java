/*Zarządza rejestracją użytkowników: wyświetla formularz rejestracji, przetwarza dane z walidacją, sprawdza unikalność nazwy użytkownika
 i zapisuje nowych użytkowników. Dodatkowo obsługuje wyświetlanie strony logowania. */

package com.secretnotes.secret_notes.controller;
import com.secretnotes.secret_notes.model.User;
import com.secretnotes.secret_notes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") User user,
                                      BindingResult bindingResult,
                                      Model model) {


        if (bindingResult.hasErrors()) {
            return "register";
        }


        if (userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.user", "Nazwa użytkownika jest zajęta.");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
