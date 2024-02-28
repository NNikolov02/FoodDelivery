package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.customer.CustomerCreateRequest;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.dto.customer.CustomerResponsePrivate;
import com.example.fooddelivery.dto.customer.CustomerUpdateRequest;
import com.example.fooddelivery.email.OnForgotCustomer;
import com.example.fooddelivery.email.OnRegistrationCompleteEventCustomer;
import com.example.fooddelivery.error.InvalidToken;
import com.example.fooddelivery.error.NotFoundCustomerException;
import com.example.fooddelivery.mapping.CartMapper;
import com.example.fooddelivery.mapping.CustomerMapper;
import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.repository.CustomerPagingRepository;
import com.example.fooddelivery.repository.CustomerRepository;
import com.example.fooddelivery.web.CustomerController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
public class CustomerServiceTest {

    @MockBean
    private CustomerRepository repo;
    @MockBean
    private CustomerPagingRepository pagingRepo;
    @MockBean
    private CustomerMapper customerMapper;
    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private CustomerService customerService;
    @MockBean
    private CartMapper cartMapper;
    @Autowired
    private CustomerController customerController;

    @Test
    public void testFetchAll() {
        List<Customer> customerList = new ArrayList<>();

        Page<Customer> customers= new PageImpl<>(customerList);

        int currentPage = 1;
        int pageSize = 10;


        when(pagingRepo.findAll(PageRequest.of(currentPage, pageSize))).thenReturn(customers);

        Page<Customer> result = customerService.fetchAll(currentPage, pageSize);


        assertEquals("See if it equal",customers, result);

        verify(pagingRepo, times(1)).findAll(PageRequest.of(currentPage, pageSize));
    }
    @Test
    public void testSaveCustomer() {

        Customer customer= Customer.builder()
                .username("Ivan")
                .build();

        when(repo.save(customer)).thenReturn(customer);

        Customer result = customerService.save(customer);

        assertEquals("See if it equal",customer, result);

        verify(repo, times(1)).save(customer);


    }
    @Test
    public void testFindCustomerById() {
        Customer customer= Customer.builder()
                .id(UUID.randomUUID())
                .username("Ivan")
                .build();

        when(repo.findById(customer.getId())).thenReturn(Optional.of(customer));

        Customer result = customerService.findById(customer.getId().toString());

        verify(repo, times(1)).findById(customer.getId());

        assertEquals("See if it equal",customer, result);


    }
    @Test
    public void testFindCustomerByUsername() {
        Customer customer= Customer.builder()
                .id(UUID.randomUUID())
                .username("Ivan")
                .build();

        when(repo.findCustomerByUsername(customer.getUsername())).thenReturn(customer);

        Customer result = customerService.findByUserName(customer.getUsername());

        verify(repo, times(1)).findCustomerByUsername(customer.getUsername());

        assertEquals("See if it equal",customer, result);


    }
    @Test
    public void testDeleteCustomerById() {
        Customer customer= Customer.builder()
                .id(UUID.randomUUID())
                .username("Ivan")
                .build();
        doNothing().when(repo).deleteById(customer.getId());

        customerService.deleteById(customer.getId().toString());
        verify(repo).deleteById(customer.getId());

        Customer nullCustomer = customerService.findById(customer.getId().toString());

        assertNull(nullCustomer);
    }
    @Test
    public void testDeleteCustomerByUsername() {
        Customer customer= Customer.builder()
                .id(UUID.randomUUID())
                .username("Ivan")
                .build();
        doNothing().when(repo).deleteCustomerByUsername(customer.getUsername());

        customerService.deleteByName(customer.getUsername());
        verify(repo).deleteCustomerByUsername(customer.getUsername());

        Customer nullCustomer = customerService.findByUserName(customer.getUsername());

        assertNull(nullCustomer);

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
        when(repo.findCustomerByUsername(userDetails.getUsername())).thenReturn(customer);
        when(cartMapper.responseFromModelOne(cart)).thenReturn(cartResponse);

        CartResponse cartResponse1 = customerService.seeCart(authentication);

        assertEquals("If it is equal",cartResponse,cartResponse1);

        verify(authentication, times(1)).getPrincipal();
        verify(repo, times(1)).findCustomerByUsername(userDetails.getUsername());
        verify(cartMapper,times(1)).responseFromModelOne(cart);





    }
    @Test
    public void testSeeProfile() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        Customer customer = Customer.builder()
                .username("Ivan")
                .password("1234")
                .build();

        CustomerResponsePrivate customerResponsePrivate = CustomerResponsePrivate.builder()
                .username("Ivan")
                .password("1234")
                .build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(repo.findCustomerByUsername(userDetails.getUsername())).thenReturn(customer);
        when(customerMapper.responseFromModelPrivate(customer)).thenReturn(customerResponsePrivate);

        CustomerResponsePrivate responsePrivate = customerService.seeProfile(authentication);

        assertEquals("If it is equal",customerResponsePrivate,responsePrivate);
        assertEquals("If it is equal","http://localhost:8084/delivery/customer/deleteAccount",responsePrivate.getDeleteAccount());
        assertEquals("If it is equal","Ivan",responsePrivate.getUsername());
        assertEquals("If it is equal","1234",responsePrivate.getPassword());

        verify(authentication, times(1)).getPrincipal();
        verify(repo, times(1)).findCustomerByUsername(userDetails.getUsername());
        verify(customerMapper,times(1)).responseFromModelPrivate(customer);


    }
    @Test
    public void testDeleteProfile() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        Customer customer = Customer.builder()
                .username("Ivan")
                .password("1234")
                .build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(repo.findCustomerByUsername(userDetails.getUsername())).thenReturn(customer);
        doNothing().when(repo).delete(customer);

        customerService.deleteProfile(authentication);


        Customer findCustomer = customerService.findByUserName(customer.getUsername());
        assertNull(findCustomer);
        verify(authentication, times(1)).getPrincipal();
        verify(repo, times(1)).findCustomerByUsername(userDetails.getUsername());
        verify(repo,times(1)).delete(customer);


    }
    @Test
    public void testForgotPassword() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<String>tokens = new ArrayList<>();
        String token = UUID.randomUUID().toString();
        tokens.add(token);
        Customer customer =Customer.builder()
                .email("nikinikolov2002@gmail.com")
                .tokens(tokens)
                .build();

        when(repo.findByEmail(customer.getEmail())).thenReturn(customer);
        doNothing().when(eventPublisher).publishEvent(any(OnForgotCustomer.class));
        String forgotPassword = customerService.forgotPassword(customer.getEmail(),request);
        assertEquals("If it is equal","Email has been sent!",forgotPassword);


        when(repo.findByEmail(customer.getEmail())).thenReturn(null);
        try {
            customerService.forgotPassword(customer.getEmail(), request);
//            fail("Should have thrown NotFoundCustomerException");
        } catch (NotFoundCustomerException e) {
            assertEquals("If it is equal","There is not such a account!", e.getMessage());
        }

        verify(repo,times(2)).findByEmail(customer.getEmail());

    }
    @Test
    public void testConfirmAndChange() {
        List<String>tokens = new ArrayList<>();
        String token = UUID.randomUUID().toString();
        tokens.add(token);
        Customer customer =Customer.builder()
                .password("1234")
                .tokens(tokens)
                .build();

        when(repo.findByTokensIn(tokens)).thenReturn(customer);
        doNothing().when(customerMapper).updateModelFromDtoPassword("12345",customer);
        when(repo.save(customer)).thenReturn(customer);

        String confirmAndChange = customerService.confirmAndChange(token,"12345");
        assertEquals("If it is equal","The password is changed successfully!",confirmAndChange);

        when(repo.findByTokensIn(tokens)).thenReturn(null);

        try {
            String confirmAndChange1 = customerService.confirmAndChange(token,"12345");
//            fail("Should have thrown NotFoundCustomerException");
        } catch (InvalidToken e) {
            assertEquals("If it is equal","The token is invalid!", e.getMessage());
        }
        verify(repo,times(2)).findByTokensIn(tokens);
        verify(customerMapper,times(1)).updateModelFromDtoPassword("12345",customer);
        verify(repo,times(1)).save(customer);

    }
    @Test
    public void testUpdateCustomer() {
        Customer customer = Customer.builder()
                .username("Ivan")
                .address("Sofia")
                .build();
        CustomerUpdateRequest customerDto = CustomerUpdateRequest.builder()
                .address("Plovdiv")
                .build();

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(repo.findCustomerByUsername(userDetails.getUsername())).thenReturn(customer);
        doNothing().when(customerMapper).updateModelFromDto(customerDto, customer);
        when(repo.save(customer)).thenReturn(customer);

        String updateCustomer = customerService.updateCustomer(authentication, customerDto);

        verify(authentication, times(1)).getPrincipal();
        verify(repo, times(1)).findCustomerByUsername(userDetails.getUsername());
        verify(customerMapper, times(1)).updateModelFromDto(customerDto, customer);
        verify(repo, times(1)).save(customer);

        assertEquals("Update Customer", "It is updated successfully!", updateCustomer);

    }

    @Test
    public void testRegisterCustomer() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Customer customer =Customer.builder()
                .username("Ivan")
                .email("nikinikolov2002@gmail.com")
                .build();
        CustomerCreateRequest customerDto = CustomerCreateRequest.builder()
                .username("Ivan")
                .email("nikinikolov2002@gmail.com")
                .build();

        when(customerMapper.modelFromCreateRequest(customerDto)).thenReturn(customer);
        when(repo.save(customer)).thenReturn(customer);
//        doNothing().when(eventPublisher).publishEvent(new OnRegistrationCompleteEventCustomer(customer, request.getLocale(),  request.getContextPath()));

        String customerCreate = customerService.registerUser(customerDto,request);

        verify(customerMapper,times(1)).modelFromCreateRequest(customerDto);
        verify(repo,times(1)).save(customer);
//        verify(eventPublisher, times(1)).publishEvent(new OnRegistrationCompleteEventCustomer(customer, request.getLocale(),  request.getContextPath()));


        assertEquals("Create Customer","Registration Successfully!", customerCreate);


    }





}
