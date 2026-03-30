package restaurant.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import restaurant.entity.Dish;
import restaurant.entity.Ingredient;
import restaurant.enums.CategoryEnum;
import restaurant.enums.DishTypeEnum;

import java.util.List;

@Repository
public class DishRepository {

    private final JdbcTemplate jdbcTemplate;

    public DishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Dish> findAllDishes() {
        String sql = "SELECT id, name, dish_type, selling_price FROM dish ORDER BY id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Dish dish = new Dish();
            dish.setId(rs.getInt("id"));
            dish.setName(rs.getString("name"));

            String typeStr = rs.getString("dish_type");
            if (typeStr != null) {
                try {
                    dish.setDishType(DishTypeEnum.valueOf(typeStr.toUpperCase()));
                } catch (Exception e) {
                    dish.setDishType(DishTypeEnum.START);
                }
            }

            Object priceObj = rs.getObject("selling_price");
            if (priceObj != null) {
                dish.setSellingPrice(rs.getDouble("selling_price"));
            }
            return dish;
        });
    }

    public Dish findDishById(Integer id) {
        if (id == null) return null;

        String dishSql = "SELECT id, name, dish_type, selling_price FROM dish WHERE id = ?";
        Dish dish = jdbcTemplate.queryForObject(dishSql, (rs, rowNum) -> {
            Dish d = new Dish();
            d.setId(rs.getInt("id"));
            d.setName(rs.getString("name"));

            String typeStr = rs.getString("dish_type");
            if (typeStr != null) {
                try {
                    d.setDishType(DishTypeEnum.valueOf(typeStr.toUpperCase()));
                } catch (Exception e) {
                    d.setDishType(DishTypeEnum.START);
                }
            }

            Object sp = rs.getObject("selling_price");
            if (sp != null) d.setSellingPrice(rs.getDouble("selling_price"));
            return d;
        }, id);

        String ingSql = """
            SELECT i.id, i.name, i.price, i.category, di.quantity_required
            FROM ingredient i
            JOIN dishingredient di ON i.id = di.id_ingredient
            WHERE di.id_dish = ?
            ORDER BY i.id
            """;

        List<Ingredient> ingredients = jdbcTemplate.query(ingSql, (rs, rowNum) -> {
            Ingredient ing = new Ingredient();
            ing.setId(rs.getInt("id"));
            ing.setName(rs.getString("name"));
            ing.setPrice(rs.getDouble("price"));
            ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
            ing.setRequiredQuantity(rs.getDouble("quantity_required"));
            return ing;
        }, id);

        dish.setIngredients(ingredients);
        return dish;
    }
}