package restaurant.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import restaurant.entity.Ingredient;
import restaurant.entity.StockValue;
import restaurant.enums.CategoryEnum;

import java.util.List;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> findAllIngredients() {
        String sql = "SELECT id, name, price, category FROM ingredient ORDER BY id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Ingredient ing = new Ingredient();
            ing.setId(rs.getInt("id"));
            ing.setName(rs.getString("name"));
            ing.setPrice(rs.getDouble("price"));
            ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
            return ing;
        });
    }

    public StockValue getStockValueAt(Integer ingredientId, java.time.Instant at, String unitStr) {
        if (ingredientId == null || at == null || unitStr == null) {
            throw new IllegalArgumentException("Paramètres at et unit obligatoires");
        }

        String sql = """
            SELECT unit, SUM(CASE WHEN type = 'IN' THEN quantity ELSE -quantity END) as actual_quantity
            FROM stockmovement
            WHERE id_ingredient = ? AND creation_datetime <= ?
            GROUP BY unit
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String unit = rs.getString("unit");
            Double quantity = rs.getDouble("actual_quantity");
            return new StockValue(quantity, restaurant.enums.UnitEnum.valueOf(unit));
        }, ingredientId, java.sql.Timestamp.from(at)).stream().findFirst().orElse(new StockValue(0.0, restaurant.enums.UnitEnum.KG));
    }

    public Ingredient findIngredientById(Integer id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";
        List<Ingredient> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Ingredient ing = new Ingredient();
            ing.setId(rs.getInt("id"));
            ing.setName(rs.getString("name"));
            ing.setPrice(rs.getDouble("price"));
            ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
            return ing;
        }, id);
        return results.isEmpty() ? null : results.get(0);
    }
}
