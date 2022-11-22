package ui;

import model.Ingredient;
import model.MealPlan;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Meal Planner's Grocery List user interface
public class GroceryListUI extends JInternalFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    // EFFECTS: constructs grocery list ui with the given groceries
    public GroceryListUI(MealPlan mp) {
        super("Grocery List", true, false, false, false);
        setSize(WIDTH, HEIGHT);
        setLocation(50, 210);
        setVisible(true);

        DefaultListModel listModel = new DefaultListModel();
        for (Ingredient ingredient: mp.getGroceryList()) {
            listModel.addElement(ingredient.getName() + " -> " + ingredient.getAmount() + " " + ingredient.getUnit());
        }

        JList<Recipe> list = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.CENTER);
    }

}
