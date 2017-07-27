package com.oh.spring.controller;

import com.oh.spring.entity.User;
import com.oh.spring.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author cho.oh
 */

//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public Object createUser(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) throws NotFoundException {
        User user = new User();
        //TODO Validation

        if (Pattern.matches("^[0-9a-zA-Z_]+$", username) && email.split("@").length == 2 && password.length() > 7) {

            user.setUsername(username);
            String passwordHash = passwordEncoder.encode(password);
            user.setPassword(passwordHash);
            user.setEmail(email);

            userRepository.save(user);
            Map<String, Object> result = new HashMap<>();
            // result.put("userId", user.getUserId());
            result.put("serverMsg", "Success");
            return result;
        }
        throw new NotFoundException("Create User Error");
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        System.out.println("logout");
        session.invalidate();
    }
}
