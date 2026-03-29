package restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.entity.Ingredient;
import restaurant.repository.IngredientRepository;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientRepository repository;

    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    // Endpoint demandé dans le TD5
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = repository.findAllIngredients();
        return ResponseEntity.ok(ingredients);
    }
}