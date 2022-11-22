package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an ingredient having a name, amount needed and the unit for that amount
public class Ingredient implements Writable {
    private final String name;            // ingredient's name
    private final int amount;             // the amount needed
    private final String unit;            // the unit of the amount

    //EFFECTS: Ingredient with the given name and the amount needed and unit
    public Ingredient(String name, int amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amount", amount);
        json.put("unit", unit);
        return json;
    }

}
