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
public class DeliveryGuy {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean available;
    private String restaurantUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonManagedReference
    private Restaurant restaurant;
    @OneToMany(mappedBy = "deliveryGuy", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Cart> carts;
}
