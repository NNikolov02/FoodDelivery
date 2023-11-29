package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyResponse;
import com.example.fooddelivery.dto.menu.MenuResponse;
import com.example.fooddelivery.dto.restaurant.RestaurantCreateRequest;
import com.example.fooddelivery.dto.restaurant.RestaurantResponse;
import com.example.fooddelivery.dto.steak.SetSteakRequest;
import com.example.fooddelivery.error.CannotRate;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.error.RestaurantNotFound;
import com.example.fooddelivery.mapping.RestaurantMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.CustomerRepository;
import com.example.fooddelivery.repository.DeliveryGuyRepository;
import com.example.fooddelivery.repository.RestaurantPagingRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repo;

    @Autowired
    private RestaurantPagingRepository pagingRepo;

    @Autowired
    private RestaurantMapper restaurantMapper;
    @Autowired
    private ObjectValidator validator;
    @Autowired
    private MenuService menuService;
    @Autowired
    private DeliveryGuyService deliveryGuyService;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private DeliveryGuyRepository deliveryGuyRepo;

    public Page<Restaurant> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }
    public Restaurant save(Restaurant restaurant){
        return repo.save(restaurant);
    }
    public Restaurant findRestaurantByName(String name){
        return repo.findByName(name);
    }
    public Restaurant findRestaurantByLocation(String location){
        return repo.findByLocation(location);
    }
    @Transactional
    public void deleteByName(String name){
        repo.deleteByName(name);
    }
    public RestaurantResponse create(RestaurantCreateRequest restaurantDto){
        Map<String, String> validationErrors = validator.validate(restaurantDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Restaurant Create", validationErrors);
        }
        Menu menu = menuService.getMenu();
        Restaurant create = restaurantMapper.modelFromCreateRequest(restaurantDto);
        create.setMenu(menu);
        create.setMenuUrl("http://localhost:8084/delivery/menu/" + create.getName());


        Restaurant restaurant =repo.save(create);
        List<Restaurant>restaurants = new ArrayList<>();
        restaurants.add(restaurant);
        menu.setRestaurants(restaurants);
        menuService.save(menu);

        RestaurantResponse restaurantResponse = restaurantMapper.responseFromModelOne(restaurant);

        return restaurantResponse;
    }
    @Transactional
    public String putMenu(Menu menu,Restaurant restaurant){
        List<Restaurant>restaurants = new ArrayList<>();
        restaurants.add(restaurant);


        if(menu != null){
            restaurant.setMenu(menu);
            restaurant.setMenuUrl("http://localhost:8084/delivery/menu");
//            menu.setRestaurants(restaurants);
//            menuService.save(menu);
            repo.save(restaurant);


        }
        return "It is put!";
    }
    public String putDeliveryGuy(String name,String firstName,String lastName){
        Restaurant restaurant = repo.findByName(name);
        DeliveryGuy deliveryGuy = deliveryGuyRepo.findByFirstNameAndLastName(firstName,lastName);
        List<DeliveryGuy>deliveryGuys = new ArrayList<>();
        deliveryGuys.add(deliveryGuy);

        if(restaurant != null){
            restaurant.setDeliveryGuys(deliveryGuys);
            deliveryGuy.setRestaurant(restaurant);
            deliveryGuyService.save(deliveryGuy);
            repo.save(restaurant);
            return "It is put";

        }else {
            throw new RestaurantNotFound("There is not such a restaurant!");
        }



    }
    public List<DeliveryGuyResponse>seeDeliveryGuys(String restaurantName){
        Restaurant restaurant = repo.findByName(restaurantName);
        List<DeliveryGuy>deliveryGuys = restaurant.getDeliveryGuys();

        List<DeliveryGuyResponse>deliveryGuyResponses = restaurantMapper.responseFromModelDeliveryGuy(deliveryGuys);

        return deliveryGuyResponses;

    }
    public RestaurantResponse putRating(String restaurantName,Integer rating, Authentication authentication){
        int sum = 0;
        int avarage;
        Restaurant restaurant = repo.findByName(restaurantName);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        Boolean hasRated = authenticatedCustomer.getHasRated();


        if(authenticatedCustomer != null) {
            if (hasRated == null) {
                RestaurantRating restaurantRating = RestaurantRating.builder()
                        .rating(rating)
                        .restaurant(restaurant)
                        .build();
                List<RestaurantRating> restaurantRatings = restaurant.getRatings();
                restaurantRatings.add(restaurantRating);
                restaurant.setRatings(restaurantRatings);
                for (RestaurantRating restaurantRating1 : restaurant.getRatings()) {
                    sum += restaurantRating1.getRating();

                }
                avarage = sum / restaurantRatings.size();
                restaurant.setRating(avarage);
                authenticatedCustomer.setHasRated(true);
                customerRepo.save(authenticatedCustomer);
                repo.save(restaurant);


            }else {
                throw new CannotRate("You have already rated!");
            }
        }
        RestaurantResponse restaurantResponse = restaurantMapper.responseFromModelOne(restaurant);

        return restaurantResponse;
    }


}
