package com.example.fooddelivery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;
    private String name;

    private String pizzaSize;
    @ElementCollection
    private List<String> toppings;
    private Integer price;
//    @ElementCollection
//    @CollectionTable(name = "customer_pizza_customerAmountMap", joinColumns = @JoinColumn(name = "pizza_id"))
//    @MapKeyJoinColumn(name = "customer_id")
//    private Map<Customer, Integer> customerAmountMap =new HashMap<>();
    private Integer amount;
    private String url;
    private String addToCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonManagedReference
    private Menu menu;
    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FoodRating> ratings;
    private Integer rating;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pizza")
    @JoinTable(
            name = "pizza_cart",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private List<Cart>carts;
}
