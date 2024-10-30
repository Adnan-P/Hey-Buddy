package com.example.heyBuddy.repository;

import com.example.heyBuddy.enums.Role;
import com.example.heyBuddy.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person,String> {
    @Query("{'_id': ?0}")
    Optional<Person> findTravellerById(String id);

    @Query("{'role': 'BUDDY', 'interestIds': {'$in': ?0}}")
    List<Person> findBuddiesMatchingTraveller(List<Integer> interestIds);
}
