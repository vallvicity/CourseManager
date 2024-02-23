package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String homepage(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
//        model.addAttribute("user", )
        return "home";
    }

    @RequestMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/users/new")
    public String createUserForm(Model model) {
        UserEntity userEntity = new UserEntity();
        model.addAttribute("user", userEntity);
        return "create_user";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return "home";
    }

}
