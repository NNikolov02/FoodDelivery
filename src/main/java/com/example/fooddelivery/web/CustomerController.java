package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.customer.*;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.CustomerMapper;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.service.CustomerService;
import com.example.fooddelivery.validation.ObjectValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ObjectValidator validator;


    @GetMapping(value = "", produces = "application/json")
    public CustomerApiPage<CustomerResponse> getAllCustomers(
            @RequestParam(required = false, defaultValue = "1") Integer currPage) {

        Page<CustomerResponse> customerPage = customerService.fetchAll(currPage - 1, 10).map(customerMapper::responseFromModelOne);
        return new CustomerApiPage<>(customerPage);
    }



    @GetMapping(value ="/{customerId}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable String customerId){

        Customer customer = customerService.findById(customerId);
        CustomerResponse customerResponse = customerMapper.responseFromModelOne(customer);

        return ResponseEntity.ok().body(customerResponse);
    }
//    @GetMapping(value ="")
//    public ResponseEntity<CustomerResponse> findByAuthentication(@PathVariable String customerId){
//
//        Customer customer = customerService.findById(customerId);
//        CustomerResponse customerResponse = customerMapper.responseFromModelOne(customer);
//
//        return ResponseEntity.ok().body(customerResponse);
//    }
    @GetMapping(value = "/cart")
    public ResponseEntity<CartResponse>seeCart(Authentication authentication){
        CartResponse cartResponse = customerService.seeCart(authentication);

        return ResponseEntity.ok().body(cartResponse);
    }
//    @DeleteMapping(value = "/cart/purchase")
//    public ResponseEntity<String>purchase(Authentication authentication){
//        customerService.purchaseCart(authentication);
//
//        return ResponseEntity.ok("It is purchased");
//    }

    @GetMapping(value ="/name/{customerName}")
    public ResponseEntity<CustomerResponse>findByUserName(@PathVariable String customerName){

        Customer customer = customerService.findByUserName(customerName);
        CustomerResponse customerResponse = customerMapper.responseFromModelOne(customer);


        return ResponseEntity.ok().body(customerResponse);
    }
    @GetMapping(value = "/seeProfile")
    public ResponseEntity<CustomerResponsePrivate>seeProfile(Authentication authentication){
        CustomerResponsePrivate customerResponsePrivate = customerService.seeProfile(authentication);

        return ResponseEntity.ok().body(customerResponsePrivate);

    }
    @DeleteMapping(value = "/deleteAccount")
    public ResponseEntity<String>deleteProfile(Authentication authentication){
        String delete = customerService.deleteProfile(authentication);

        return ResponseEntity.ok(delete);
    }

    @DeleteMapping(value ="/{customerId}")
    public ResponseEntity<String> deleteById(@PathVariable String customerId){
        customerService.deleteById(customerId);

        return ResponseEntity.ok("It is deleted!");

    }
    @DeleteMapping(value ="/name/{customerName}")
    public ResponseEntity<String>deleteByName(@PathVariable String customerName){
        customerService.deleteByName(customerName);

        return ResponseEntity.ok("It is deleted!");
    }

    @PostMapping(value ="/registration")
    public ResponseEntity<String> createUserAndRegister(
            @RequestBody @Valid CustomerCreateRequest customerDto,
            HttpServletRequest request, Errors errors)  {

        String connect = customerService.registerUser(customerDto,request);


        return  ResponseEntity.status(HttpStatus.CREATED).body(connect);

    }
    @PatchMapping(value ="/update")
    public ResponseEntity<String>updateCustomer( @RequestBody CustomerUpdateRequest customerDto,Authentication authentication){
        String customerUpdate  = customerService.updateCustomer(authentication,customerDto);


        return ResponseEntity.ok().body(customerUpdate);
    }
    @PostMapping(value = "/forgotPassword")
    public ResponseEntity<String>forgetPassword(@RequestParam String email, HttpServletRequest request){
        String forgot = customerService.forgotPassword(email,request);

        return ResponseEntity.ok(forgot);

    }
    @PatchMapping(value = "/confirm")
    public ResponseEntity<String>verifyToken(@RequestParam String token,@RequestParam String newPassword){
        String confirm = customerService.confirmAndChange(token,newPassword);

        return ResponseEntity.ok(confirm);

    }
}