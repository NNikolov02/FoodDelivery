package com.example.fooddelivery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;
    @Column(unique = true)
    private String username;

    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String address;
    private  String url;

    @Column(name = "has_rated")
    private Boolean hasRated;
    @OneToOne(mappedBy = "customer")
    @JsonIgnoreProperties("customer")
    private Cart cart;
    @ManyToMany(mappedBy = "customers", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("customers")
    private List<Restaurant> restaurants;

}
