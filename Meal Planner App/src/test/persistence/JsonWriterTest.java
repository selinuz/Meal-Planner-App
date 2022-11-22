package persistence;

import model.Ingredient;
import model.MealPlan;
import model.Recipe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MealPlan mp = new MealPlan();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMealPlan() {
        try {
            MealPlan mp = new MealPlan();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMealPlan.json");
            writer.open();
            writer.write(mp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMealPlan.json");
            mp = reader.read();
            assertEquals(0, mp.getRecipes().size());
            assertEquals(0, mp.getGroceryList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMealPlan() {
        try {
            MealPlan mp = new MealPlan();
            Ingredient ingredient = new Ingredient("Butter", 2, "tbsp");
            Recipe r1 = new Recipe("Rice");
            r1.addIngredient(ingredient);
            Recipe r2 = new Recipe("Chicken");
            r2.addIngredient(ingredient);

            mp.addRecipe(r1);
            mp.addRecipe(r2);
            mp.addToGroceryList(r1);
            mp.addToGroceryList(r2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMealPlan.json");
            writer.open();
            writer.write(mp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMealPlan.json");
            mp = reader.read();
            assertEquals(2, mp.getRecipes().size());
            assertEquals(1, mp.getGroceryList().size());
            checkRecipe("Rice", mp.getRecipes().get(0));
            checkRecipe("Chicken", mp.getRecipes().get(1));

            checkIngredient("Butter", 4, "tbsp", mp.getGroceryList().get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
