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
public class Pasta {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;

    private String name;
    private String pastaType;
    private String sauce;
    private Integer price;
    private String url;
    private String addToCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonManagedReference
    private Menu menu;
    @OneToMany(mappedBy = "pasta", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FoodRating> ratings;
    private Integer rating;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pasta")
    @JoinTable(
            name = "pasta_cart",
            joinColumns = @JoinColumn(name = "pasta_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private List<Cart>carts;
}
