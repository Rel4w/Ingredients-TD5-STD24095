package restaurant.entity;

import restaurant.enums.UnitEnum;

public class StockValue {
    private Double quantity;
    private UnitEnum unit;

    // Constructeurs
    public StockValue() {}

    public StockValue(Double quantity, UnitEnum unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getters et Setters
    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public UnitEnum getUnit() {
        return unit;
    }

    public void setUnit(UnitEnum unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", quantity != null ? quantity : 0.0, unit != null ? unit : "UNIT");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockValue that = (StockValue) o;

        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        return unit == that.unit;
    }

    @Override
    public int hashCode() {
        int result = quantity != null ? quantity.hashCode() : 0;
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }
}
