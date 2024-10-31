package com.example.heyBuddy.service;

import com.example.heyBuddy.dto.UserDTO;
import com.example.heyBuddy.enums.Role;
import com.example.heyBuddy.model.User;
import com.example.heyBuddy.model.Vehicle;
import com.example.heyBuddy.repository.UserRepository;
import com.example.heyBuddy.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<String> createUser(UserDTO userDTO) {
        try {
            userRepository.save(userDtoToUserEntity(userDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
        } catch(Exception e){

        return
                 ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("creation Unsuccessfully");
        }
    }


    private User userDtoToUserEntity(UserDTO userDTO) {
        User user = new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPhoneNumber(),
                userDTO.getAadharNumber(),
                userDTO.getLocation(),
                userDTO.getRole(),
                Util.getCurrentDate(),
                userDTO.getInterestIds()
        );

        // Set Vehicle details if the role is BUDDY and vehicle data is provided
        if (user.getRole() == Role.BUDDY && userDTO.getVehicle() != null) {
            user.setVehicle(new Vehicle(
                    userDTO.getVehicle().getMake(),
                    userDTO.getVehicle().getModel(),
                    userDTO.getVehicle().getYear(),
                    userDTO.getVehicle().getRegistrationNumber()
            ));
        }

        return user;
    }


    //list all travellers
    public List<UserDTO> getAllTravellers(){
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.TRAVELLER)
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }


    //list all buddies
    public List<UserDTO> getAllBuddies(){
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.BUDDY)
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }




    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAadharNumber(),
                user.getLocation(),
                user.getRole(),
                user.getCreatedDate(),
                user.getInterestIds()
        );

        // Add vehicle details if the role is BUDDY
        if (user.getRole() == Role.BUDDY && user.getVehicle() != null) {
            userDTO.setVehicle(new Vehicle(
                    user.getVehicle().getMake(),
                    user.getVehicle().getModel(),
                    user.getVehicle().getYear(),
                    user.getVehicle().getRegistrationNumber()
            ));
        }

        return userDTO;
    }


    //getTravellerById Logic
    public UserDTO getTravellerById(String id) {
        Optional<User> userOpt = userRepository.findTravellerById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new UserDTO(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getAadharNumber(),
                    user.getLocation(),
                    user.getRole(),
                    user.getCreatedDate(),
                    user.getInterestIds()
            );
        } else {
            // Handle the case where the traveller is not found (e.g., throw an exception)
            return null;
        }
    }




    public List<UserDTO> getMatchingBuddiesWithPercentage(String travellerId) {
        Optional<User> travellerOpt = userRepository.findTravellerById(travellerId);

        if (travellerOpt.isPresent()) {
            User traveller = travellerOpt.get();
            List<Integer> travellerInterests = traveller.getInterestIds(); // Get list of interests of the traveller

            // Fetch buddies that have at least one matching interest
            List<User> buddies = userRepository.findBuddiesMatchingTraveller(travellerInterests);

            // Calculate match percentage for each buddy
            List<UserDTO> buddyDTOs = new ArrayList<>();
            for (User user : buddies) {
                double matchPercentage = calculateMatchPercentage(travellerInterests, user.getInterestIds());
                UserDTO apply = convertToUserDTO(user);
                apply.setMatchPercentage(matchPercentage); // Assuming UserDTO has a setMatchPercentage method
                buddyDTOs.add(apply);
            }
            buddyDTOs.sort(Comparator.comparingDouble(UserDTO::getMatchPercentage).reversed());

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


}
