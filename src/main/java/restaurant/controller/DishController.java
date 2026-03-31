package restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.entity.Dish;
import restaurant.entity.Ingredient;
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
        return ResponseEntity.ok(repository.findAllDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Integer id) {
        Dish dish = repository.findDishById(id);
        if (dish == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(dish);
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<String> updateIngredients(
            @PathVariable Integer id,
            @RequestBody List<Ingredient> ingredients) {

        if (ingredients == null || ingredients.isEmpty()) {
            return ResponseEntity.badRequest().body("Corps de requête obligatoire contenant la liste d'ingrédients");
        }

        Dish existing = repository.findDishById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body("Dish.id=" + id + " is not found");
        }

        repository.updateDishIngredients(id, ingredients);
        return ResponseEntity.ok("Ingrédients mis à jour pour le plat " + id);
    }

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<?> getDishIngredients(
            @PathVariable Integer id,
            @RequestParam(required = false) String ingredientName,
            @RequestParam(required = false) Double ingredientPriceAround) {

        if (!repository.existsDishById(id)) {
            return ResponseEntity.status(404).body("Dish.id=" + id + " is not found");
        }

        List<Ingredient> ingredients = repository.findIngredientsByDishId(
                id, ingredientName, ingredientPriceAround);

        return ResponseEntity.ok(ingredients);
    }
}