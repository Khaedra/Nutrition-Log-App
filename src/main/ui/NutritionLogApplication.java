package ui;

import java.util.Scanner;
import java.util.ArrayList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Daily;
import model.Meal;
import model.NutritionLog;
import model.Goal;
import model.GoalList;

//Some methods and ideas from Lab 3.2
//Handles the creation and procedures of the nutrition app. 
public class NutritionLogApplication {

    private static final String JSON_STORE = "./data/goallog.json";
    private static final String JSON_STORE2 = "./data/daylog.json";
    private Scanner scanner;
    private boolean programRunning;
    private NutritionLog alltime;
    private GoalList goallist;
    private JsonWriter jsonWriter;
    private JsonWriter jsonWriter2;
    private JsonReader jsonReader;
    private JsonReader jsonReader2;

    // MODIFIES: this
    // EFFECTS: prints out the welcome menu
    public NutritionLogApplication() throws FileNotFoundException {
        init();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonWriter2 = new JsonWriter(JSON_STORE2);
        jsonReader = new JsonReader(JSON_STORE);
        jsonReader2 = new JsonReader(JSON_STORE2);
        divider();
        System.out.println("Welcome to the Nutrition Log!");
        divider();

        while (programRunning) {
            displayMenu();
        }

    }

    // MODIFIES: this
    // INITIALIZES VARAIBLES
    public void init() {
        programRunning = true;
       alltime = new NutritionLog();
        goallist = new GoalList();
        scanner = new Scanner(System.in);

    }

    // Shows menu commands and takes input
    public void displayMenu() {
        showProgress();
        showMenuCommands();
        int input = scanner.nextInt();
        performCommand(input);

    }

    // List of all available commands
    public void showMenuCommands() {
        System.out.println("Select an option:");
        System.out.println("1 - New Day");
        System.out.println("2 - Select Day");
        // TODO THE REST OF THESE
        System.out.println("3 - Add Goal");
        System.out.println("4 - View Goal Progress"); // MUST HAVE A MARK AS COMPELETED NOTIFICATION
        System.out.println("5 - Weekly Overview");
        System.out.println("6 - All Time Overview");
        System.out.println("7 - Save log to file");
        System.out.println("8 - Load log from file");
        System.out.println("9 - Quit application");

    }

    // TODO: Show Daily Progress Goal
    public void showProgress() {

    }

    public void performCommand(int input) {

        switch (input) {
            case 1:
                newDay();
                break;
            case 2:
                displayDays();
                break;

            case 3:
                goalMenu();
                break;

            case 4:
                viewGoalProgress();
                break;

            case 7:
                saveLog();
                break;
            
            case 8: 
                loadLog();
                break;

            case 9: 
                quit();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    public void quit() {
        programRunning = false; 
        System.out.println("See you again!");
    }

    public void newDay() {
        System.out.println("Enter Date: ");
        scanner.nextLine();
        String date = scanner.nextLine();

        Daily day = new Daily(date);
        alltime.add(day);

        System.out.println("New Day Successfully Added!");

        displayDayMenu(day);

    }

    public void displayDayMenu(Daily day) {
        System.out.println("1 - Add a meal");
        System.out.println("2 - Edit meal");
        System.out.println("3 - Delete a meal");
        System.out.println("4 - View Daily Macros"); // TODO

        int input = scanner.nextInt();

        handleDayInput(day, input, goallist);

    }

    public void handleDayInput(Daily day, int input, GoalList goallist) {
        switch (input) {
            case 1:
                addMeal(day);
                break;

            case 2:
                editMeals(day);
                break;

            case 3:
                deleteMeals(day);
                break;

            case 4:
                viewDailyMacros(day, goallist);
                break;

            default:
                System.out.println("Invalid option inputted. Please try again.");

                // TODO: add other cases
        }

    }

    public void addMeal(Daily day) {
        System.out.println("Enter food item: ");
        // TODO cant have multiple words
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Enter calories: ");

        int calories = scanner.nextInt();
        System.out.println("Enter carbohydrates (grams): ");
        int carbohydrates = scanner.nextInt();
        System.out.println("Enter protein (grams): ");
        int protein = scanner.nextInt();
        System.out.println("Enter fat (grams): ");
        int fat = scanner.nextInt();

        Meal meal = new Meal(name, calories, carbohydrates, protein, fat);
        day.addMeal(meal);

        System.out.println("Meal Added!");

    }

    public void displayDays() {

        if (alltime.size() == 0) {
            System.out.println("No days added. Please add a day.");
        } else {
            System.out.println("Select a Date: ");

            for (int i = 0; i < alltime.size(); i++) {
                System.out.println((alltime.get(i)).getDate());
            }
            scanner.nextLine();
            String input = scanner.nextLine();

            goIntoDays(input);
        }

    }

    public void goIntoDays(String input) {
        boolean found = false;

        for (int i = 0; i < alltime.size(); i++) {
            if (input.equals(alltime.get(i).getDate())) {
                displayDayMenu(alltime.get(i));
                found = true;
                break;
            } else if (i == (alltime.size() - 1) && found == false) {
                System.out.println("Invalid date entered. Please try again.");
                displayDays();
            }

        }

    }

    public void editMeals(Daily day) {
        if (day.getSize() == 0) {
            System.out.println("No meals added. Please add a meal.");
        } else {
            editAction(day);
        }
    }

    public void editAction(Daily day) {
        System.out.println("Type the name of the meal you wish to edit (type back to go back): ");
        for (int i = 0; i < day.getSize(); i++) {
            System.out.println(day.getMealString(i));
        }
        scanner.nextLine();
        String input = scanner.nextLine();
        Boolean found = false;
        for (int j = 0; j < day.getSize(); j++) {
            if (input.equals((day.getMeal(j)).getName())) {
                int placeholder = j;
                day.remove(placeholder);
                editMenu(day, placeholder);
                found = true;
                break;
            } else if (input.equals("back")) {
                displayDayMenu(day);
            } else if (j == (day.getSize() - 1) && found == false) {
                System.out.println("Invalid meal choice. Please try again.");
                editMeals(day);
            }
        }
    }

    public void editMenu(Daily day, int index) {
        System.out.println("Enter food item: ");
        //scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Enter calories: ");
        int calories = scanner.nextInt();
        System.out.println("Enter carbohydrates (grams): ");
        int carbohydrates = scanner.nextInt();
        System.out.println("Enter protein (grams): ");
        int protein = scanner.nextInt();
        System.out.println("Enter fat (grams): ");
        int fat = scanner.nextInt();

        Meal meal = new Meal(name, calories, carbohydrates, protein, fat);
        day.addMealSpecific(index, meal);

        System.out.println("Meal Edited!");
    }

    // MODIFIES: this
    // EFFECTS: If no meals are logged today, print no meals added. If a valid meal
    // is selected, bring up menu to edit meal details.

    public void deleteMeals(Daily day) {
        if (day.getSize() == 0) {
            System.out.println("No meals added. Please add a meal.");
        } else {
            deleteAction(day);
        }
    }

    // MODIFIES: this
    // EFFECTS: If valid meal is typed, delete meal from day.
    // If "back" is typed, return to day menu.
    // If invalid meal is typed, display error message.
    public void deleteAction(Daily day) {
        System.out.println("Type the name of the meal you wish to delete (type back to go back): ");
        for (int i = 0; i < day.getSize(); i++) {
            System.out.println(day.getMealString(i));
        }
        scanner.nextLine();
        String input = scanner.nextLine();
        boolean found = false;
        for (int j = 0; j < day.getSize(); j++) {
            if (input.equals((day.getMeal(j)).getName())) {
                int placeholder = j;
                day.remove(placeholder);
                System.out.println("Meal Succesfully Deleted.");
                found = true;
                break;
            } else if (input.equals("back")) {
                displayDayMenu(day);
            } else if (j == (day.getSize() - 1) && found == false) {
                System.out.println("Invalid meal choice. Please try again. ");
                deleteMeals(day);
            }
        }
    }

    
    // DISPLAYS A SUMMARY OF TOTAL MACROS EATEN IN SELECTED DAY
    
    public void viewDailyMacros(Daily day, GoalList goallist) {
        int totalCalories = 0;
        int totalCarbs = 0;
        int totalProtein = 0;
        int totalFat = 0;

        for (int i = 0; i < day.getSize(); i++) {
            totalCalories += day.getMeal(i).getCalories();
            totalCarbs += day.getMeal(i).getCarbohydrates();
            totalProtein += day.getMeal(i).getProteins();
            totalFat += day.getMeal(i).getFats();
        }

        System.out.println("Total Calories: " + totalCalories);
        System.out.println("Total Carbohydrates: " + totalCarbs);
        System.out.println("Total Protein: " + totalProtein);
        System.out.println("Total Fats: " + totalFat);
        System.out.println("add - Add macros to goals");
        System.out.println("back - Return to menu");
        scanner.nextLine();
        String add = scanner.nextLine();
        if (add.equals("back")) {
            displayMenu();
        } else if (add.equals("add")) {
            for (int i = 0; i < goallist.getSize(); i++) {
                Goal a = goallist.getGoal(i);
                if (a.getObjective().equals("Calorie Goal")) {
                    a.increaseGoalProgress(totalCalories);
                } else if (a.getObjective().equals("Carb Goal")) {
                    a.increaseGoalProgress(totalCarbs);
                } else if (a.getObjective().equals("Protein Goal")) {
                    a.increaseGoalProgress(totalProtein);
                } else if (a.getObjective().equals("Fat Goal")) {
                    a.increaseGoalProgress(totalFat);
                }
            }

            System.out.println("Macros added to goals!");
            displayMenu();
        }

    }

    // MUST HAVE A MARK AS COMPELETED NOTIFICATION
    public void goalMenu() {
        System.out.println("Select a type of goal: ");
        System.out.println("1 - Water Goal");
        System.out.println("2 - Calorie Goal");
        System.out.println("3 - Carb Goal");
        System.out.println("4 - Protein Goal");
        System.out.println("5 - Fat Goal");

        int input = scanner.nextInt();

        handleGoalInput(input);

    }

    public void handleGoalInput(int input) {
        switch (input) {
            case 1:
                waterGoal();
                break;

            case 2:
                calorieGoal();
                break;
            case 3:
                carbGoal();
                break;
            case 4:
                proteinGoal();
                break;
            case 5:
                fatGoal();
                break;
            default:
                System.out.println("Invalid option selected, please try again.");
                break;
        }
    }

    public void waterGoal() {
        System.out.println("What is your water goal per day (liters)?");
        int liter = scanner.nextInt();
        Goal water = new Goal("Water Goal", liter);
        goallist.addGoal(water);
        System.out.println("Water Goal Added!");

    }

    public void calorieGoal() {
        System.out.println("What is your calorie goal?");
        int cal = scanner.nextInt();
        Goal calorie = new Goal("Calorie Goal", cal);
        goallist.addGoal(calorie);
        System.out.println("Calorie Goal Added!");
    }

    public void carbGoal() {
        System.out.println("What is your carb goal (grams)?");
        int carb = scanner.nextInt();
        Goal carbohydrate = new Goal("Carb Goal", carb);
        goallist.addGoal(carbohydrate);
        System.out.println("Carbohydrate Goal Added!");
    }

    public void proteinGoal() {
        System.out.println("What is your protein goal?");
        int pro = scanner.nextInt();
        Goal protein = new Goal("Protein Goal", pro);
        goallist.addGoal(protein);
        System.out.println("Protein Goal Added!");
    }

    public void fatGoal() {
        System.out.println("What is your fat goal?");
        int f = scanner.nextInt();
        Goal fat = new Goal("Fat Goal", f);
        goallist.addGoal(fat);
        System.out.println("Fat Goal Added!");
    }

    public void viewGoalProgress() {

        if (goallist.getSize() == 0) {
            System.out.println("You have no goals. Try adding one!");
        } else {
            for (int i = 0; i < goallist.getSize(); i++) {
                System.out.println(goallist.getGoal(i).toString());
            }

            System.out.println("edit - edit a goal");
            System.out.println("delete - Delete a goal");
            System.out.println("back - Return to main menu");
            scanner.nextLine();
            String input = scanner.nextLine();

            if (input.equals("back")) {
                displayMenu();
            } else if (input.equals("edit")) {
                editGoalMenu();
            } else if (input.equals("delete")) {
                deleteGoalMenu();
            }
        }
    }

    public void deleteGoalMenu() {
        for (int i = 0; i < goallist.getSize(); i++) {
            System.out.println((i + 1) + " - " + goallist.getGoal(i).toString());
        }
        System.out.println("Select goal you wish to delete");
        int input = scanner.nextInt();
        goallist.removeGoal(input - 1);
        System.out.println("Goal Successfully Deleted!");
    }

    public void editGoalMenu() {
        for (int i = 0; i < goallist.getSize(); i++) {
            System.out.println((i + 1) + " - " + goallist.getGoal(i).toString());
        }
        System.out.println("Select goal you wish to edit");
        int input = scanner.nextInt();

        editGoal(input);

    }

    public void editGoal(int input) {
        Goal a = goallist.getGoal(input - 1);
        System.out.println("What is your new goal?");
        int newgoal = scanner.nextInt();
        a.setGoal(newgoal);
        System.out.println("What is the current goal progress?");
        int newprog = scanner.nextInt();
        a.setGoalProgress(newprog);
        System.out.println("Goal Successfully Edited");
    }



    // EFFECTS: saves the workroom to file
    private void saveLog() {
        try {
            jsonWriter.open();
            jsonWriter2.open();
            jsonWriter.writeGoalList(goallist);
            jsonWriter2.writeNutritionLog(alltime);
            jsonWriter.close();
            jsonWriter2.close();
            System.out.println("Saved log to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadLog() {
        try {
            goallist = jsonReader.readGoalList();
            alltime = jsonReader2.readNutritionLog();
            System.out.println("Loaded log from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void divider() {
        System.out.println("-----------------------------");
    }

}
