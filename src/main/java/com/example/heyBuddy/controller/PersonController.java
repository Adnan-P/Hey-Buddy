package com.example.heyBuddy.controller;

import com.example.heyBuddy.dto.PersonDTO;
import com.example.heyBuddy.repository.PersonRepository;
import com.example.heyBuddy.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addPerson(@RequestBody PersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }
    @GetMapping("/travellers")
    public List<PersonDTO> getTravellers(){
        return personService.getAllTravellers();
    }

    @GetMapping("/buddies")
    public List<PersonDTO> getBuddies(){
        return personService.getAllBuddies();
    }


//    get traveller using id
    @GetMapping("/traveller/{id}")
    public ResponseEntity<PersonDTO> getTravellerId(@PathVariable String id) {
        PersonDTO personDTO = personService.getTravellerById(id);
        if (personDTO != null) {
            return ResponseEntity.ok(personDTO);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if traveller not found
        }
    }

    @GetMapping("/traveller/{id}/matching-buddies")
    public ResponseEntity<List<PersonDTO>> getMatchingBuddiesWithPercentage(@PathVariable String id){
        List<PersonDTO> matchingBuddies = personService.getMatchingBuddiesWithPercentage(id);
        return ResponseEntity.ok(matchingBuddies);
    }
}
