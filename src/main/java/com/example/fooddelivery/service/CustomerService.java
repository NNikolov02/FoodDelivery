package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.customer.CustomerCreateRequest;
import com.example.fooddelivery.dto.customer.CustomerUpdateRequest;
import com.example.fooddelivery.email.OnRegistrationCompleteEventCustomer;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.CartMapper;
import com.example.fooddelivery.mapping.CustomerMapper;
import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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

    public String updateCustomer(Customer customer, CustomerUpdateRequest customerDto){
        Map<String, String> validationErrors = validator.validate(customerDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Customer Update", validationErrors);
        }
        customerMapper.updateModelFromDto(customerDto,customer);

        Customer saved = repo.save(customer);

        return "It is updated successfully!";


    }
    public CartResponse seeCart(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = repo.findCustomerByUsername(userDetails.getUsername());
        Cart cart = authenticatedCustomer.getCart();

        CartResponse cartResponse = cartMapper.responseFromModelOne(cart);

        return cartResponse;
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
