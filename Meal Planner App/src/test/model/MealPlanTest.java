package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MealPlanTest {

    private MealPlan testMealPlan;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    void runBefore() {
        Ingredient ingredient1 = new Ingredient("White Long Grain Rice", 1, "cups");
        Ingredient ingredient2 = new Ingredient("Butter", 1, "tbsp");

        Ingredient ingredient3 = new Ingredient("Egg", 2, "unit");
        Ingredient ingredient4 = new Ingredient("Butter", 3, "tbsp");

        recipe1 = new Recipe("Rice");
        recipe1.addIngredient(ingredient1);
        recipe1.addIngredient(ingredient2);
        recipe2 = new Recipe("Omelette");
        recipe2.addIngredient(ingredient3);
        recipe2.addIngredient(ingredient4);

        testMealPlan = new MealPlan();

    }

    @Test
    void testAddToMeals() {
        testMealPlan.addRecipe(recipe1);
        testMealPlan.addRecipe(recipe2);
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);
        assertEquals(recipes, testMealPlan.getRecipes());
    }

    @Test
    void testAddToGroceryList() {
        testMealPlan.addToGroceryList(recipe1);
        testMealPlan.addToGroceryList(recipe2);
        List<Ingredient> groceryList = testMealPlan.getGroceryList();
        assertEquals(3, testMealPlan.getGroceryList().size());
        Ingredient butter = groceryList.get(1);
        assertEquals(4, butter.getAmount());
    }

}