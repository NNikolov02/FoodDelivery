package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.menu.MenuCreateRequest;
import com.example.fooddelivery.dto.menu.MenuResponse;
import com.example.fooddelivery.dto.menu.MenuUpdateRequest;
import com.example.fooddelivery.dto.pasta.PastaCreateRequest;
import com.example.fooddelivery.dto.pasta.PastaResponse;
import com.example.fooddelivery.dto.pizza.PizzaCreateRequest;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.dto.salad.SaladCreateRequest;
import com.example.fooddelivery.dto.salad.SaladResponse;
import com.example.fooddelivery.dto.steak.SteakCreateRequest;
import com.example.fooddelivery.dto.steak.SteakResponse;
import com.example.fooddelivery.mapping.MenuMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.*;
import com.example.fooddelivery.service.MenuService;
import com.example.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Pizza>pizzas = menu.getPizzas();
        List<Pasta>pastas = menu.getPastas();
        List<Steak>steaks = menu.getSteaks();
        List<Salad>salads = menu.getSalads();
        for(Pizza pizza:pizzas){
            pizza.setUrl("http://localhost:8084/delivery/menu/" +restaurantName + "/pizza/" + pizza.getName());
        }
        for(Pasta pasta:pastas){
            pasta.setUrl("http://localhost:8084/delivery/menu/" +restaurantName + "/pasta/" + pasta.getName());
        }
        for(Steak steak:steaks){
            steak.setUrl("http://localhost:8084/delivery/menu/" +restaurantName + "/steak/" + steak.getName());
        }
        for(Salad salad:salads){
            salad.setUrl("http://localhost:8084/delivery/menu/" +restaurantName + "/salad/" + salad.getName());
        }


        return ResponseEntity.ok(menuMapper.responseFromModelOne(menu));
    }
    @GetMapping(value = "/name/{menuName}")
    public ResponseEntity<MenuResponse>findByName(@PathVariable String menuName){
        Menu menu = menuService.findByName(menuName);
        MenuResponse menuResponse = menuMapper.responseFromModelOne(menu);

        return ResponseEntity.ok(menuResponse);
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
    @GetMapping(value = "/{restaurantName}/pizza/{pizzaName}")
    public ResponseEntity<PizzaResponse>findPizza(@PathVariable String pizzaName,@PathVariable String restaurantName){
        PizzaResponse pizzaResponse = menuService.findPizza(pizzaName,restaurantName);

        return ResponseEntity.ok(pizzaResponse);
    }
    @GetMapping(value = "/{restaurantName}/pasta/{pastaName}")
    public ResponseEntity<PastaResponse>findPasta(@PathVariable String pastaName,@PathVariable String restaurantName){
        PastaResponse pastaResponse = menuService.findPasta(pastaName,restaurantName);

        return ResponseEntity.ok(pastaResponse);
    }
    @GetMapping(value = "/{restaurantName}/salad/{saladName}")
    public ResponseEntity<SaladResponse>findSalad(@PathVariable String saladName,@PathVariable String restaurantName){
        SaladResponse saladResponse = menuService.findSalad(saladName,restaurantName);

        return ResponseEntity.ok(saladResponse);
    }
    @GetMapping(value = "/{restaurantName}/steak/{steakName}")
    public ResponseEntity<SteakResponse>findSteak(@PathVariable String steakName,@PathVariable String restaurantName){
        SteakResponse steakResponse = menuService.findSteak(steakName,restaurantName);

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
    public ResponseEntity<MenuResponse>createMenu(@RequestBody MenuCreateRequest menuDto){
        MenuResponse menuCreate = menuService.create(menuDto);

        return ResponseEntity.ok().body(menuCreate);

    }
    @PostMapping(value = "/{restaurantName}/createPizza")
    public ResponseEntity<MenuResponse>createPizza(@RequestBody PizzaCreateRequest pizzaDto, @PathVariable String restaurantName){
        MenuResponse pizzaResponse = menuService.createPizza(pizzaDto,restaurantName);


        return ResponseEntity.ok().body(pizzaResponse);


    }
    @PostMapping(value = "/{restaurantName}/createPasta")
    public ResponseEntity<MenuResponse> createPasta(@RequestBody PastaCreateRequest pastaDto,@PathVariable String restaurantName){
        MenuResponse pastaResponse = menuService.createPasta(pastaDto,restaurantName);

        return ResponseEntity.ok().body(pastaResponse);


    }
    @PostMapping(value = "/{restaurantName}/createSalad")
    public ResponseEntity<MenuResponse> createSalad(@RequestBody SaladCreateRequest saladDto,@PathVariable String restaurantName){
        MenuResponse menuResponse = menuService.createSalad(saladDto,restaurantName);

        return ResponseEntity.ok().body(menuResponse);


    }
    @PostMapping(value = "/{restaurantName}/createSteak")
    public ResponseEntity<MenuResponse> createSteak(@RequestBody SteakCreateRequest steakDto, @PathVariable String restaurantName){
        MenuResponse menuResponse = menuService.createSteak(steakDto,restaurantName);

        return ResponseEntity.ok().body(menuResponse);


    }
    @PatchMapping(value = "")
    public ResponseEntity<MenuResponse>updateMenu(@RequestBody MenuUpdateRequest menuDto){

        Menu menu = menuService.getMenu();
        MenuResponse menuResponse = menuService.update(menuDto,menu);

        return ResponseEntity.ok().body(menuResponse);
    }


}
