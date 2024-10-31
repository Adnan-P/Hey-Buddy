package com.example.heyBuddy.dto;

import com.example.heyBuddy.enums.Role;
import com.example.heyBuddy.model.Vehicle;
import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
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
    private double matchPercentage;
    private Vehicle vehicle;

    public UserDTO(String id, String firstName, String lastName, String email, String phoneNumber,
                   String aadharNumber, String location, Role role, Date createdDate, List<Integer> interestIds) {
        this.id=id;
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

