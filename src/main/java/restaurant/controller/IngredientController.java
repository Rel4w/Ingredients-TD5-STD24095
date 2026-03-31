package restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.entity.Ingredient;
import restaurant.entity.StockValue;
import restaurant.repository.IngredientRepository;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientRepository repository;

    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = repository.findAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<StockValue> getStockAt(
            @PathVariable Integer id,
            @RequestParam("at") String atStr,
            @RequestParam("unit") String unit) {

        try {
            java.time.Instant at = java.time.Instant.parse(atStr);
            StockValue stock = repository.getStockValueAt(id, at, unit);
            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id) {
        Ingredient ingredient = repository.findIngredientById(id);
        if (ingredient == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(ingredient);
    }
}