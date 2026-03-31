package restaurant.repository;

import org.springframework.stereotype.Repository;
import restaurant.entity.Ingredient;
import restaurant.entity.StockValue;
import restaurant.enums.CategoryEnum;
import restaurant.enums.UnitEnum;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientRepository {

    private final DataSource dataSource;

    public IngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ingredient> findAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT id, name, price, category FROM ingredient ORDER BY id";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapIngredient(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Ingredient findIngredientById(Integer id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapIngredient(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ingredientId);
            ps.setTimestamp(2, Timestamp.from(at));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new StockValue(rs.getDouble("actual_quantity"),
                            UnitEnum.valueOf(rs.getString("unit")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new StockValue(0.0, UnitEnum.KG);
    }

    private Ingredient mapIngredient(ResultSet rs) throws SQLException {
        Ingredient ing = new Ingredient();
        ing.setId(rs.getInt("id"));
        ing.setName(rs.getString("name"));
        ing.setPrice(rs.getDouble("price"));
        ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
        return ing;
    }
}