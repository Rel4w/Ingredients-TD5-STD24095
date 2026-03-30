package restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.entity.Dish;
import restaurant.repository.DishRepository;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishRepository repository;

    public DishController(DishRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        List<Dish> dishes = repository.findAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Integer id) {
        Dish dish = repository.findDishById(id);
        if (dish == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dish);
    }
}
