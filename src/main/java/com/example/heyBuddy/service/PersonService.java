package com.example.heyBuddy.service;

import com.example.heyBuddy.dto.PersonDTO;
import com.example.heyBuddy.enums.Role;
import com.example.heyBuddy.model.Interest;
import com.example.heyBuddy.model.Person;
import com.example.heyBuddy.repository.InterestRepository;
import com.example.heyBuddy.repository.PersonRepository;
import com.example.heyBuddy.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public ResponseEntity<String> createPerson(PersonDTO personDTO) {
        try {
            personRepository.save(personDtoToPersonEntity(personDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
        } catch(Exception e){

        return
                 ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("creation Unsuccessfully");
        }
    }


    private Person personDtoToPersonEntity(PersonDTO personDTO) {
        return new Person(personDTO.getFirstName(), personDTO.getLastName(),
                personDTO.getEmail(), personDTO.getPhoneNumber(), personDTO.getAadharNumber(), personDTO.getLocation(),
                personDTO.getRole(), Util.getCurrentDate(), personDTO.getInterestIds());
    }


    //list all travellers
    public List<PersonDTO> getAllTravellers(){
        return personRepository.findAll().stream()
                .filter(person -> person.getRole() == Role.TRAVELLER)
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }


    //list all buddies
    public List<PersonDTO> getAllBuddies(){
        return personRepository.findAll().stream()
                .filter(person -> person.getRole() == Role.BUDDY)
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }




    private PersonDTO convertToPersonDTO(Person person){
        return new PersonDTO(
                person.getId(),
                 person.getFirstName(), person.getLastName(), person.getEmail(), person.getPhoneNumber(), person.getAadharNumber(),
                person.getLocation(), person.getRole(), person.getCreatedDate(), person.getInterestIds()

        );
    }


    //getTravellerById Logic
    public PersonDTO getTravellerById(String id) {
        Optional<Person> personOpt = personRepository.findTravellerById(id);

        if (personOpt.isPresent()) {
            Person person = personOpt.get();
            return new PersonDTO(
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getEmail(),
                    person.getPhoneNumber(),
                    person.getAadharNumber(),
                    person.getLocation(),
                    person.getRole(),
                    person.getCreatedDate(),
                    person.getInterestIds()
            );
        } else {
            // Handle the case where the traveller is not found (e.g., throw an exception)
            return null;
        }
    }




    public List<PersonDTO> getMatchingBuddiesWithPercentage(String travellerId) {
        Optional<Person> travellerOpt = personRepository.findTravellerById(travellerId);

        if (travellerOpt.isPresent()) {
            Person traveller = travellerOpt.get();
            List<Integer> travellerInterests = traveller.getInterestIds();  //get list of interests of the traveller

            // Fetch buddies that have at least one matching interest
            List<Person> buddies = personRepository.findBuddiesMatchingTraveller(travellerInterests);

            // Calculate match percentage for each buddy
            // Sort by match percentage in descending order
            List<PersonDTO> buddyDTOs = new ArrayList<>();
            for (Person person : buddies) {
                double matchPercentage = calculateMatchPercentage(travellerInterests, person.getInterestIds());
                PersonDTO apply = new PersonDTO(
                        person.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getEmail(),
                        person.getPhoneNumber(),
                        person.getAadharNumber(),
                        person.getLocation(),
                        person.getRole(),
                        person.getCreatedDate(),
                        person.getInterestIds(),
                        matchPercentage
                );
                buddyDTOs.add(apply);
            }
            buddyDTOs.sort(Comparator.comparingDouble(PersonDTO::getMatchPercentage).reversed());

            return buddyDTOs;
        } else {
            return Collections.emptyList(); // No matching traveler found
        }
    }

    // Helper method to calculate the match percentage
    private double calculateMatchPercentage(List<Integer> travellerInterests, List<Integer> buddyInterests) {
        if (travellerInterests == null || buddyInterests == null || travellerInterests.isEmpty()) {
            return 0.0;
        }

        // Find the number of matching interests
        long matchingInterestsCount = travellerInterests.stream()
                .filter(buddyInterests::contains)
                .count();

        // Calculate percentage of match
        return ((double) matchingInterestsCount / travellerInterests.size()) * 100;
    }

    @Autowired
    private InterestRepository interestRepository;

    // Method to retrieve interest names by IDs
    public List<Interest> getInterestsByIds(List<Integer> interestIds) {
        return interestRepository.findAllById(interestIds);
    }

}
