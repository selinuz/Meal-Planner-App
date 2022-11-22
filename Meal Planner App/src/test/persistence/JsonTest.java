package persistence;

import model.Ingredient;
import model.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRecipe(String name, Recipe recipe) {
        assertEquals(name, recipe.getName());
    }

    protected void checkIngredient(String name, int amount, String unit, Ingredient ingredient) {
        assertEquals(name, ingredient.getName());
        assertEquals(amount, ingredient.getAmount());
        assertEquals(unit, ingredient.getUnit());
    }

}