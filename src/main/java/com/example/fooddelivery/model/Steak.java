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
public class Steak {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;

    private String name;
    private String steakType;
    private String cookingLevel;
    private Integer price;
    private Integer amount;
    private String url;
    private String addToCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonManagedReference
    private Menu menu;
    @OneToMany(mappedBy = "steak", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FoodRating> ratings;
    private Integer rating;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("steak")
    @JoinTable(
            name = "steak_cart",
            joinColumns = @JoinColumn(name = "steak_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private List<Cart>carts;
}
