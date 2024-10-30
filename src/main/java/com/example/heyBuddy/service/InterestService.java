package com.example.heyBuddy.service;

import com.example.heyBuddy.model.Interest;
import com.example.heyBuddy.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InterestService {

    @Autowired
    InterestRepository interestRepository;
    public Interest createInterest(Interest interest){
        return interestRepository.save(interest);
    }
}
