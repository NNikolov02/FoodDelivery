package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.menu.MenuCreateRequest;
import com.example.fooddelivery.dto.menu.MenuResponse;
import com.example.fooddelivery.dto.menu.MenuUpdateRequest;
import com.example.fooddelivery.dto.pasta.PastaResponse;
import com.example.fooddelivery.dto.pasta.SetPastaRequest;
import com.example.fooddelivery.dto.pizza.PizzaCreateRequest;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.dto.pizza.SetPizzaRequest;
import com.example.fooddelivery.dto.salad.SaladResponse;
import com.example.fooddelivery.dto.salad.SetSaladRequest;
import com.example.fooddelivery.dto.steak.SetSteakRequest;
import com.example.fooddelivery.dto.steak.SteakResponse;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.MenuMapper;
import com.example.fooddelivery.mapping.PizzaMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.*;
import com.example.fooddelivery.service.MenuService;
import com.example.fooddelivery.service.PizzaService;
import com.example.fooddelivery.service.RestaurantService;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private PizzaRepository pizzaRepo;
    @Autowired
    private RestaurantService restaurantService;


    @GetMapping(value = "{restaurantName}")
    public ResponseEntity<MenuResponse> seeMenu(@PathVariable String restaurantName) {
        Restaurant restaurant = restaurantService.findRestaurantByName(restaurantName);
        Menu menu = restaurant.getMenu();

        return ResponseEntity.ok(menuMapper.responseFromModelOne(menu));
    }
    @GetMapping(value = "/pizza")
    public ResponseEntity<List<PizzaResponse>>seePizza(){
        List<Pizza> pizzas = menuService.getPizza();
        List<PizzaResponse>pizzaResponses = menuMapper.responseFromModelPizza(pizzas);
        for(PizzaResponse pizzaResponses1:pizzaResponses) {
            for (Pizza pizza : pizzas) {
                pizzaResponses1.setUrl("http://localhost:8084/delivery/menu/pizza/" + pizza.getName());
            }
        }
        return ResponseEntity.ok().body(pizzaResponses);
    }
    @GetMapping(value = "/pizza/{pizzaName}")
    public ResponseEntity<PizzaResponse>findPizza(@PathVariable String pizzaName){
        PizzaResponse pizzaResponse = menuService.findPizza(pizzaName);

        return ResponseEntity.ok(pizzaResponse);
    }
    @GetMapping(value = "/pasta/{pastaName}")
    public ResponseEntity<PastaResponse>findPasta(@PathVariable String pastaName){
        PastaResponse pastaResponse = menuService.findPasta(pastaName);

        return ResponseEntity.ok(pastaResponse);
    }
    @GetMapping(value = "/salad/{saladName}")
    public ResponseEntity<SaladResponse>findSalad(@PathVariable String saladName){
        SaladResponse saladResponse = menuService.findSalad(saladName);

        return ResponseEntity.ok(saladResponse);
    }
    @GetMapping(value = "/steak/{steakName}")
    public ResponseEntity<SteakResponse>findSteak(@PathVariable String steakName){
        SteakResponse steakResponse = menuService.findSteak(steakName);

        return ResponseEntity.ok(steakResponse);
    }
    @GetMapping(value = "/pasta")
    public ResponseEntity<List<PastaResponse>>seePasta(){
        List<Pasta> pastas = menuService.getPasta();

        List<PastaResponse> pastaResponses = menuMapper.responseFromModelPasta(pastas);
        for(PastaResponse pastaResponses1:pastaResponses) {
            for (Pasta pasta : pastas) {
                pastaResponses1.setUrl("http://localhost:8084/delivery/menu/pasta/" + pasta.getName());
            }
        }
        return ResponseEntity.ok().body(pastaResponses);
    }
    @GetMapping(value = "/salad")
    public ResponseEntity<List<SaladResponse>>seeSalad(){
        List<Salad> salads = menuService.getSalad();

        List<SaladResponse> saladResponses = menuMapper.responseFromModelSalad(salads);
        for(SaladResponse saladResponses1:saladResponses) {
            for (Salad salad : salads) {
                saladResponses1.setUrl("http://localhost:8084/delivery/menu/salad/" + salad.getName());
            }
        }
        return ResponseEntity.ok().body(saladResponses);
    }
    @GetMapping(value = "/steak")
    public ResponseEntity<List<SteakResponse>>seeSteak(){
        List<Steak> steaks = menuService.getSteak();
        List<SteakResponse> steakResponse = menuMapper.responseFromModelSteak(steaks);
        for(SteakResponse steakResponse1:steakResponse) {
            for (Steak steak : steaks) {
                steakResponse1.setUrl("http://localhost:8084/delivery/menu/steak/" + steak.getName());
            }
        }
        return ResponseEntity.ok().body(steakResponse);
    }
    @DeleteMapping("/name")
    public ResponseEntity<String> deleteByName(@PathVariable String name){
        menuService.deleteByName(name);
        return ResponseEntity.ok("It is deleted");
    }
    @DeleteMapping("/pizza/{pizzaName}")
    public ResponseEntity<String> deletePizzaByName(@PathVariable String pizzaName){
        menuService.deletePizzaByName(pizzaName);
        return ResponseEntity.ok("It is deleted");
    }
    @DeleteMapping("/pasta/{pastaName}")
    public ResponseEntity<String> deletePastaByName(@PathVariable String pastaName){
        menuService.deletePastaByName(pastaName);
        return ResponseEntity.ok("It is deleted");
    }
    @DeleteMapping("/salad/{saladName}")
    public ResponseEntity<String> deleteSaladByName(@PathVariable String saladName){
        menuService.deleteSaladByName(saladName);
        return ResponseEntity.ok("It is deleted");
    }
    @DeleteMapping("/steak/{steakName}")
    public ResponseEntity<String> deleteSteakByName(@PathVariable String steakName){
        menuService.deleteSteakByName(steakName);
        return ResponseEntity.ok("It is deleted");
    }



//    @GetMapping("/{menuId}")
//    public ResponseEntity<MenuResponse>findMenu(@PathVariable String menuId){
//        Menu menu = menuService.findById(menuId);
//
//        return ResponseEntity.ok(menuMapper.responseFromModelOne(menu));
//
//    }

    @PostMapping(value = "")
    public ResponseEntity<String>createMenu(@RequestBody MenuCreateRequest menuDto){
       String menuCreate = menuService.create(menuDto);

        return ResponseEntity.ok().body(menuCreate);

    }
    @PutMapping(value = "/pizza")
    public ResponseEntity<MenuResponse>putPizzaInTheMenu(@RequestBody SetPizzaRequest pizzaDto){

        Menu findMenu = menuService.getMenu();
        MenuResponse menuResponse = menuService.putPizza(findMenu,pizzaDto);

        return ResponseEntity.ok().body(menuResponse);

    }
    @PutMapping(value = "/pasta")
    public ResponseEntity<MenuResponse>putPastaInTheMenu(@RequestBody SetPastaRequest pastaDto){

        Menu findMenu = menuService.getMenu();
        MenuResponse menuResponse = menuService.putPasta(findMenu,pastaDto);

        return ResponseEntity.ok().body(menuResponse);

    }
    @PutMapping(value = "/salad")
    public ResponseEntity<MenuResponse>putSaladInTheMenu(@RequestBody SetSaladRequest saladDto){

        Menu findMenu = menuService.getMenu();
        MenuResponse menuResponse = menuService.putSalad(findMenu,saladDto);

        return ResponseEntity.ok().body(menuResponse);

    }
    @PutMapping(value = "/steak")
    public ResponseEntity<MenuResponse>putSteakInTheMenu(@RequestBody SetSteakRequest steakDto){

        Menu findMenu = menuService.getMenu();
        MenuResponse menuResponse = menuService.putSteak(findMenu,steakDto);

        return ResponseEntity.ok().body(menuResponse);

    }
    @PatchMapping(value = "")
    public ResponseEntity<MenuResponse>updateMenu(@RequestBody MenuUpdateRequest menuDto){

        Menu menu = menuService.getMenu();
        MenuResponse menuResponse = menuService.update(menuDto,menu);

        return ResponseEntity.ok().body(menuResponse);
    }


}
