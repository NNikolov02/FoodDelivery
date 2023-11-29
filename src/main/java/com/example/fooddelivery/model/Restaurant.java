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
public class Restaurant {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;

    private String name;

    private String description;

    private String location;
    private String menuUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonManagedReference
    private Menu menu;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RestaurantRating> ratings;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<DeliveryGuy> deliveryGuys;

    private Integer rating;


}
