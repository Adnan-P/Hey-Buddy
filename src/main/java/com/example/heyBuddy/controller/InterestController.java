package com.example.heyBuddy.controller;

import com.example.heyBuddy.model.Interest;
import com.example.heyBuddy.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interest")
public class InterestController {

    @Autowired
    InterestService interestService;
    public Interest addInterest(@RequestBody Interest interest){
        return interestService.createInterest(interest);
    }
}
