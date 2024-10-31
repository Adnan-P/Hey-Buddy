package com.example.heyBuddy.repository;

import com.example.heyBuddy.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    @Query("{'_id': ?0}")
    Optional<User> findTravellerById(String id);

    @Query("{'role': 'BUDDY', 'interestIds': {'$in': ?0}}")
    List<User> findBuddiesMatchingTraveller(List<Integer> interestIds);
}
