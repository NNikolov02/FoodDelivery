package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.deliveryguy.DeliverGuyResponsePrivate;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyCreateRequest;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyResponse;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyUpdateRequest;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.DeliveryGuyMapper;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.repository.DeliveryGuyPagingRepository;
import com.example.fooddelivery.repository.DeliveryGuyRepository;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Service
public class DeliveryGuyService {

    @Autowired
    private DeliveryGuyRepository repo;
    @Autowired
    private DeliveryGuyMapper deliveryGuyMapper;

    @Autowired
    private ObjectValidator validator;
    @Autowired
    private DeliveryGuyPagingRepository pagingRepo;

    public DeliveryGuy save(DeliveryGuy deliveryGuy){
        return repo.save(deliveryGuy);
    }

    public Page<DeliveryGuy> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }
    public DeliveryGuy findByName(String name){
        return repo.findByUsername(name);
    }
    @Transactional
    public void deleteByName(String name){
        repo.deleteByUsername(name);
    }
    public List<DeliveryGuy>findByAvailable(boolean available){
        return repo.findByAvailable(available);
    }
    public List<DeliveryGuy>findByRestaurant(String name){
        return repo.findByRestaurantName(name);
    }
    public DeliveryGuy findByCartId(String id){
        return repo.findByCartId(UUID.fromString(id)).getCart().getDeliveryGuy();
    }
    public DeliverGuyResponsePrivate seeCredentials(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DeliveryGuy authenticatedDeliveryGuy = repo.findByUsername(userDetails.getUsername());

        DeliverGuyResponsePrivate deliverGuyResponsePrivate = deliveryGuyMapper.responseFromModel(authenticatedDeliveryGuy);

        return deliverGuyResponsePrivate;
    }
    public DeliveryGuyResponse create(DeliveryGuyCreateRequest deliveryGuyDto){
        Map<String, String> validationErrors = validator.validate(deliveryGuyDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid DeliveryGuy Create", validationErrors);
        }
        DeliveryGuy create = deliveryGuyMapper.modelFromCreateRequest(deliveryGuyDto);
        DeliveryGuy deliveryGuy = repo.save(create);
        DeliveryGuyResponse deliveryGuyResponse = deliveryGuyMapper.responseFromModelOne(deliveryGuy);

        return deliveryGuyResponse;

    }
    @Transactional
    public DeliveryGuyResponse update(boolean available, Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DeliveryGuy authenticatedDeliveryGuy = repo.findByUsername(userDetails.getUsername());
        deliveryGuyMapper.updateModelFromDto(available,authenticatedDeliveryGuy);
        DeliveryGuy deliveryGuy = repo.save(authenticatedDeliveryGuy);

        DeliveryGuyResponse deliveryGuyResponse = deliveryGuyMapper.responseFromModelOne(deliveryGuy);

        return deliveryGuyResponse;

    }

}
