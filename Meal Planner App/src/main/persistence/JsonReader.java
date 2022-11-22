package persistence;

import model.Ingredient;
import model.MealPlan;
import model.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads meal plan from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MealPlan read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMealPlan(jsonObject);
    }

    // This method references code from this [JsonSerializationDemo]
    // Link: [https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo]
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // This method references code from this [JsonSerializationDemo]
    // Link: [https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo]
    // EFFECTS: parses meal plan from JSON object and returns it
    private MealPlan parseMealPlan(JSONObject jsonObject) {
        MealPlan mp = new MealPlan();
        addRecipes(mp, jsonObject);
        addGroceryList(mp, jsonObject);
        return mp;
    }

    // MODIFIES: mp
    // EFFECTS: parses recipes from JSON object and sets it to meal plan's recipes
    private void addRecipes(MealPlan mp, JSONObject jsonObject) {
        JSONArray jsonArrayRecipes = jsonObject.getJSONArray("recipes");
        List<Recipe> recipes = new ArrayList<>();

        for (Object json : jsonArrayRecipes) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipe(recipes, nextRecipe);
        }
        mp.setRecipes(recipes);
    }

    // MODIFIES: mp
    // EFFECTS: parses grocery list from JSON object and sets it to meal plan's grocery list
    private void addGroceryList(MealPlan mp, JSONObject jsonObject) {
        JSONArray jsonArrayGroceryList = jsonObject.getJSONArray("groceryList");
        List<Ingredient> groceryList = new ArrayList<>();

        for (Object json : jsonArrayGroceryList) {
            JSONObject nextIngredient = (JSONObject) json;
            addIngredient(groceryList, nextIngredient);
        }
        mp.setGroceryList(groceryList);
    }

    // MODIFIES: recipes
    // EFFECTS: parses recipe from JSON object and adds recipes
    private void addRecipe(List<Recipe> recipes, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray jsonArrayIngredients = jsonObject.getJSONArray("ingredients");
        List<Ingredient> ingredients = new ArrayList<>();

        for (Object json : jsonArrayIngredients) {
            JSONObject nextIngredient = (JSONObject) json;
            addIngredient(ingredients, nextIngredient);
        }

        Recipe recipe = new Recipe(name);
        for (Ingredient i: ingredients) {
            recipe.addIngredient(i);
        }
        recipes.add(recipe);
    }

    // MODIFIES: ingredients
    // EFFECTS: parses ingredient from JSON object and adds to ingredients
    private void addIngredient(List<Ingredient> ingredients, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int amount = jsonObject.getInt("amount");
        String unit = jsonObject.getString("unit");
        Ingredient ingredient = new Ingredient(name, amount, unit);
        ingredients.add(ingredient);
    }

}
