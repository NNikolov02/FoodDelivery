package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.customer.CustomerCreateRequest;
import com.example.fooddelivery.dto.customer.CustomerResponse;
import com.example.fooddelivery.dto.customer.CustomerResponsePrivate;
import com.example.fooddelivery.dto.customer.CustomerUpdateRequest;
import com.example.fooddelivery.email.OnForgotCustomer;
import com.example.fooddelivery.email.OnForgotDeliveryGuy;
import com.example.fooddelivery.email.OnRegistrationCompleteEventCustomer;
import com.example.fooddelivery.error.*;
import com.example.fooddelivery.mapping.CartMapper;
import com.example.fooddelivery.mapping.CustomerMapper;
import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.repository.CartRepository;
import com.example.fooddelivery.repository.CustomerPagingRepository;
import com.example.fooddelivery.repository.CustomerRepository;
import com.example.fooddelivery.validation.ObjectValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Component
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerPagingRepository pagingRepo;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ObjectValidator validator;

    public Page<Customer> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }

    public  Customer save(Customer customer){
        return repo.save(customer);
    }

    public Customer findById(String customerId) {
        return repo.findById(UUID.fromString(customerId)).orElse(null);

    }
    public Customer findByUserName(String name){
        return repo.findCustomerByUsername(name);
    }

    public void deleteById(String customerId){
        repo.deleteById(UUID.fromString(customerId));
    }

    @Transactional
    public void deleteByName(String name){
        repo.deleteCustomerByUsername(name);
    }

    public String registerUser(CustomerCreateRequest customerDto, HttpServletRequest request) {
        Map<String, String> validationErrors = validator.validate(customerDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Customer Create", validationErrors);
        }
        Customer create = customerMapper.modelFromCreateRequest(customerDto);
        Customer saved = repo.save(create);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEventCustomer(saved, request.getLocale(), appUrl));

        return "Registration Successfully!";
    }
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    public void createVerificationToken(Customer customer, String token) {
        System.out.println("Creating verification token for customer: " + customer.getUsername());
        System.out.println("Token: " + token);
    }

    public String updateCustomer(Authentication authentication, CustomerUpdateRequest customerDto){
        Map<String, String> validationErrors = validator.validate(customerDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Customer Update", validationErrors);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = repo.findCustomerByUsername(userDetails.getUsername());
        customerMapper.updateModelFromDto(customerDto,authenticatedCustomer);

        Customer saved = repo.save(authenticatedCustomer);

        return "It is updated successfully!";


    }
    public CartResponse seeCart(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = repo.findCustomerByUsername(userDetails.getUsername());
        Cart cart = authenticatedCustomer.getCart();

        CartResponse cartResponse = cartMapper.responseFromModelOne(cart);

        return cartResponse;
    }
    public CustomerResponsePrivate seeProfile(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = repo.findCustomerByUsername(userDetails.getUsername());
        CustomerResponsePrivate customerResponsePrivate = customerMapper.responseFromModelPrivate(authenticatedCustomer);
        customerResponsePrivate.setDeleteAccount("http://localhost:8084/delivery/customer/deleteAccount");

        return customerResponsePrivate;

    }
    @Transactional
    public String deleteProfile(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = repo.findCustomerByUsername(userDetails.getUsername());
        repo.delete(authenticatedCustomer);

        return "It is deleted successfully!";
    }
    public String forgotPassword(String email, HttpServletRequest request){
        Customer customer = repo.findByEmail(email);

        if(customer != null){
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnForgotCustomer(customer, request.getLocale(), appUrl));
        }else {
            throw new NotFoundCustomerException("There is not such a account!");
        }

        return "Email has been sent!";
    }
    @Transactional
    public String confirmAndChange(String token,String password){
        List<String> tokens = new ArrayList<>();
        tokens.add(token);
        Customer customer = repo.findByTokensIn(tokens);
        if(customer != null){
            customerMapper.updateModelFromDtoPassword(password,customer);
            customer.setTokens(null);
            repo.save(customer);
        }else {
            throw new InvalidToken("The token is invalid!");
        }

        return "The password is changed successfully!";
    }
//    @Transactional
//    public void purchaseCart(Authentication authentication){
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Customer authenticatedCustomer = repo.findCustomerByUsername(userDetails.getUsername());
//        cartRepository.deleteByCustomerName(authenticatedCustomer.getUsername());
//    }
//    public String rating(Appointment appointment, Doctor doctor, SetRatingRequest request){
//
//        if(appointment.getDoctor() == doctor) {
//
//
//            List<Rating> ratings = doctor.getRatings();
//            Rating newRating = Rating.builder()
//                    .rating(request.getRating())
//                    .doctor(doctor)
//                    .build();
//            ratings.add(newRating);
//            Integer average = 0;
//
//            for (Rating rating : ratings) {
//                average += rating.getRating();
//
//            }
//            doctor.setRating(average / ratings.size());
//
//            doctorRepo.save(doctor);
//        }
//
//
//        return "Rated successfully!";
//
//
//    }



}
