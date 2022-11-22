package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Meal Planner application user interface
public class MealPlannerUI extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private MealPlan mp;
    private final JDesktopPane desktop;
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;
    private static final String JSON_STORE = "./data/mealPlan.json";
    private RecipesUI recipesUI;
    private GroceryListUI groceriesUI;
    private MealPlanUI mealPlanUI;

    // EFFECTS: runs the meal planner application
    public MealPlannerUI() {
        mp = new MealPlan();
        desktop = new JDesktopPane();

        JFrame frame = new JFrame();
        ImageIcon icon = new ImageIcon("data/mp2.png");
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        showLoadPopUp();
        frame.setVisible(false);

        setContentPane(desktop);
        setTitle("Meal Planner");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        showRecipes();
        showGroceryList();
        showMealPlan();

        showSavePopUp();

        setVisible(true);
    }

    // EFFECTS: creates and shows the pop-up menu for loading from file when the program is started
    private void showLoadPopUp() {
        String[] objButtons = {"No", "Yes"};
        int promptResult = JOptionPane.showOptionDialog(null,
                "Do you want to load from file?", "Load from File",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                objButtons,objButtons[1]);
        if (promptResult == 1) {
            loadMealPlan();
        }
    }

    // EFFECTS: creates and shows the pop-up menu for saving to file while exiting the program
    private void showSavePopUp() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] objButtons = {"Don't Save", "Save"};
                int promptResult = JOptionPane.showOptionDialog(null,
                        "Do you want to save before you exit?", "Save to File",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        objButtons,objButtons[1]);
                if (promptResult == 1) {
                    saveMealPlan();
                }
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    // EFFECTS: prints event log to console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // EFFECTS: creates and shows the RecipesUI
    private void showRecipes() {
        recipesUI = new RecipesUI(mp, this);
        desktop.add(recipesUI);
    }

    // EFFECTS: creates and shows the GroceryListUI
    private void showGroceryList() {
        groceriesUI = new GroceryListUI(mp);
        desktop.add(groceriesUI);
    }

    // EFFECTS: creates and shows the MealPlanUI
    private void showMealPlan() {
        mealPlanUI = new MealPlanUI(mp, this);
        desktop.add(mealPlanUI);
    }

    // EFFECTS: updates meal plan ui
    protected void updateMealPlan(MealPlan mp) {
        this.mp = mp;
        desktop.remove(mealPlanUI);
        showMealPlan();
    }

    // EFFECTS: updates grocery list ui
    protected void updateGroceryList(MealPlan mp) {
        this.mp = mp;
        desktop.remove(groceriesUI);
        showGroceryList();
    }

    // EFFECTS: saves the meal plan to file
    private void saveMealPlan() {
        try {
            jsonWriter.open();
            jsonWriter.write(mp);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the meal plan from file
    private void loadMealPlan() {
        try {
            mp = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: starts the application
    public static void main(String[] args) throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        new MealPlannerUI();
    }

}
