package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.menu.MenuCreateRequest;
import com.example.fooddelivery.dto.menu.MenuResponse;
import com.example.fooddelivery.dto.menu.MenuUpdateRequest;
import com.example.fooddelivery.dto.pasta.PastaResponse;
import com.example.fooddelivery.dto.pasta.SetPastaRequest;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.dto.pizza.SetPizzaRequest;
import com.example.fooddelivery.dto.salad.SaladResponse;
import com.example.fooddelivery.dto.salad.SetSaladRequest;
import com.example.fooddelivery.dto.steak.SetSteakRequest;
import com.example.fooddelivery.dto.steak.SteakCreateRequest;
import com.example.fooddelivery.dto.steak.SteakResponse;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.*;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.*;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Component
@Service
public class MenuService {

    @Autowired
    private MenuRepository repo;
    @Autowired
    private PizzaRepository pizzaRepo;
    @Autowired
    private PastaRepository pastaRepo;
    @Autowired
    private SaladRepository saladRepo;
    @Autowired
    private SteakRepository steakRepo;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private PizzaMapper pizzaMapper;
    @Autowired
    private SaladMapper saladMapper;
    @Autowired
    private SteakMapper steakMapper;
    @Autowired
    private PastaMapper pastaMapper;

    @Autowired
    private ObjectValidator validator;


    public Menu save(Menu menu) {
        return repo.save(menu);
    }

    public Menu getMenu(){
        String id = "e4ba926d-651d-4f37-8a95-4ebf111d70d2";
        return repo.findById(UUID.fromString(id)).orElse(null);
    }
    public PizzaResponse findPizza(String pizzaName){
        Pizza pizza = pizzaRepo.findByName(pizzaName);
        PizzaResponse pizzaResponse = pizzaMapper.responseFromModel(pizza);
        pizzaResponse.setAddToCart("http://localhost:8084/delivery/cart/pizza?pizzaName=" + pizza.getName());
        pizzaResponse.setUrl("http://localhost:8084/delivery/menu/pizza/" + pizza.getName());

        return pizzaResponse;
    }
    public PastaResponse findPasta(String pastaName){
        Pasta pasta = pastaRepo.findByName(pastaName);
        PastaResponse pastaResponse = pastaMapper.responseFromModel(pasta);
        pastaResponse.setAddToCart("http://localhost:8084/delivery/cart/pasta?pastaName=" + pasta.getName());
        pastaResponse.setUrl("http://localhost:8084/delivery/menu/pasta/" + pasta.getName());

        return pastaResponse;
    }
    public SaladResponse findSalad(String saladName){
        Salad salad = saladRepo.findByName(saladName);
        SaladResponse saladResponse = saladMapper.responseFromModel(salad);
        saladResponse.setAddToCart("http://localhost:8084/delivery/cart/salad?saladName=" + salad.getName());
        saladResponse.setUrl("http://localhost:8084/delivery/menu/salad/" + salad.getName());

        return saladResponse;
    }
    public SteakResponse findSteak(String steakName){
        Steak steak = steakRepo.findByName(steakName);
        SteakResponse steakResponse = steakMapper.responseFromModel(steak);
        steakResponse.setUrl("http://localhost:8084/delivery/menu/steak/" + steak.getName());
        steakResponse.setAddToCart("http://localhost:8084/delivery/cart/steak?steakName=" + steak.getName());

        return steakResponse;
    }
    @Transactional
    public void deletePizzaByName(String name){
        pizzaRepo.deleteByName(name);
    }
    @Transactional
    public void deleteSteakByName(String name){
        steakRepo.deleteByName(name);
    }
    @Transactional
    public void deletePastaByName(String name){
        pastaRepo.deleteByName(name);
    }
    @Transactional
    public void deleteSaladByName(String name){
        saladRepo.deleteByName(name);
    }
    public List<Pizza> getPizza(){
        String id = "e4ba926d-651d-4f37-8a95-4ebf111d70d2";
        return pizzaRepo.findByMenuId(UUID.fromString(id));
    }
    public List<Pasta> getPasta(){
        String id = "e4ba926d-651d-4f37-8a95-4ebf111d70d2";
        return pastaRepo.findByMenuId(UUID.fromString(id));
    }
    public List<Salad> getSalad(){
        String id = "e4ba926d-651d-4f37-8a95-4ebf111d70d2";
        return saladRepo.findByMenuId(UUID.fromString(id));
    }
    public List<Steak> getSteak(){
        String id = "e4ba926d-651d-4f37-8a95-4ebf111d70d2";
        return steakRepo.findByMenuId(UUID.fromString(id));
    }
    public MenuResponse putSteak(Menu findMenu, SetSteakRequest steakDto){


        if(findMenu != null){

            Steak steak = steakRepo.findByName(steakDto.getName());
            List<Steak>steaks = new ArrayList<>();
            steaks.add(steak);
            findMenu.setSteaks(steaks);
            steak.setUrl("http://localhost:8084/delivery/menu/steak/" + steak.getName());
            steak.setMenu(findMenu);
            steakRepo.save(steak);
            repo.save(findMenu);


        }
        MenuResponse menuResponse = menuMapper.responseFromModelOne(findMenu);

        return menuResponse;
    }
    public MenuResponse putPasta(Menu findMenu, SetPastaRequest pastaDto){


        if(findMenu != null){

            Pasta pasta = pastaRepo.findByName(pastaDto.getName());
            List<Pasta>pastas = new ArrayList<>();
            pastas.add(pasta);
            findMenu.setPastas(pastas);
            pasta.setUrl("http://localhost:8084/delivery/menu/pasta/" + pasta.getName());
            pasta.setMenu(findMenu);

            pastaRepo.save(pasta);
            repo.save(findMenu);


        }
        MenuResponse menuResponse = menuMapper.responseFromModelOne(findMenu);

        return menuResponse;
    }
    public MenuResponse putSalad(Menu findMenu, SetSaladRequest saladDto){


        if(findMenu != null){

            Salad salad = saladRepo.findByName(saladDto.getName());
            List<Salad>salads = new ArrayList<>();
            salads.add(salad);
            findMenu.setSalads(salads);
            salad.setUrl("http://localhost:8084/delivery/menu/salad/" + salad.getName());
            salad.setMenu(findMenu);
            saladRepo.save(salad);
            repo.save(findMenu);


        }
        MenuResponse menuResponse = menuMapper.responseFromModelOne(findMenu);

        return menuResponse;
    }
    public MenuResponse putPizza(Menu findMenu, SetPizzaRequest pizzaDto){


        if(findMenu != null){

            Pizza pizza = pizzaRepo.findByName(pizzaDto.getName());
            List<Pizza>pizzas = new ArrayList<>();
            pizzas.add(pizza);
            findMenu.setPizzas(pizzas);
            pizza.setUrl("http://localhost:8084/delivery/menu/pizza/" + pizza.getName());
            pizza.setMenu(findMenu);
            pizzaRepo.save(pizza);
            repo.save(findMenu);


        }
        MenuResponse menuResponse = menuMapper.responseFromModelOne(findMenu);



        return menuResponse;
    }
    public String create(MenuCreateRequest menuDto){

        Map<String, String> validationErrors = validator.validate(menuDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Menu Create", validationErrors);
        }

        Menu menu =menuMapper.modelFromCreateRequest(menuDto);
        repo.save(menu);
        MenuResponse menuResponse = menuMapper.responseFromModelOne(menu);

        return "It is created successfully!";
    }
    public MenuResponse update(MenuUpdateRequest menuDto,Menu menu){
        Map<String, String> validationErrors = validator.validate(menuDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Menu Update", validationErrors);
        }
        menuMapper.updateModelFromDto(menuDto,menu);
        repo.save(menu);
        MenuResponse menuResponse = menuMapper.responseFromModelOne(menu);

        return menuResponse;
    }
    public Menu findById(String id){
        return repo.findById(UUID.fromString(id)).orElse(null);
    }
    public Menu findByName(String name){
        return repo.findByName(name);
    }
//    public Menu findByRestaurantName(String name){
//        return repo.findByRestaurantName(name);
//    }
    public void deleteById(String menuId){
        repo.deleteById(UUID.fromString(menuId));
    }
    public  void deleteByName(String name){
        repo.deleteByName(name);
    }


}
