package com.example.helloword.controller;

import com.example.helloword.model.Admin;
import com.example.helloword.model.User;
import com.example.helloword.repository.AdminRepository;
import com.example.helloword.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public UserController(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @GetMapping("/userLogin")
    public String userLogin() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Dashboard";
    }

    @PostMapping("/userLogin")
    public String userlogin(@RequestParam String email,
            @RequestParam String password,
            HttpSession session, RedirectAttributes redirAttrs) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("role", "user");
            session.setAttribute("email", email);
            redirAttrs.addFlashAttribute("success", "Hello " + user.getName() + ", how are you? Now you can access.");
            return "redirect:/dashboard";
        } else {
            redirAttrs.addFlashAttribute("error", "Email or password incorrect.");
            return "redirect:/";
        }
    }

    @GetMapping("/adminLogin")
    public String adminLogin() {
        return "adminLogin";
    }

    @GetMapping("/adminIndex")
    public String adminIndex() {
        return "adminIndex";
    }
    @GetMapping("/UserRegister")
    public String UserRegister() {
        return "UserRegister";
    }
   @PostMapping("/registerUser")
public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirAttrs) {
    userRepository.save(user);
    redirAttrs.addFlashAttribute("success", "User registered successfully.");
    return "redirect:/userLogin";
}

    @PostMapping("/adminLogin")
    public String adminloginaction(@RequestParam String email,
            @RequestParam String password,
            HttpSession session, RedirectAttributes redirAttrs) {
        Admin user = adminRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("role", "admin");
            session.setAttribute("email", email);
            redirAttrs.addFlashAttribute("success",
                    "Hello " + user.getUsername() + ", how are you? Now you can access.");
            return "redirect:/adminIndex";
        } else {
            redirAttrs.addFlashAttribute("error", "Email or password incorrect.");
            return "redirect:/";
        }
    }

    @GetMapping("/adminlogout")
    public String logout(HttpSession session, RedirectAttributes redirAttrs) {
        session.invalidate();
        redirAttrs.addFlashAttribute("success", "You have been logged out successfully.");
        return "redirect:/";
    }

    @GetMapping("/userprofile")
    public String profile(HttpSession session, Model model, RedirectAttributes redirAttrs) {
        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");

        if (email != null && "user".equals(role)) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                model.addAttribute("user", user);
                return "userProfile";
            } else {
                redirAttrs.addFlashAttribute("error", "User not found.");
                return "redirect:/userLogin";
            }
        } else {
            redirAttrs.addFlashAttribute("error", "Please log in as a user.");
            return "redirect:/userLogin";
        }
    }

}
