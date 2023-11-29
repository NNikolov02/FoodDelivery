package com.example.fooddelivery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;
    private String location;
    private LocalTime createTime;
    private LocalTime timeOfDelivery;
    private Integer fullPrice;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnoreProperties("cart")
    private Customer customer;
    @ManyToMany(mappedBy = "carts", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("carts")
    private List<Pizza> pizzas;
    @ManyToMany(mappedBy = "carts", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("carts")
    private List<Pasta> pastas;
    @ManyToMany(mappedBy = "carts", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("carts")
    private List<Salad> salads;
    @ManyToMany(mappedBy = "carts", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("carts")
    private List<Steak> steaks;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "deliveryGuy_id", referencedColumnName = "id")
    @JsonIgnoreProperties("cart")
    private DeliveryGuy deliveryGuy;
}
