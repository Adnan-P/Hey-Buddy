package com.example.heyBuddy.controller;

import com.example.heyBuddy.dto.UserDTO;
import com.example.heyBuddy.repository.UserRepository;
import com.example.heyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDTO personDTO) {
        return userService.createUser(personDTO);
    }
    @GetMapping("/travellers")
    public List<UserDTO> getTravellers(){
        return userService.getAllTravellers();
    }

    @GetMapping("/buddies")
    public List<UserDTO> getBuddies(){
        return userService.getAllBuddies();
    }


//    get traveller using id
    @GetMapping("/traveller/{id}")
    public ResponseEntity<UserDTO> getTravellerId(@PathVariable String id) {
        UserDTO userDTO = userService.getTravellerById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if traveller not found
        }
    }

    @GetMapping("/traveller/{id}/matching-buddies")
    public ResponseEntity<List<UserDTO>> getMatchingBuddiesWithPercentage(@PathVariable String id){
        List<UserDTO> matchingBuddies = userService.getMatchingBuddiesWithPercentage(id);
        return ResponseEntity.ok(matchingBuddies);
    }
}
