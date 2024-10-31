package com.example.heyBuddy.model;

import com.example.heyBuddy.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String aadharNumber;
    private String location;
    private Role role;
    private Date createdDate;
    private List<Integer> interestIds;
    private Vehicle vehicle;

    public User(String firstName, String lastName, String email, String phoneNumber, String aadharNumber, String location, Role role, Date createdDate, List<Integer> interestIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.aadharNumber = aadharNumber;
        this.location = location;
        this.role = role;
        this.createdDate = createdDate;
        this.interestIds = interestIds;
    }


}
