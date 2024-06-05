package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.mapping.CartMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.CartRepository;
import com.example.fooddelivery.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;
    @MockBean
    private CartRepository repo;
    @MockBean
    private CartMapper cartMapper;
    @MockBean
    private CustomerRepository customerRepo;

    @Test
    public void testSaveCart() {
        Cart cart = Cart.builder()
                .createTime(LocalDateTime.now())
                .build();

        when(repo.save(cart)).thenReturn(cart);

        Cart savedCart = cartService.save(cart);

        assertEquals("If it is equal",cart,savedCart);

        verify(repo,times(1)).save(cart);

    }
    @Test
    public void testFindById() {
        Cart cart = Cart.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime.now())
                .build();
        when(repo.findById(cart.getId())).thenReturn(Optional.of(cart));

        Cart findCart = cartService.findById(cart.getId().toString());

        assertEquals("If it is equal",cart,findCart);

        verify(repo,times(1)).findById(cart.getId());
    }
    @Test
    public void testRemoveCart() {
        DeliveryGuy deliveryGuy =DeliveryGuy.builder()
                .username("Ivan")
                .build();
        Cart cart = Cart.builder()
                .createTime(LocalDateTime.now())
                .deliveryGuy(deliveryGuy)
                .build();

        when(repo.save(cart)).thenReturn(cart);

        cartService.removeCart(cart);

        assertEquals("If it is equal",null,cart.getDeliveryGuy());

        verify(repo,times(1)).save(cart);

    }
    @Test
    public void testPizzaName() {
        Pizza pizza = Pizza.builder()
                .name("Peperoni")
                .build();
        Pizza pizza1 = Pizza.builder()
                .name("Margarita")
                .build();
        List<Pizza>pizzas = List.of(pizza,pizza1);
        List<String>names = List.of(pizza.getName(),pizza1.getName());

        List<String>testNames = cartService.pizzasName(pizzas);

        assertEquals("if it is equal",names,testNames);



    }
    @Test
    public void testPastaName() {
        Pasta pasta = Pasta.builder()
                .name("Bolognese")
                .build();
        Pasta pasta1 = Pasta.builder()
                .name("Karbonarra")
                .build();
        List<Pasta>pastas = List.of(pasta,pasta1);
        List<String>names = List.of(pasta.getName(),pasta1.getName());

        List<String>testNames = cartService.pastasName(pastas);

        assertEquals("if it is equal",names,testNames);



    }
    @Test
    public void testSteakName() {
        Steak steak = Steak.builder()
                .name("Tartar")
                .build();
        Steak steak1 = Steak.builder()
                .name("Steak-Medium")
                .build();
        List<Steak>steaks = List.of(steak,steak1);
        List<String>names = List.of(steak.getName(),steak1.getName());

        List<String>testNames = cartService.steaksName(steaks);

        assertEquals("if it is equal",names,testNames);



    }
    @Test
    public void testSaladName() {
        Salad salad = Salad.builder()
                .name("Cezar")
                .build();
        Salad salad1 = Salad.builder()
                .name("Greek-Salad")
                .build();
        List<Salad>salads = List.of(salad,salad1);
        List<String>names = List.of(salad.getName(),salad1.getName());

        List<String>testNames = cartService.saladName(salads);

        assertEquals("if it is equal",names,testNames);



    }
    @Test
    public void testSeeCart() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        Cart cart = Cart.builder()
                .createTime(LocalDateTime.now())
                .build();
        Customer customer= Customer.builder()
                .username("Ivan")
                .firstName("Ivan80")
                .cart(cart)
                .build();
        cart.setCustomer(customer);
        CustomerDto customerDto =CustomerDto.builder()
                .firstName("Ivan80")
                .build();
        CartResponse cartResponse =CartResponse.builder()
                .createTime(LocalDateTime.now())
                .customer(customerDto)
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(customerRepo.findCustomerByUsername(userDetails.getUsername())).thenReturn(customer);
        when(cartMapper.responseFromModelOne(cart)).thenReturn(cartResponse);

        CartResponse cartResponse1 = cartService.seeCart(authentication);

        assertEquals("If it is equal",cartResponse,cartResponse1);
        assertEquals("If it is equal","http://localhost:8084/delivery/cart/finish",cartResponse1.getFinish());

        verify(authentication, times(1)).getPrincipal();
        verify(customerRepo, times(1)).findCustomerByUsername(userDetails.getUsername());
        verify(cartMapper,times(1)).responseFromModelOne(cart);





    }
    @Test
    public void testFinish() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        Pizza pizza = Pizza.builder()
                .name("Peperoni")
                .build();
        List<Pizza>pizzas = List.of(pizza);
        Pasta pasta = Pasta.builder()
                .name("Bolognese")
                .build();
        List<Pasta>pastas = List.of(pasta);
        Steak steak = Steak.builder()
                .name("Tartar")
                .build();
        List<Steak>steaks = List.of(steak);
        Salad salad = Salad.builder()
                .name("Cezar")
                .build();
        List<Salad>salads = List.of(salad);
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Georgi")
                .firstName("Georgi")
                .restaurant(restaurant)
                .email("nikinikolov2002@gmail.com")
                .build();

        Customer customer =Customer.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .phoneNumber("0884175050")
                .build();
        Cart cart = Cart.builder()
                .customer(customer)
                .deliveryGuy(deliveryGuy)
                .location("Sofia")
                .steaks(steaks)
                .pizzas(pizzas)
                .pastas(pastas)
                .salads(salads)
                .createTime(LocalDateTime.now())
                .fullPrice(55)
                .build();
        customer.setCart(cart);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(customerRepo.findCustomerByUsername(userDetails.getUsername())).thenReturn(customer);
        when(repo.findByCustomersId(customer.getId())).thenReturn(cart);
        when(repo.save(cart)).thenReturn(cart);
        when(customerRepo.save(customer)).thenReturn(customer);
        when(customerRepo.findCustomerByCartDeliveryGuy(deliveryGuy.getId())).thenReturn(customer);

        String testFinish = cartService.finish(authentication,request);

        assertEquals("if it is equal","The purchase is finished!",testFinish);
        assertEquals("if it is equal","Ivan Ivanov",cart.getCustomerName());
        assertEquals("if it is equal","0884175050",cart.getCustomerNumber());
        assertEquals("if it is equal",null,cart.getCustomer());
        assertEquals("if it is equal",null,customer.getCart());

        verify(authentication,times(1)).getPrincipal();
        verify(customerRepo,times(1)).findCustomerByUsername(userDetails.getUsername());
        verify(repo,times(1)).findByCustomersId(customer.getId());
        verify(repo,times(2)).save(cart);
        verify(customerRepo,times(1)).save(customer);




    }

}
