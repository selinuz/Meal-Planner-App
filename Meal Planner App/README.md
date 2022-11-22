# Meal Planner

## A meal planner and a grocery list maker according to the recipes you know how to make

Meal Planner will be an application where you can put in the 
recipes you know how to make and plan your meals for the week. After 
creating your weekly meal plan, you will also be able to generate a grocery
list according to the ingredients you are going to need. This app will be useful
to anyone who needs to shop for groceries and cook. As a young adult who recently
started living alone, figuring out what to cook each week and what to buy is a
hassle for me. By making this application, I will also be creating a way that will
make my life easier.


## *User Stories* ##
- As a user, I want to be able to add recipes to my "Recipes" list
- As a user, I want to be able to view names of the recipes on my "Recipes" list
- As a user, I want to be able to create a meal plan by selecting several recipes
- As a user, I want to be able to generate a grocery list according to the recipes chosen
- As a user, I want to be able to view the ingredients currently in my grocery list
- As a user, I want to be able to save my recipes and my grocery list
- As a user, I want to be able to load my recipes and my grocery list from file 
- As a user, when I select the quit option from the application menu, I want to be reminded to save my meal plan to file and have the option to do so or not.


## Phase 4: Task 2: ##
- Mon Nov 22 12:47:50 PST 2021
- chicken was added to the ingredients of chicken
- Mon Nov 22 12:47:50 PST 2021
- butter was added to the ingredients of chicken
- Mon Nov 22 12:47:50 PST 2021
- chicken was added to the recipes
- Mon Nov 22 12:48:11 PST 2021
- rice was added to the ingredients of rice
- Mon Nov 22 12:48:11 PST 2021
- butter was added to the ingredients of rice
- Mon Nov 22 12:48:11 PST 2021
- rice was added to the recipes
- Mon Nov 22 12:48:35 PST 2021
- egg was added to the ingredients of omelette
- Mon Nov 22 12:48:35 PST 2021
- butter was added to the ingredients of omelette
- Mon Nov 22 12:48:35 PST 2021
- omelette was added to the recipes
- Mon Nov 22 12:48:37 PST 2021
- rice was deleted from the recipes

## Phase 4: Task 3: ##
If I had more time, I would work on applying the observer design pattern to my project. The bidirectional associations MealPlanUI-MealPlannerUI and RecipesUI-MealPlannerUI are redundant. If I apply the observer design pattern, I could make the MealPlan extent Observable and notify observers whenever a new recipe is added. And MealPlannerUI will implement the interface Observer and everytime if is notified it will update the appropriate JInternal Frame. The argument passed to notifyObservers could be used as a way to decide which JInternalFrame is going to be updated.







