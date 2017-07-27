package com.oh.spring.controller;

import com.oh.spring.entity.Target;
import com.oh.spring.repository.TargetRepository;
import com.oh.spring.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class TargetRestController {
    @Autowired
    private TargetRepository targetRepository;

    @GetMapping("/getTarget")
    public Target getTarget(@AuthenticationPrincipal LoginUser loginUser) {
        int userId = loginUser.getLoginUserId();
        Target target = targetRepository.findByCreator_UserId(userId);
        return target;
    }

    @PostMapping("/addTarget")
    public String addTarget(@AuthenticationPrincipal LoginUser loginUser, @RequestParam("finalTarget") String finalTarget) {
        int userId = loginUser.getLoginUserId();
        Target targetEntity = new Target();

        Target target = targetRepository.findByCreator_UserId(userId);
        return "SUCCESS";
    }

}
