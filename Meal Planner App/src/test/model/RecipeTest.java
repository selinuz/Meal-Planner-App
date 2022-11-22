package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    private Recipe recipe1;
    private Ingredient ingredient1;
    private Ingredient ingredient2;

    @BeforeEach
    void runBefore() {
        ingredient1 = new Ingredient("White Long Grain Rice", 1, "cups");
        ingredient2 = new Ingredient("Butter", 1, "tbsp");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        recipe1 = new Recipe("Rice");
        recipe1.addIngredient(ingredient1);
        recipe1.addIngredient(ingredient2);
    }

    @Test
    void testGetName() {
        assertEquals("Rice", recipe1.getName());
    }

    @Test
    void testGetIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        assertEquals(ingredients, recipe1.getIngredients());
    }

}
