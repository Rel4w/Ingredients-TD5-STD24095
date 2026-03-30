package restaurant.entity;

import restaurant.enums.DishTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private Double sellingPrice;
    private List<Ingredient> ingredients = new ArrayList<>();

    public Dish() {}

    public Dish(Integer id, String name, DishTypeEnum dishType) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public DishTypeEnum getDishType() { return dishType; }
    public void setDishType(DishTypeEnum dishType) { this.dishType = dishType; }

    public Double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(Double sellingPrice) { this.sellingPrice = sellingPrice; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = (ingredients != null) ? ingredients : new ArrayList<>();
    }

    public Double getDishCost() {
        if (ingredients == null || ingredients.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Ingredient ing : ingredients) {
            if (ing.getRequiredQuantity() != null && ing.getPrice() != null) {
                total += ing.getPrice() * ing.getRequiredQuantity();
            }
        }
        return total;
    }

    public Double getGrossMargin() {
        if (sellingPrice == null) {
            return null;
        }
        return sellingPrice - getDishCost();
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishType=" + dishType +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}