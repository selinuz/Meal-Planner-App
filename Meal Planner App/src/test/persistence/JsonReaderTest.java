package persistence;

import model.Ingredient;
import model.MealPlan;
import model.Recipe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MealPlan mp = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMealPlan() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMealPlan.json");
        try {
            MealPlan mp = reader.read();
            assertEquals(0, mp.getRecipes().size());
            assertEquals(0, mp.getGroceryList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMealPlan() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMealPlan.json");
        try {
            MealPlan mp = reader.read();
            Ingredient ingredient = new Ingredient("Butter", 3, "tbsp");
            Recipe r1 = new Recipe("Rice");
            r1.addIngredient(ingredient);
            Recipe r2 = new Recipe("Chicken");
            r2.addIngredient(ingredient);

            mp.addRecipe(r1);
            mp.addRecipe(r2);
            mp.addToGroceryList(r1);
            mp.addToGroceryList(r2);

            assertEquals(4, mp.getRecipes().size());
            assertEquals(1, mp.getGroceryList().size());
            checkRecipe("Rice", mp.getRecipes().get(0));
            checkRecipe("Chicken", mp.getRecipes().get(1));
            checkIngredient("Butter", 10, "tbsp", mp.getGroceryList().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
