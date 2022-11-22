package ui;

import model.Ingredient;
import model.MealPlan;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Meal Planner's Recipes user interface
public class RecipesUI extends JInternalFrame {
    private final MealPlan mp;
    private final JList<String> list;
    private final DefaultListModel<String> recipeListModel;
    private final JButton buttonAddRecipe = new JButton("Add Recipe");
    private final JButton buttonDeleteRecipe = new JButton("Delete Recipe");
    private final MealPlannerUI mealPlannerUI;

    // EFFECTS: constructs recipes ui with the given recipes
    public RecipesUI(MealPlan mp, MealPlannerUI mpui) {
        super("Recipes", true, false, false, false);
        this.mp = mp;
        mealPlannerUI = mpui;

        setLocation(0, 0);
        setVisible(true);

        recipeListModel = new DefaultListModel<>();
        for (Recipe recipe: mp.getRecipes()) {
            recipeListModel.addElement(recipe.getName());
        }

        list = new JList<>(recipeListModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.NORTH);

        add(buttonDeleteRecipe, BorderLayout.WEST);
        deleteRecipeAction();
        add(buttonAddRecipe, BorderLayout.EAST);
        addRecipeAction();
    }

    // MODIFIES: recipeListModel, theRecipes
    // EFFECTS: deletes the recipe from the recipes when delete button is pressed
    private void deleteRecipeAction() {
        buttonDeleteRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                recipeListModel.removeElementAt(selectedIndex);
                mp.removeRecipe(selectedIndex);
                mealPlannerUI.updateMealPlan(mp);
            }
        });
    }

    // EFFECTS: creates the add recipe ui and if add recipe button is pressed, adds that recipe to the list
    private void addRecipeAction() {
        buttonAddRecipe.addActionListener(new ActionListener() {
            final AddRecipeUI newRecipeUI = new AddRecipeUI();
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, newRecipeUI,
                        "Please enter the ingredients name, amount and unit", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String name = newRecipeUI.getTextName().getText();
                    Recipe recipe = new Recipe(name);
                    for (Ingredient ingredient: newRecipeUI.getIngredients()) {
                        recipe.addIngredient(ingredient);
                    }
                    recipeListModel.addElement(recipe.getName());
                    mp.addRecipe(recipe);
                    mealPlannerUI.updateMealPlan(mp);
                }
            }
        });
    }

    // Add Recipe user interface
    public class AddRecipeUI extends JPanel {
        private static final int WIDTH = 400;
        private static final int HEIGHT = 200;
        private final JLabel labelName = new JLabel("Name: ");
        private final JLabel labelIngredients = new JLabel("Ingredients: ");
        private final JTextField textName = new JTextField(20);
        private final JButton buttonAddIngredient = new JButton("Add Ingredient");
        private final JButton buttonDeleteIngredient = new JButton("Delete Ingredient");
        private final GridBagConstraints constraints = new GridBagConstraints();
        private final JPanel newPanel = new JPanel(new GridBagLayout());
        private JList<String> ingredientsList;
        private List<Ingredient> ingredients;
        private DefaultListModel<String> ingredientsListModel;

        // EFFECTS: constructs a panel to add recipes
        public AddRecipeUI() {
            setSize(WIDTH, HEIGHT);
            setLocation(WIDTH, 0);
            setVisible(true);
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);
            addName();
            addIngredients();
            add(newPanel);
            pack();
        }

        public JTextField getTextName() {
            return textName;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        // MODIFIES: newPanel
        // EFFECTS: creates the section to add the name of the recipe
        private void addName() {
            constraints.gridx = 0;
            constraints.gridy = 0;
            newPanel.add(labelName, constraints);
            constraints.gridx = 1;
            newPanel.add(textName, constraints);
        }

        // MODIFIES: newPanel
        // EFFECTS: creates the section to show the ingredients of the recipe
        private void addIngredients() {
            constraints.gridx = 0;
            constraints.gridy = 1;
            newPanel.add(labelIngredients, constraints);
            constraints.gridx = 1;

            ingredients = new ArrayList<>();
            ingredientsListModel = new DefaultListModel<>();
            ingredientsList = new JList<>(ingredientsListModel);
            JScrollPane listScrollPane = new JScrollPane(ingredientsList);
            newPanel.add(listScrollPane, constraints);

            constraints.gridx = 1;
            constraints.gridy = 2;
            newPanel.add(buttonAddIngredient, constraints);
            new AddIngredientAction();

            constraints.gridx = 0;
            constraints.gridy = 2;
            newPanel.add(buttonDeleteIngredient, constraints);
            deleteIngredientAction();
        }

        // represents the Add Ingredient pop-up menu
        private class AddIngredientAction {
            JTextField ingredientName = new JTextField(6);
            JTextField ingredientUnit = new JTextField(3);
            JSpinner ingredientAmount = new JSpinner((new SpinnerNumberModel(1, 1, 1000, 1)));

            // MODIFIES: ingredientsListModel, ingredients
            // EFFECTS: if add ingredient button is pressed, adds that ingredient to the ingredients
            AddIngredientAction() {
                buttonAddIngredient.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JPanel myPanel = new JPanel();
                        myPanel.add(new JLabel("Name:"));
                        myPanel.add(ingredientName);
                        myPanel.add(new JLabel("Amount:"));
                        myPanel.add(ingredientAmount);
                        myPanel.add(new JLabel("Unit:"));
                        myPanel.add(ingredientUnit);

                        int result = JOptionPane.showConfirmDialog(null, myPanel,
                                "Please enter the ingredients name, amount and unit", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            String strName = ingredientName.getText();
                            int intAmount = (int) ingredientAmount.getValue();
                            String strUnit = ingredientUnit.getText();
                            ingredientsListModel.addElement(strName + " -> " + intAmount + " " + strUnit);
                            ingredients.add(new Ingredient(strName, intAmount, strUnit));
                        }
                    }
                }
                );
            }
        }

        // MODIFIES: ingredientsListModel, ingredients
        // EFFECTS: if delete ingredient button is pressed, deletes that ingredient from the ingredients
        private void deleteIngredientAction() {
            buttonDeleteIngredient.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = ingredientsList.getSelectedIndex();
                    ingredientsListModel.removeElementAt(selectedIndex);
                    ingredients.remove(selectedIndex);
                }
            });
        }
    }
}
