package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.cart.CartDeliveryGuy;
import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.dto.customer.CustomerResponsePrivate;
import com.example.fooddelivery.dto.deliveryguy.DeliverGuyResponsePrivate;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyCreateRequest;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyDto;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyResponse;
import com.example.fooddelivery.dto.restaurant.RestaurantDto;
import com.example.fooddelivery.email.OnForgotCustomer;
import com.example.fooddelivery.error.InvalidToken;
import com.example.fooddelivery.error.NotFoundCustomerException;
import com.example.fooddelivery.error.NotFoundDeiveryGuyException;
import com.example.fooddelivery.mapping.DeliveryGuyMapper;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.repository.*;
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
@SpringBootTest
public class DeliveryGuyServiceTest {

    @MockBean
    private DeliveryGuyMapper deliveryGuyMapper;

    @Autowired
    private DeliveryGuyService deliveryGuyService;

    @MockBean
    private DeliveryGuyRepository repo;
    @MockBean
    private DeliveryGuyPagingRepository pagingRepo;
    @MockBean
    private RestaurantRepository restaurantRepo;
    @MockBean
    private CartService cartService;
    @MockBean
    private ApplicationEventPublisher eventPublisher;



    @Test
    public void testFetchAll() {
        List<DeliveryGuy> deliveryGuyList = new ArrayList<>();
        DeliveryGuy deliveryGuy =  DeliveryGuy.builder()
                .username("Ivan")
                .build();
        deliveryGuyList.add(deliveryGuy);
        Page<DeliveryGuy>deliveryGuys = new PageImpl<>(deliveryGuyList);
        int currentPage  = 0;
        int pageSize = 10;

        when(pagingRepo.findAll(PageRequest.of(currentPage, pageSize))).thenReturn(deliveryGuys);

        Page<DeliveryGuy>deliveryGuyPage = deliveryGuyService.fetchAll(0,10);

        assertEquals("If it is equal",deliveryGuys,deliveryGuyPage);

        verify(pagingRepo, times(1)).findAll(PageRequest.of(currentPage, pageSize));
    }
    @Test
    public void testSave() {
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .build();

        when(repo.save(deliveryGuy)).thenReturn(deliveryGuy);

        DeliveryGuy saveDeliveryGuy = deliveryGuyService.save(deliveryGuy);

        assertEquals("If it is equal",deliveryGuy,saveDeliveryGuy);

        verify(repo,times(1)).save(deliveryGuy);

    }
    @Test
    public void testFindByName() {
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .build();

        when(repo.findByUsername(deliveryGuy.getUsername())).thenReturn(deliveryGuy);

        DeliveryGuy findDeliveryGuy = deliveryGuyService.findByName(deliveryGuy.getUsername());

        assertEquals("If it is equal",deliveryGuy,findDeliveryGuy);

        verify(repo,times(1)).findByUsername(deliveryGuy.getUsername());


    }
    @Test
    public void testDeleteByName() {
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .build();
        doNothing().when(repo).deleteByUsername(deliveryGuy.getUsername());

        deliveryGuyService.deleteByName(deliveryGuy.getUsername());

        DeliveryGuy findDeliveryGuy = deliveryGuyService.findByName(deliveryGuy.getUsername());

        assertNull(findDeliveryGuy);

        verify(repo,times(1)).deleteByUsername(deliveryGuy.getUsername());


    }
    @Test
    public void testSeeCredentials() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .password("1234")
                .build();

        DeliverGuyResponsePrivate deliverGuyResponsePrivate = DeliverGuyResponsePrivate.builder()
                .username("Ivan")
                .password("1234")
                .build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(repo.findByUsername(userDetails.getUsername())).thenReturn(deliveryGuy);
        when(deliveryGuyMapper.responseFromModel(deliveryGuy)).thenReturn(deliverGuyResponsePrivate);

        DeliverGuyResponsePrivate responsePrivate = deliveryGuyService.seeCredentials(authentication);

        assertEquals("If it is equal",deliverGuyResponsePrivate,responsePrivate);
        assertEquals("If it is equal","Ivan",responsePrivate.getUsername());
        assertEquals("If it is equal","1234",responsePrivate.getPassword());

        verify(authentication, times(1)).getPrincipal();
        verify(repo, times(1)).findByUsername(userDetails.getUsername());
        verify(deliveryGuyMapper,times(1)).responseFromModel(deliveryGuy);


    }
    @Test
    public void testViewCart() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        List<Cart>carts = new ArrayList<>();
        Cart cart = Cart.builder()
                .createTime(LocalDateTime.now())
                .build();
        carts.add(cart);

       DeliveryGuy deliveryGuy = DeliveryGuy.builder()
               .firstName("Ivan80")
               .carts(carts)
               .build();
       cart.setDeliveryGuy(deliveryGuy);

        DeliveryGuyDto deliveryGuyDto =DeliveryGuyDto.builder()
                .firstName("Ivan80")
                .build();
        List<CartDeliveryGuy>cartDeliveryGuys = new ArrayList<>();
        CartDeliveryGuy cartDeliveryGuy =CartDeliveryGuy.builder()
                .createTime(LocalDateTime .now())
                .deliveryGuy(deliveryGuyDto)
                .build();
        cartDeliveryGuys.add(cartDeliveryGuy);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(repo.findByUsername(userDetails.getUsername())).thenReturn(deliveryGuy);
        when(deliveryGuyMapper.responseFromCart(carts)).thenReturn(cartDeliveryGuys);

        List<CartDeliveryGuy> cartResponse= deliveryGuyService.viewCarts(authentication);

        assertEquals("If it is equal",cartDeliveryGuys,cartResponse);

        verify(authentication, times(1)).getPrincipal();
        verify(repo, times(1)).findByUsername(userDetails.getUsername());
        verify(deliveryGuyMapper,times(1)).responseFromCart(carts);





    }
    @Test
    public void testCreateDeliveryGuy() {
        List<DeliveryGuy>deliveryGuys = new ArrayList<>();

        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .firstName("Ivan80")
                .build();
        deliveryGuys.add(deliveryGuy);
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .deliveryGuys(deliveryGuys)
                .build();
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .name("Happy")
                .build();
        deliveryGuy.setRestaurant(restaurant);

        DeliveryGuyCreateRequest deliveryGuyDto = DeliveryGuyCreateRequest.builder()
                .username("Ivan")
                .firstName("Ivan80")
                .build();
        DeliveryGuyResponse deliveryGuyResponse = DeliveryGuyResponse
                .builder()
                .firstName("Ivan80")
                .restaurant(restaurantDto)
                .build();
        when(restaurantRepo.findByName(restaurant.getName())).thenReturn(restaurant);
        when(deliveryGuyMapper.modelFromCreateRequest(deliveryGuyDto)).thenReturn(deliveryGuy);
        when(repo.save(deliveryGuy)).thenReturn(deliveryGuy);
        when(restaurantRepo.save(restaurant)).thenReturn(restaurant);
        when(deliveryGuyMapper.responseFromModelOne(deliveryGuy)).thenReturn(deliveryGuyResponse);

        DeliveryGuyResponse deliveryGuyResponseTest = deliveryGuyService.create(deliveryGuyDto,restaurant.getName());

        assertEquals("If it is equal",deliveryGuyResponse,deliveryGuyResponseTest);

        verify(restaurantRepo,times(1)).findByName(restaurant.getName());
        verify(deliveryGuyMapper,times(1)).modelFromCreateRequest(deliveryGuyDto);
        verify(repo,times(1)).save(deliveryGuy);
        verify(restaurantRepo,times(1)).save(restaurant);
        verify(deliveryGuyMapper,times(1)).responseFromModelOne(deliveryGuy);
    }
    @Test
    public void testDeliveredCart() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        Cart cart = Cart.builder()
                .id(UUID.randomUUID())
                .createTime(LocalDateTime .now())
                .build();
        Cart cart1 = Cart.builder()
                .createTime(LocalDateTime .now())
                .build();
        Cart cart2 = Cart.builder()
                .createTime(LocalDateTime .now())
                .build();
        Cart cart3 = Cart.builder()
                .createTime(LocalDateTime .now())
                .build();
        Cart cart4 = Cart.builder()
                .createTime(LocalDateTime .now())
                .build();
        Cart cart5 = Cart.builder()
                .createTime(LocalDateTime .now())
                .build();
        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart);
        carts.add(cart2);
        List<Cart> carts1 = List.of(cart1,cart2);
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .available(true)
                .carts(carts)
                .build();
        cart.setDeliveryGuy(deliveryGuy);
        cart1.setDeliveryGuy(deliveryGuy);
        cart2.setDeliveryGuy(deliveryGuy);


        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(repo.findByUsername(userDetails.getUsername())).thenReturn(deliveryGuy);
        when(cartService.findById(cart.getId().toString())).thenReturn(cart);
        doNothing().when(cartService).removeCart(cart);
        when(repo.save(deliveryGuy)).thenReturn(deliveryGuy);

        String deliveredCart = deliveryGuyService.deliveredCart(authentication,true,cart.getId().toString());

        assertEquals("If it is equal","I have already delivered it!",deliveredCart);
        assertEquals("If it is equal",true,deliveryGuy.isAvailable());
        assertEquals("If it is equal",carts1,deliveryGuy.getCarts());

        carts.add(cart3);
        carts.add(cart4);
        carts.add(cart5);
        cart3.setDeliveryGuy(deliveryGuy);
        cart4.setDeliveryGuy(deliveryGuy);
        cart5.setDeliveryGuy(deliveryGuy);

        String deliveredCart1 = deliveryGuyService.deliveredCart(authentication,true,cart.getId().toString());

        assertEquals("If it is equal",false,deliveryGuy.isAvailable());

        verify(authentication, times(2)).getPrincipal();
        verify(repo, times(2)).findByUsername(userDetails.getUsername());
        verify(repo,times(2)).save(deliveryGuy);



    }
    @Test
    public void testUpdate() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        DeliveryGuy deliveryGuy =DeliveryGuy.builder()
                .username("Ivan")
                .firstName("Ivan")
                .available(true)
                .build();
        DeliveryGuyResponse deliveryGuyResponse =DeliveryGuyResponse.builder()
                .firstName("Ivan")
                .available(false)
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(repo.findByUsername(userDetails.getUsername())).thenReturn(deliveryGuy);
        doNothing().when(deliveryGuyMapper).updateModelFromDto(false,deliveryGuy);
        when(repo.save(deliveryGuy)).thenReturn(deliveryGuy);
        when(deliveryGuyMapper.responseFromModelOne(deliveryGuy)).thenReturn(deliveryGuyResponse);

        DeliveryGuyResponse result = deliveryGuyService.update(false,authentication);

        assertEquals("If it is equal",deliveryGuyResponse,result);

        verify(authentication, times(1)).getPrincipal();
        verify(repo,times(1)).findByUsername(userDetails.getUsername());
        verify(deliveryGuyMapper,times(1)).updateModelFromDto(false,deliveryGuy);
        verify(repo,times(1)).save(deliveryGuy);
        verify(deliveryGuyMapper,times(1)).responseFromModelOne(deliveryGuy);





    }
    @Test
    public void testForgotPassword() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<String>tokens = new ArrayList<>();
        String token = UUID.randomUUID().toString();
        tokens.add(token);
        DeliveryGuy deliveryGuy=DeliveryGuy.builder()
                .email("nikinikolov2002@gmail.com")
                .tokens(tokens)
                .build();

        when(repo.findByEmail(deliveryGuy.getEmail())).thenReturn(deliveryGuy);
        doNothing().when(eventPublisher).publishEvent(any(OnForgotCustomer.class));
        String forgotPassword = deliveryGuyService.forgotPassword(deliveryGuy.getEmail(),request);
        assertEquals("If it is equal","Email has been sent!",forgotPassword);


        when(repo.findByEmail(deliveryGuy.getEmail())).thenReturn(null);
        try {
            deliveryGuyService.forgotPassword(deliveryGuy.getEmail(), request);
//            fail("Should have thrown NotFoundCustomerException");
        } catch (NotFoundDeiveryGuyException e) {
            assertEquals("If it is equal","There is not such a account!", e.getMessage());
        }

        verify(repo,times(2)).findByEmail(deliveryGuy.getEmail());

    }
    @Test
    public void testConfirmAndChange() {
        List<String>tokens = new ArrayList<>();
        String token = UUID.randomUUID().toString();
        tokens.add(token);
        DeliveryGuy deliveryGuy =DeliveryGuy.builder()
                .password("1234")
                .tokens(tokens)
                .build();

        when(repo.findByTokensIn(tokens)).thenReturn(deliveryGuy);
        doNothing().when(deliveryGuyMapper).updateModelFromDtoPassword("12345",deliveryGuy);
        when(repo.save(deliveryGuy)).thenReturn(deliveryGuy);

        String confirmAndChange = deliveryGuyService.confirmAndChange(token,"12345");
        assertEquals("If it is equal","The password is changed successfully!",confirmAndChange);

        when(repo.findByTokensIn(tokens)).thenReturn(null);

        try {
            String confirmAndChange1 = deliveryGuyService.confirmAndChange(token,"12345");
//            fail("Should have thrown NotFoundCustomerException");
        } catch (InvalidToken e) {
            assertEquals("If it is equal","The token is invalid!", e.getMessage());
        }
        verify(repo,times(2)).findByTokensIn(tokens);
        verify(deliveryGuyMapper,times(1)).updateModelFromDtoPassword("12345",deliveryGuy);
        verify(repo,times(1)).save(deliveryGuy);

    }


}
