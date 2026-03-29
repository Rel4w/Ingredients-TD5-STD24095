package restaurant.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import restaurant.entity.Ingredient;
import restaurant.enums.CategoryEnum;

import java.util.List;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // GET /ingredients → liste complète (comme demandé dans le TD5)
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
}
