package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {
    private Ingredient ingredient1;

    @BeforeEach
    void runBefore() {
        ingredient1 = new Ingredient("Butter", 250, "grams");
    }

    @Test
    void testGetName() {
        assertEquals("Butter", ingredient1.getName());
    }

    @Test
    void testGetAmount() {
        assertEquals(250, ingredient1.getAmount());
    }

    @Test
    void testGetUnit() {
        assertEquals("grams", ingredient1.getUnit());
    }

}
