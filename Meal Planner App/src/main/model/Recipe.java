package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a recipe with its name and ingredients needed
public class Recipe implements Writable {
    private final String name;                    // recipe name
    private final List<Ingredient> ingredients;   // ingredients needed

    public Recipe(String recipeName) {
        this.name = recipeName;
        ingredients = new ArrayList<>();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        EventLog.getInstance().logEvent(new Event(
                ingredient.getName() + " was added to the ingredients of " + name));
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ingredients", ingredientsToJson());
        return json;
    }

    // EFFECTS: returns ingredients in this meal plan's grocery list as a JSON array
    private JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ingredient i : ingredients) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }
}
