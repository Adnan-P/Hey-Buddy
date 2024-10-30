package com.example.heyBuddy.repository;

import com.example.heyBuddy.model.Interest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterestRepository extends MongoRepository<Interest,Integer> {
}
