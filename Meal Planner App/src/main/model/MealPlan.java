package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a meal plan having recipes chosen and a grocery list according to the recipes
public class MealPlan implements Writable {
    private List<Recipe> recipes;
    private List<Ingredient> groceryList;

    // Creates an empty meal plan and an empty grocery list
    public MealPlan() {
        recipes = new ArrayList<>();
        groceryList = new ArrayList<>();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public List<Ingredient> getGroceryList() {
        return groceryList;
    }

    public void setRecipes(List<Recipe> newRecipes) {
        recipes = newRecipes;
    }

    public void setGroceryList(List<Ingredient> newGroceryList) {
        groceryList = newGroceryList;
    }

    // MODIFIES: this
    // EFFECTS: adds the given recipe to the meal plan
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        EventLog.getInstance().logEvent(new Event(
                recipe.getName() + " was added to the recipes"));
    }

    // MODIFIES: this
    // EFFECTS: removes the given recipe from the meal plan
    public void removeRecipe(int index) {
        Recipe r = recipes.get(index);
        recipes.remove(index);
        EventLog.getInstance().logEvent(new Event(
                r.getName() + " was deleted from the recipes"));
    }

    // REQUIRES: if ingredients have the same name, they also have the same unit
    // MODIFIES: this
    // EFFECTS: adds the ingredients needed for the given recipe to the grocery list
    //          if an ingredient is already in the grocery list, adds the amount needed to the current number
    public void addToGroceryList(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        nextItem:
        for (Ingredient ingredient : ingredients) {
            for (int i = 0; i < groceryList.size(); i++) {
                Ingredient ingredient1 = groceryList.get(i);
                if (ingredient1.getName().equals(ingredient.getName())) {
                    int newAmount = ingredient1.getAmount() + ingredient.getAmount();
                    groceryList.set(i, new Ingredient(ingredient.getName(), newAmount, ingredient.getUnit()));
                    break nextItem;
                }
            }
            groceryList.add(ingredient);
            EventLog.getInstance().logEvent(new Event(ingredient.getName() + " was added to the grocery list"));
        }
    }

    // EFFECTS: Finds the recipe in the list by its name and calls addToGroceryList to add the ingredients to groceries
    public void addRecipeToMealPlan(String name) {
        for (Recipe recipe: recipes) {
            if (recipe.getName().equals(name)) {
                addToGroceryList(recipe);
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("recipes", recipesToJson());
        json.put("groceryList", groceryListToJson());
        return json;
    }

    // EFFECTS: returns recipes in this meal plan as a JSON array
    private JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe r : recipes) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns grocery list in this meal plan as a JSON array
    private JSONArray groceryListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ingredient i : groceryList) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

}




