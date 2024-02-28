package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.RestaurantRatingDto;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyResponse;
import com.example.fooddelivery.dto.restaurant.RestaurantCreateRequest;
import com.example.fooddelivery.dto.restaurant.RestaurantResponse;
import com.example.fooddelivery.error.CannotRate;
import com.example.fooddelivery.error.NotFoundCustomerException;
import com.example.fooddelivery.error.RestaurantNotFound;
import com.example.fooddelivery.mapping.RestaurantMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.CustomerRepository;
import com.example.fooddelivery.repository.DeliveryGuyRepository;
import com.example.fooddelivery.repository.RestaurantPagingRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

@SpringBootTest
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;
    @MockBean
    private RestaurantRepository repo;
    @MockBean
    private RestaurantPagingRepository pagingRepo;
    @MockBean
    private MenuService menuService;
    @MockBean
    private RestaurantMapper restaurantMapper;
    @MockBean
    private DeliveryGuyService deliveryGuyService;
    @MockBean
    private DeliveryGuyRepository deliveryGuyRepo;
    @MockBean
    private CustomerRepository customerRepo;
    @MockBean
    private CustomerService customerService;

    @Test
    public void testFetchAll() {
        List<Restaurant>restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        restaurants.add(restaurant);
        Page<Restaurant> restaurantsPage = new PageImpl<>(restaurants);
        int currentPage = 0;
        int pageSize = 10;

        when(pagingRepo.findAll(PageRequest.of(currentPage, pageSize))).thenReturn(restaurantsPage);

        Page<Restaurant>findAll = restaurantService.fetchAll(currentPage,pageSize);

        assertEquals("if it is equal",findAll,restaurantsPage);

        verify(pagingRepo,times(1)).findAll(PageRequest.of(currentPage, pageSize));



    }
    @Test
    public void testFindByResName() {
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();

        when(repo.findByName(restaurant.getName())).thenReturn(restaurant);

        Restaurant findRestaurant = restaurantService.findRestaurantByName(restaurant.getName());

        assertEquals("If it is equal",restaurant,findRestaurant);

        verify(repo,times(1)).findByName(restaurant.getName());


    }
    @Test
    public void testFindByResLocation() {

        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .location("Sofia")
                .build();

        when(repo.findByLocation(restaurant.getLocation())).thenReturn(restaurant);

        Restaurant findRestaurant = restaurantService.findRestaurantByLocation(restaurant.getLocation());

        assertEquals("If it is equal",restaurant,findRestaurant);

        verify(repo,times(1)).findByLocation(restaurant.getLocation());


    }
    @Test
    public void testDeleteByResName() {
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        doNothing().when(repo).deleteByName(restaurant.getName());
        restaurantService.deleteByName(restaurant.getName());
        Restaurant nullRestaurant = restaurantService.findRestaurantByName(restaurant.getName());

        assertNull("if it is null",nullRestaurant);
        verify(repo,times(1)).deleteByName(restaurant.getName());

    }
    @Test
    public void testCreateRestaurant() {
        Menu menu = Menu.builder()
                .name("Happy Menu")
                .build();
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .menu(menu)
                .build();
        RestaurantCreateRequest createDto = RestaurantCreateRequest
                .builder()
                .name("Happy")
                .build();
        RestaurantResponse restaurantResponse = RestaurantResponse.builder()
                .name("Happy")
                .menuUrl("http://localhost:8084/delivery/menu/" + createDto.getName())
                .build();
        when(restaurantMapper.modelFromCreateRequest(createDto)).thenReturn(restaurant);
        when(menuService.getMenu()).thenReturn(menu);
        when(repo.save(restaurant)).thenReturn(restaurant);
        when(menuService.save(menu)).thenReturn(menu);
        when(restaurantMapper.responseFromModelOne(restaurant)).thenReturn(restaurantResponse);

        RestaurantResponse createRestaurant = restaurantService.create(createDto);

        assertEquals("if it is equal",restaurantResponse,createRestaurant);

        verify(restaurantMapper,times(1)).modelFromCreateRequest(createDto);
        verify(restaurantMapper,times(1)).responseFromModelOne(restaurant);
        verify(menuService,times(1)).getMenu();
        verify(repo,times(1)).save(restaurant);
        verify(menuService,times(1)).save(menu);


    }
    @Test
    public void testPutMenu() {
        Menu menu = Menu.builder()
                .name("Happy Menu")
                .build();
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        Restaurant testRestaurant = Restaurant.builder()
                .name("Happy")
                .menu(menu)
                .menuUrl("http://localhost:8084/delivery/menu")
                .build();

        when(repo.save(restaurant)).thenReturn(restaurant);

        String putMenu = restaurantService.putMenu(menu,restaurant);

        assertEquals("if it is equal","It is put!",putMenu);
        assertEquals("if it is equal",testRestaurant,restaurant);

        verify(repo,times(1)).save(restaurant);

    }
    @Test
    public void testPutDeliveryGuy() {
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        DeliveryGuy testDeliveryGuy = DeliveryGuy.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .restaurant(restaurant)
                .build();

        when(repo.findByName(restaurant.getName())).thenReturn(restaurant);
        when(deliveryGuyRepo.findByFirstNameAndLastName(deliveryGuy.getFirstName(),deliveryGuy.getLastName())).thenReturn(deliveryGuy);
        when(repo.save(restaurant)).thenReturn(restaurant);
        when(deliveryGuyService.save(testDeliveryGuy)).thenReturn(testDeliveryGuy);

        String putDeliveryGuy = restaurantService.putDeliveryGuy(restaurant.getName(),deliveryGuy.getFirstName(),deliveryGuy.getLastName());

        assertEquals("if it is equal","It is put",putDeliveryGuy);
        assertEquals("if it is equal",testDeliveryGuy,deliveryGuy);
        try {
             restaurantService.putDeliveryGuy("Avangard",deliveryGuy.getFirstName(),deliveryGuy.getLastName());

        } catch (RestaurantNotFound e) {
            assertEquals("If it is equal","There is not such a restaurant!", e.getMessage());
        }

        verify(repo,times(1)).findByName(restaurant.getName());
        verify(deliveryGuyRepo,times(2)).findByFirstNameAndLastName(deliveryGuy.getFirstName(),deliveryGuy.getLastName());
        verify(repo,times(1)).save(restaurant);
        verify(deliveryGuyService,times(1)).save(testDeliveryGuy);

    }
    @Test
    public void testSeeDeliveryGuy() {
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .restaurant(restaurant)
                .build();
        DeliveryGuy deliveryGuy1 = DeliveryGuy.builder()
                .firstName("Georgi")
                .lastName("Ivanov")
                .restaurant(restaurant)
                .build();
        List<DeliveryGuy>deliveryGuys = List.of(deliveryGuy,deliveryGuy1);
        restaurant.setDeliveryGuys(deliveryGuys);

        DeliveryGuyResponse deliveryGuyResponse = DeliveryGuyResponse.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        DeliveryGuyResponse deliveryGuyResponse1 = DeliveryGuyResponse.builder()
                .firstName("Georgi")
                .lastName("Ivanov")
                .build();
        List<DeliveryGuyResponse>deliveryGuyResponses = List.of(deliveryGuyResponse,deliveryGuyResponse1);
        when(repo.findByName(restaurant.getName())).thenReturn(restaurant);
        when(restaurantMapper.responseFromModelDeliveryGuy(deliveryGuys)).thenReturn(deliveryGuyResponses);

        List<DeliveryGuyResponse>testDeliveryGuyResponse = restaurantService.seeDeliveryGuys(restaurant.getName());

        assertEquals("if it is equal",deliveryGuyResponses,testDeliveryGuyResponse);

        verify(repo,times(1)).findByName(restaurant.getName());
        verify(restaurantMapper,times(1)).responseFromModelDeliveryGuy(deliveryGuys);



    }
    @Test
    public void testPutRating() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        List<Restaurant>restaurants = new ArrayList<>();
        restaurants.add(restaurant);

        Restaurant newRestaurant = Restaurant.builder()
                .name("Avangard")
                .build();
        RestaurantRating restaurantRating = RestaurantRating.builder()
                .rating(4)
                .restaurant(newRestaurant)
                .build();
        List<RestaurantRating>restaurantRatingsFirst = new ArrayList<>();
        restaurantRatingsFirst.add(restaurantRating);
        newRestaurant.setRatings(restaurantRatingsFirst);
        Customer customer = Customer.builder()
                .username("Ivan")
                .restaurants(restaurants)
                .build();
        List<Customer>customers =new ArrayList<>();
        customers.add(customer);
        restaurant.setCustomers(customers);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(customerRepo.findCustomerByUsername(userDetails.getUsername())).thenReturn(customer);
        when(repo.findByName(restaurant.getName())).thenReturn(restaurant);

        try {
            restaurantService.putRating(restaurant.getName(),5,authentication);

        } catch (CannotRate e) {
            assertEquals("If it is equal","You have already rated!", e.getMessage());
        }
        when(customerRepo.findCustomerByUsername(userDetails.getUsername())).thenReturn(null);
        try {
            restaurantService.putRating(restaurant.getName(),5,authentication);

        } catch (CannotRate e) {
            assertEquals("If it is equal","This is the error", e.getMessage());
        }
        when(customerRepo.findCustomerByUsername(userDetails.getUsername())).thenReturn(customer);
        when(repo.findByName(newRestaurant.getName())).thenReturn(newRestaurant);

        Restaurant testNewRestaurant = Restaurant.builder()
                .name("Avangard")
                .build();
        RestaurantRating restaurantRating1 = RestaurantRating.builder()
                .rating(4)
                .restaurant(testNewRestaurant)
                .build();

        RestaurantRating restaurantRating2 = RestaurantRating.builder()
                .rating(5)
                .restaurant(testNewRestaurant)
                .build();
        List<RestaurantRating>restaurantRatingList = new ArrayList<>();
        restaurantRatingList.add(restaurantRating1);
        restaurantRatingList.add(restaurantRating2);
        testNewRestaurant.setRatings(restaurantRatingList);
        RestaurantRatingDto restaurantRatingDto1 = RestaurantRatingDto.builder()
                .rating(4)
                .build();

        RestaurantRatingDto restaurantRatingDto2 = RestaurantRatingDto.builder()
                .rating(5)
                .build();
        testNewRestaurant.setRating(4);
        List<RestaurantRatingDto>restaurantRatings = new ArrayList<>();
        restaurantRatings.add(restaurantRatingDto1);
        restaurantRatings.add(restaurantRatingDto2);
        RestaurantResponse restaurantResponse = RestaurantResponse.builder()
                .name("Avangard")
                .ratings(restaurantRatings)
                .build();
        List<Restaurant>newRestaurants = new ArrayList<>();
        newRestaurants.add(testNewRestaurant);
        newRestaurants.add(restaurant);
        Customer newCustomer = Customer.builder()
                .username("Ivan")
                .restaurants(newRestaurants)
                .build();
        List<Customer>newCustomers = new ArrayList<>();
        newCustomers.add(newCustomer);
        testNewRestaurant.setCustomers(newCustomers);
        when(repo.save(testNewRestaurant)).thenReturn(testNewRestaurant);
        when(customerRepo.save(newCustomer)).thenReturn(newCustomer);
        when(restaurantMapper.responseFromModelOne(testNewRestaurant)).thenReturn(restaurantResponse);

        RestaurantResponse restaurantResponseTest = restaurantService.putRating(newRestaurant.getName(),5,authentication);

        assertEquals("if it is equal",restaurantResponse,restaurantResponseTest);
        assertEquals("if it is equal",2,customer.getRestaurants().size());
        assertEquals("If it is equal",4,newRestaurant.getRating());

        verify(authentication,times(3)).getPrincipal();
        verify(customerRepo,times(3)).findCustomerByUsername(userDetails.getUsername());
        verify(repo,times(2)).findByName(restaurant.getName());
//        verify(repo,times(1)).findByName(newRestaurant.getName());
////        verify(repo,times(1)).save(testNewRestaurant);
//        verify(customerRepo,times(1)).save(newCustomer);
//        verify(restaurantMapper,times(1)).responseFromModelOne(testNewRestaurant);

    }
}
