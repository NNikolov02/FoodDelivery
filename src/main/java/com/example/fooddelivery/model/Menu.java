package com.example.fooddelivery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Menu {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;

    private String name;
    private String description;
    private String url;


    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Restaurant> restaurants;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Pizza> pizzas;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Pasta> pastas;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Salad> salads;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Steak> steaks;




}
