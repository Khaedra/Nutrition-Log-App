package ui;

import java.util.Scanner;
import java.util.ArrayList;

import model.Daily;
import model.Meal;


//Some methods and ideas from Lab 3.2
public class NutritionLog {

    private Scanner scanner; 
    private boolean programRunning;

    private ArrayList<Daily> alltime; 


    //MODIFIES: this
    //EFFECTS: prints out the welcome menu
    public NutritionLog() {
        init();
        divider();
        System.out.println("Welcome to the Nutrition Log!");
        divider();

        while (programRunning) {
            displayMenu();
        }

    }

    //MODIFIES: this
    //INITIALIZES VARAIBLES
    public void init() {
        programRunning = true; 
        alltime = new ArrayList<Daily>();
        scanner = new Scanner(System.in);

    }

    //Shows menu commands and takes input
    public void displayMenu() {
        showProgress();
        showMenuCommands();
        int input = scanner.nextInt();
        performCommand(input);


    }

    //List of all available commands
    public void showMenuCommands() {
        System.out.println("Select an option:");
        System.out.println("1 - New Day");
        System.out.println("2 - Select Day");
        //TODO THE REST OF THESE
        System.out.println("3 - Add Goal");
        System.out.println("4 - View Goal Progress"); //MUST HAVE A MARK AS COMPELETED NOTIFICATION
        System.out.println("5 - Weekly Overview");
        System.out.println("6 - All Time Overview");

    }

    //TODO: Show Daily Progress Goal
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
                // case "r":
                //     reviewFlashcards();
                //     break;
                // case "q":
                //     quitApplication();
                //     break;
                default:
                    System.out.println("Invalid option inputted. Please try again.");
            }
        }


    public void newDay() {
        System.out.println("Enter Date: ");
        String date = scanner.next();

        Daily day = new Daily(date);
        alltime.add(day);

        System.out.println("New Day Successfully Added!");

        displayDayMenu(day);

    }

    public void displayDayMenu(Daily day) {
        System.out.println("1 - Add a meal");
        System.out.println("2 - Edit meal"); 
        System.out.println("3 - Delete a meal");
        System.out.println("4 - View Daily Macros"); //TODO

        int input = scanner.nextInt();

        handleDayInput(day, input);

    }

    public void handleDayInput(Daily day, int input) {
        switch(input) {
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
            viewDailyMacros(day);
            break;

            default:
            System.out.println("Invalid option inputted. Please try again.");


            //TODO: add other cases
        }

    }

    public void addMeal(Daily day){
        System.out.println("Enter food item: ");
        //TODO cant have multiple words
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
        }
        else {
            System.out.println("Select a Date: ");

            for (int i = 0; i <alltime.size(); i++) {
                System.out.println((alltime.get(i)).getDate());
            }
            String input = scanner.next();
            
            goIntoDays(input);
        }
        
    }

    public void goIntoDays(String input) {
        boolean found = false; 

        for (int i = 0; i < alltime.size(); i ++) {
            if (input.equals(alltime.get(i).getDate())) {
                displayDayMenu(alltime.get(i));
                found = true; 
                break; 
            }
            else if (i == (alltime.size() - 1) && found == false) {
                System.out.println("Invalid date entered. Please try again.");
                displayDays();
            }
            
        }
        
    }

    public void editMeals(Daily day) {
        if (day.getSize() == 0) {
            System.out.println("No meals added. Please add a meal.");
        }
        else {
            System.out.println("Type the name of the meal you wish to edit (type back to go back): ");
            for(int i = 0; i < day.getSize(); i++) {
                System.out.println(day.getMealString(i));
    
            }
            String input = scanner.next();
            Boolean found = false; 

            for (int j = 0; j < day.getSize(); j ++) {
                if (input.equals((day.getMeal(j)).getName())) {
                    int placeholder = j; 
                    day.remove(placeholder);    
                    editAction(day, placeholder);
                    found = true; 
                    break;
                }
                else if (input.equals("back")){
                   displayDayMenu(day);
                }
                else if (j == (day.getSize() - 1) && found == false ) {
                    System.out.println("Invalid meal choice. Please try again.");
                    editMeals(day);
                }
                
            }
            

        }
        
    }

    public void editAction(Daily day, int index) {
        System.out.println("Enter food item: ");
        scanner.nextLine();
        String name = scanner.next();
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

    //MODIFIES: this
    //EFFECTS: If no meals are logged today, print no meals added. If a valid meal is selected, bring up menu to edit
    // meal details. If "back" is typed, return to day menu. 
    public void deleteMeals(Daily day) {
        if (day.getSize() == 0) {
            System.out.println("No meals added. Please add a meal.");
        }
        else {
            System.out.println("Type the name of the meal you wish to delete (type back to go back): ");
            for(int i = 0; i < day.getSize(); i++) {
                System.out.println(day.getMealString(i));
    
            }
            String input = scanner.next();
            boolean found = false; 
            for (int j = 0; j < day.getSize(); j ++) {
                if (input.equals((day.getMeal(j)).getName())) {
                    int placeholder = j; 
                    day.remove(placeholder);  
                    System.out.println("Meal Succesfully Deleted.");  
                    found = true; 
                    break;
                }
                else if (input.equals("back")){
                    displayDayMenu(day);
                 }
                else if(j == (day.getSize() - 1) && found == false) {
                    System.out.println("Invalid meal choice. Please try again. ");
                    deleteMeals(day);
                }
            }

        }
    }

    //DISPLAYS A SUMMARY OF TOTAL MACROS EATEN IN SELECTED DAY
    public void viewDailyMacros(Daily day) {
        int totalCalories = 0;
        int totalCarbs = 0;
        int totalProtein = 0;
        int totalFat = 0;

        for (int i = 0; i<day.getSize(); i++) {
            totalCalories += day.getMeal(i).getCalories();
            totalCarbs += day.getMeal(i).getCalories();
            totalProtein += day.getMeal(i).getCalories();
            totalFat += day.getMeal(i).getCalories();
        }

        System.out.println("Total Calories: "  + totalCalories);
        System.out.println("Total Carbohydrates: "  + totalCarbs);
        System.out.println("Total Protein: "  + totalProtein);
        System.out.println("Total Fats: "  + totalFat);

        
    }
    


    public void divider () {
        System.out.println("-----------------------------");
    }
    


}
