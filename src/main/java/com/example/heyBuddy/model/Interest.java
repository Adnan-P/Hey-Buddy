package com.example.heyBuddy.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Interest {
    @Id
    private Integer id;
    private String name;

}
