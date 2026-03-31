package restaurant.repository;

import org.springframework.stereotype.Repository;
import restaurant.entity.Dish;
import restaurant.entity.Ingredient;
import restaurant.enums.CategoryEnum;
import restaurant.enums.DishTypeEnum;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepository {

    private final DataSource dataSource;

    public DishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dish> findAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        String sql = "SELECT id, name, dish_type, selling_price FROM dish ORDER BY id";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dishes.add(mapDish(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishes;
    }

    public Dish findDishById(Integer id) {
        if (id == null) return null;
        String dishSql = "SELECT id, name, dish_type, selling_price FROM dish WHERE id = ?";
        Dish dish = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(dishSql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dish = mapDish(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (dish == null) return null;

        String ingSql = """
            SELECT i.id, i.name, i.price, i.category, di.quantity_required
            FROM ingredient i
            JOIN dishingredient di ON i.id = di.id_ingredient
            WHERE di.id_dish = ?
            ORDER BY i.id
            """;
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(ingSql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingredients.add(mapIngredientWithQty(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dish.setIngredients(ingredients);
        return dish;
    }

    public boolean existsDishById(Integer id) {
        String sql = "SELECT COUNT(*) FROM dish WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<Ingredient> findIngredientsByDishId(
            Integer dishId,
            String ingredientName,
            Double ingredientPriceAround) {

        StringBuilder sql = new StringBuilder("""
            SELECT i.id, i.name, i.price, i.category, di.quantity_required
            FROM ingredient i
            JOIN dishingredient di ON i.id = di.id_ingredient
            WHERE di.id_dish = ?
            """);

        if (ingredientName != null && !ingredientName.isBlank()) {
            sql.append(" AND i.name ILIKE ?");
        }
        if (ingredientPriceAround != null) {
            sql.append(" AND i.price BETWEEN ? AND ?");
        }
        sql.append(" ORDER BY i.id");

        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;
            ps.setInt(index++, dishId);
            if (ingredientName != null && !ingredientName.isBlank()) {
                ps.setString(index++, "%" + ingredientName + "%");
            }
            if (ingredientPriceAround != null) {
                ps.setDouble(index++, ingredientPriceAround - 50);
                ps.setDouble(index++, ingredientPriceAround + 50);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingredients.add(mapIngredientWithQty(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public void updateDishIngredients(Integer dishId, List<Ingredient> newIngredients) {
        String deleteSql = "DELETE FROM dishingredient WHERE id_dish = ?";
        String insertSql = """
            INSERT INTO dishingredient (id_dish, id_ingredient, quantity_required, unit)
            VALUES (?, ?, ?, ?::unit_enum)
            """;
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, dishId);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                for (Ingredient ing : newIngredients) {
                    if (ing.getId() != null) {
                        double qty = ing.getRequiredQuantity() != null ? ing.getRequiredQuantity() : 0.0;
                        ps.setInt(1, dishId);
                        ps.setInt(2, ing.getId());
                        ps.setDouble(3, qty);
                        ps.setString(4, "KG");
                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Dish mapDish(ResultSet rs) throws SQLException {
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
    }

    private Ingredient mapIngredientWithQty(ResultSet rs) throws SQLException {
        Ingredient ing = new Ingredient();
        ing.setId(rs.getInt("id"));
        ing.setName(rs.getString("name"));
        ing.setPrice(rs.getDouble("price"));
        ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
        ing.setRequiredQuantity(rs.getDouble("quantity_required"));
        return ing;
    }
}