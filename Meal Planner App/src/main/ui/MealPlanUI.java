package ui;

import model.MealPlan;
import model.Recipe;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

// Meal Planner's Meal Plan user interface
public class MealPlanUI extends JInternalFrame {
    private static final int WIDTH = 230;
    private static final int HEIGHT = 200;
    private final MealPlan mp;
    private final MealPlannerUI mealPlannerUI;
    private final JButton buttonCreateGroceryList = new JButton("Create My Grocery List");

    // EFFECTS: constructs meal plan ui with the given recipes
    public MealPlanUI(MealPlan mp, MealPlannerUI mpui) {
        super("Weekly Meal Plan");
        this.mp = mp;
        mealPlannerUI = mpui;
        setSize(WIDTH, HEIGHT);
        setLocation(270, 0);
        setVisible(true);
        setUpTable();
    }

    // EFFECTS: when create grocery list button is pressed, it passes each recipe on the table to addRecipeToMealPlan
    private void createGroceryListAction(JTable table) {
        buttonCreateGroceryList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < table.getRowCount(); i++) {
                    String recipeName = String.valueOf(table.getValueAt(i, 1));
                    mp.addRecipeToMealPlan(recipeName);
                    mealPlannerUI.updateGroceryList(mp);
                }
            }
        });
    }

    // EFFECTS: sets up the table with the names of the days and the recipe chosen for that day
    private void setUpTable() {
        Object[][] data = {
                {"Sunday", " "},
                {"Monday", " "},
                {"Tuesday", " "},
                {"Wednesday", " "},
                {"Thursday", " "},
                {"Friday", " "},
                {"Saturday", " "}
        };
        String[] columnNames = {" ", " "};
        JTable table = new JTable(data, columnNames);
        add(table);
        setUpRecipesColumn(table, table.getColumnModel().getColumn(1));
        add(buttonCreateGroceryList, BorderLayout.SOUTH);
        createGroceryListAction(table);
    }

    // EFFECTS: sets up recipe column for the table with a comboBox to chose from recipes in the meal plan
    private void setUpRecipesColumn(JTable table, TableColumn column) {
        Vector<String> recipeNames = new Vector<String>();

        for (Recipe recipe: mp.getRecipes()) {
            recipeNames.add(recipe.getName());
        }
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addItem(" ");
        for (String name : recipeNames) {
            comboBox.addItem(name);
        }
        column.setCellEditor(new DefaultCellEditor(comboBox));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click to view the recipes");
        column.setCellRenderer(renderer);
    }

}
