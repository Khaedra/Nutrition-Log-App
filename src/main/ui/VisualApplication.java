package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import model.Daily;
import model.Goal;
import model.GoalList;
import model.Meal;
import model.NutritionLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Code for generating a GUI nutrition log application
public class VisualApplication {

    private static final String JSON_STORE = "./data/goallog.json";
    private static final String JSON_STORE2 = "./data/daylog.json";
    private NutritionLog alltime;
    private GoalList goallist;
    private JsonWriter jsonWriter;
    private JsonWriter jsonWriter2;
    private JsonReader jsonReader;
    private JsonReader jsonReader2;

    // MODIFIES: this
    // EFFECTS: Initializes variables, readers and writers, and displays main menu
    public VisualApplication() throws FileNotFoundException {

        init();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonWriter2 = new JsonWriter(JSON_STORE2);
        jsonReader = new JsonReader(JSON_STORE);
        jsonReader2 = new JsonReader(JSON_STORE2);
        displayMenu();

    }

    // MODIFIES: this
    // EFFECTS: Initializes variables
    public void init() {
        alltime = new NutritionLog();
        goallist = new GoalList();
    }

    // EFFECTS: Displays main menu with buttons
    // NOTE: doesn't make sense to decompose this method. Too long even using
    // multiple helper methods. Serves as the main starting point with all options.
    @SuppressWarnings("methodlength")
    public void displayMenu() {

        JFrame frame = initFrame("Nutrition Log Application");

        JLabel title = new JLabel("Welcome to the Nutrition Log");
        title.setPreferredSize(new Dimension(650, 100));
        title.setFont(new java.awt.Font("Times New Roman", 1, 18));

        JButton newday = new JButton("New Day");
        JButton selectday = new JButton("Select Day");
        JButton addgoal = new JButton("Add Goal");
        JButton viewgoal = new JButton("Manage and View Goals");
        JButton weekly = new JButton("Weekly Overview");
        JButton all = new JButton("All Time Overview");
        JButton save = new JButton("Save log to file");
        JButton load = new JButton("Load log from file");
        JButton quit = new JButton("Quit application");
        JLabel nodays = new JLabel("No days added. Please add a day.");
        JLabel nogoals = new JLabel("You have no goals. Try adding one!");
        JLabel saved = new JLabel("Saved log to " + JSON_STORE);
        JLabel notsaved = new JLabel("Unable to write to file " + JSON_STORE);
        JLabel loaded = new JLabel("Loaded log from " + JSON_STORE);
        JLabel notloaded = new JLabel("Unable to read from file: " + JSON_STORE);

        frame.add(title);
        frame.add(newday);
        frame.add(selectday);
        frame.add(addgoal);
        frame.add(viewgoal);
        frame.add(weekly);
        frame.add(all);
        frame.add(save);
        frame.add(load);
        frame.add(quit);
        frame.setVisible(true);

        newday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newDay();
            }
        });

        selectday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alltime.size() == 0) {
                    frame.add(nodays);
                    frame.revalidate();
                    frame.repaint();

                } else {
                    displayDays();
                }

            }
        });

        addgoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goalMenu();

            }
        });

        viewgoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (goallist.getSize() == 0) {
                    frame.add(nogoals);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    viewGoalProgress();
                }

            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter2.open();
                    jsonWriter.writeGoalList(goallist);
                    jsonWriter2.writeNutritionLog(alltime);
                    jsonWriter.close();
                    jsonWriter2.close();
                    frame.add(saved);
                    frame.revalidate();
                    frame.repaint();
                } catch (FileNotFoundException f) {
                    frame.add(notsaved);
                    frame.revalidate();
                    frame.repaint();
                }

            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    goallist = jsonReader.readGoalList();
                    alltime = jsonReader2.readNutritionLog();
                    frame.add(loaded);
                    frame.revalidate();
                    frame.repaint();
                } catch (IOException f) {
                    frame.add(notloaded);
                    frame.revalidate();
                    frame.repaint();
                }

            }
        });
        weekly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alltime.size() == 0) {
                    frame.add(nodays);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    viewWeekly();
                }

            }
        });

        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alltime.size() == 0) {
                    frame.add(nodays);
                    frame.revalidate();
                    frame.repaint();
                } else {

                    viewAllTime();
                }
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    // EFFECTS: generic method to initialize a basic frame with a title
    public JFrame initFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setSize(750, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        return frame;
    }

    // MODIFIES: this
    // EFFECTS: creates a new frame with a prompt to enter the date of a new day
    // NOTE: Can't shorten this method since it uses the value of the text field to
    // check conditionals. Must all be in the same method
    @SuppressWarnings("methodlength")
    public void newDay() {
        JFrame newdaymenu = initFrame("Create new day");
        JLabel enterdate = new JLabel("Enter Date: ");
        JLabel duplicatedate = new JLabel("Duplicate date. Try again.");
        JLabel invalid = new JLabel("Invalid");
        JTextField date = new JTextField(10);
        // JButton create = new JButton("Add Day");
        JButton create = createButton("Add Day", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean duplicate = false;

                if (date.getText().isEmpty()) {
                    newdaymenu.add(invalid);
                    newdaymenu.revalidate();
                    newdaymenu.repaint();
                } else {
                    for (Daily d : alltime.getNutritionLog()) {
                        if (d.getDate().equals(date.getText())) {
                            newdaymenu.add(duplicatedate);
                            newdaymenu.revalidate();
                            newdaymenu.repaint();
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate) {
                        Daily day = new Daily(date.getText());
                        alltime.add(day);
                        newdaymenu.revalidate();
                        newdaymenu.repaint();
                        displayDayMenu(day);
                        newdaymenu.dispose();
                    }
                }
            }
        });

        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newdaymenu.dispose();
            }
        });
        newdaymenu.add(enterdate);
        newdaymenu.add(date);
        newdaymenu.add(create);
        newdaymenu.add(back);

    }

    // EFFECTS: generic method to create a button with an action listener and effect
    // when pressed
    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    // EFFECTS: Displays the menu that has options to do with each day.
    // NOTE: Including multiple helper methods won't get this method to under 25
    // lines.
    @SuppressWarnings("methodlength")
    public void displayDayMenu(Daily day) {
        JFrame displaydaymenu = initFrame(day.getDate());

        JButton addmeal = new JButton("Add a meal");
        JButton editmeal = new JButton("Edit a meal");
        JButton deletemeal = new JButton("Delete a meal");
        JButton viewmacros = new JButton("View Daily Macros");
        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaydaymenu.dispose();
            }
        });
        JLabel nomeals = new JLabel("No meals added. Please add a meal.");
        displaydaymenu.add(addmeal);
        displaydaymenu.add(editmeal);
        displaydaymenu.add(deletemeal);
        displaydaymenu.add(viewmacros);
        displaydaymenu.add(back);

        addmeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMeal(day);
            }
        });

        editmeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (day.getSize() == 0) {
                    displaydaymenu.add(nomeals);
                    displaydaymenu.revalidate();
                    displaydaymenu.repaint();
                } else {
                    editMeals(day);
                }
            }
        });

        viewmacros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewDailyMacros(day, goallist);
            }
        });

        deletemeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (day.getSize() == 0) {
                    displaydaymenu.add(nomeals);
                    displaydaymenu.revalidate();
                    displaydaymenu.repaint();
                } else {
                    deleteMeals(day);
                }
            }
        });

    }

    // REQUIRES: calories, carbs, proteins, and fats must be integer values.
    // MODIFIES: this
    // EFFECTS: Displays prompts for user to enter specific meal details.
    // NOTE: Hard to decompose this method since the labels and fields need to be in
    // a specific order. As well, needs the inputs from the fields to create the
    // meal
    @SuppressWarnings("methodlength")
    public void addMeal(Daily day) {
        JFrame addMealMenu = initFrame("Add Meal");

        JLabel enterfood = new JLabel("Enter food item: ");
        JTextField foodname = new JTextField(10);
        JLabel entercalories = new JLabel("Enter Calories:  ");
        JTextField caloriesField = new JTextField(13);
        JLabel entercarbs = new JLabel("Enter carbohydrates (grams): ");
        JTextField carbField = new JTextField(10);
        JLabel enterprotein = new JLabel("Enter protein (grams): ");
        JTextField proteinField = new JTextField(10);
        JLabel enterfat = new JLabel("Enter fat (grams): ");
        JTextField fatField = new JTextField(10);
        JLabel success = new JLabel("Meal Added! ");
        JButton addMealButton = new JButton("Add Meal");
        JButton back = new JButton("Back");
        ImageIcon imageIcon = new ImageIcon("src/resources/image.png");
        Image originalImage = imageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(600, 350, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel image = new JLabel(scaledIcon);

        JLabel reference = new JLabel("Image sourced from Health.mil");

        addMealMenu.setLayout(new FlowLayout());
        addMealMenu.add(enterfood);
        addMealMenu.add(foodname);
        addMealMenu.add(entercalories);
        addMealMenu.add(caloriesField);
        addMealMenu.add(entercarbs);
        addMealMenu.add(carbField);
        addMealMenu.add(enterprotein);
        addMealMenu.add(proteinField);
        addMealMenu.add(enterfat);
        addMealMenu.add(fatField);
        addMealMenu.add(addMealButton); 
        addMealMenu.add(back);
        addMealMenu.add(image);
        addMealMenu.add(reference);

        addMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Meal meal = new Meal(foodname.getText(), Integer.parseInt(caloriesField.getText()),
                        Integer.parseInt(carbField.getText()), Integer.parseInt(proteinField.getText()),
                        Integer.parseInt(fatField.getText()));
                day.addMeal(meal);
                addMealMenu.add(success);
                addMealMenu.revalidate();
                addMealMenu.repaint();
                addMealButton.setEnabled(false);
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMealMenu.dispose();
            }
        });

    }

    // EFFECTS: Displays all days currently added as buttons
    public void displayDays() {

        JFrame alldays = new JFrame("All Days");
        alldays.setSize(750, 750);
        alldays.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        alldays.setVisible(true);

        JLabel select = new JLabel("Select a Date: ");
        alldays.setLayout(new FlowLayout());
        alldays.add(select);

        for (int i = 0; i < alltime.size(); i++) {
            int counter = i;
            JButton d = new JButton(alltime.get(i).getDate());
            alldays.add(d);
            alldays.revalidate();
            alldays.repaint();

            d.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayDayMenu(alltime.get(counter));
                    alldays.dispose();
                }
            });
        }

    }

    // MODIFIES: this
    // EFFECTS: displays list of current meals as buttons and prompts user to select
    // which to edit.
    // NOTE: this method is 26 lines long...
    @SuppressWarnings("methodlength")
    public void editMeals(Daily day) {
        JFrame allmeals = initFrame(day.getDate() + " meals");
        JLabel select = new JLabel("Select a meal you wish to edit: ");
        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allmeals.dispose();
            }
        });
        allmeals.add(select);
        allmeals.add(back);
        for (int i = 0; i < day.getSize(); i++) {
            int index = i;
            JButton d = createButton(day.getMealString(i), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    day.remove(index);
                    editMenu(day, index);
                    allmeals.dispose();
                }
            });
            allmeals.add(d);
            allmeals.revalidate();
            allmeals.repaint();
        }
    }

    // REQUIRES: calories, carbs, proteins, and fats must be integer values.
    // MODIFIES: this
    // EFFECTS: prompts user to enter the new details of the meal they are editing.
    // NOTE: Same problem as other methods that use the value of fields. Hard to
    // decompose. Order of fields and labels is intertwined
    @SuppressWarnings("methodlength")
    public void editMenu(Daily day, int index) {
        JFrame addMealMenu = initFrame("Edit Meal");

        JLabel enterfood = new JLabel("Enter food item: ");
        JTextField foodname = new JTextField(10);
        JLabel entercalories = new JLabel("Enter Calories:  ");
        JTextField caloriesField = new JTextField(13);
        JLabel entercarbs = new JLabel("Enter carbohydrates (grams): ");
        JTextField carbField = new JTextField(10);
        JLabel enterprotein = new JLabel("Enter protein (grams): ");
        JTextField proteinField = new JTextField(10);
        JLabel enterfat = new JLabel("Enter fat (grams): ");
        JTextField fatField = new JTextField(10);
        JLabel success = new JLabel("Meal Edited! ");
        JButton addMealButton = new JButton("Edit Meal");
        JButton back = new JButton("Back");

        addMealMenu.add(enterfood);
        addMealMenu.add(foodname);
        addMealMenu.add(entercalories);
        addMealMenu.add(caloriesField);
        addMealMenu.add(entercarbs);
        addMealMenu.add(carbField);
        addMealMenu.add(enterprotein);
        addMealMenu.add(proteinField);
        addMealMenu.add(enterfat);
        addMealMenu.add(fatField);
        addMealMenu.add(addMealButton);
        addMealMenu.add(back);

        addMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Meal meal = new Meal(foodname.getText(), Integer.parseInt(caloriesField.getText()),
                        Integer.parseInt(carbField.getText()), Integer.parseInt(proteinField.getText()),
                        Integer.parseInt(fatField.getText()));
                day.addMealSpecific(index, meal);
                addMealMenu.add(success);
                addMealMenu.revalidate();
                addMealMenu.repaint();
                addMealButton.setEnabled(false);
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMealMenu.dispose();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Displays list of meals as buttons and prompts user to select which
    // to delete
    public void deleteMeals(Daily day) {
        JFrame allmeals = initFrame(day.getDate() + " meals");

        JLabel select = new JLabel("Select a meal you wish to delete: ");

        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allmeals.dispose();
            }
        });

        allmeals.add(select);
        allmeals.add(back);

        deleteAction(allmeals, day);
    }

    // EFFECTS: deletes meal user selects
    public void deleteAction(JFrame allmeals, Daily day) {
        JLabel success = new JLabel("Meal Successfully Deleted: ");
        for (int i = 0; i < day.getSize(); i++) {
            int index = i;
            JButton d = new JButton(day.getMealString(i));
            allmeals.add(d);
            allmeals.revalidate();
            allmeals.repaint();

            d.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    day.remove(index);
                    allmeals.add(success);
                    allmeals.revalidate();
                    allmeals.repaint();
                    d.setEnabled(false);
                }
            });
        }
    }

    // EFFECTS: returns the total amount of calories, carbs, proteins, or fat within
    // a day
    public int calculateTotal(Daily day, String macro) {
        int total = 0;
        for (Meal m : day.getLog()) {
            if (macro.equals("Calories")) {
                total += m.getCalories();
            }
            if (macro.equals("Carb")) {
                total += m.getCarbohydrates();
            }
            if (macro.equals("Protein")) {
                total += m.getProteins();
            }
            if (macro.equals("Fat")) {
                total += m.getFats();
            }
        }
        return total;
    }

    // EFFECTS: displays the current daily macros based on meals added to current
    // day.
    public void viewDailyMacros(Daily day, GoalList goallist) {

        JFrame allmeals = initFrame("View Macros");

        JButton add = new JButton("Add Macros To Goals");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMacrosToGoals(allmeals, day);
                add.setEnabled(false);
            }
        });
        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allmeals.dispose();
            }
        });
        addDailyMacroLabels(allmeals, day);
        allmeals.add(add);
        allmeals.add(back);
    }

    // EFFECTS: Displays actual macro label.
    public void addDailyMacroLabels(JFrame allmeals, Daily day) {
        final int totalCalories = calculateTotal(day, "Calories");
        final int totalCarbs = calculateTotal(day, "Carb");
        final int totalProtein = calculateTotal(day, "Protein");
        final int totalFat = calculateTotal(day, "Fat");
        JLabel calLabel = new JLabel("Total Calories: " + totalCalories);
        JLabel carbLabel = new JLabel("Total Carbohydrates: " + totalCarbs);
        JLabel proteinLabel = new JLabel("Total Protein: " + totalProtein);
        JLabel fatLabel = new JLabel("Total Fats: " + totalFat);
        allmeals.add(calLabel);
        allmeals.add(carbLabel);
        allmeals.add(proteinLabel);
        allmeals.add(fatLabel);
    }

    // MODIFIES: this
    // EFFECTS: Actually adds the macros to existing goals and progresses value.
    public void addMacrosToGoals(JFrame allmeals, Daily day) {
        final int totalCalories = calculateTotal(day, "Calories");
        final int totalCarbs = calculateTotal(day, "Carb");
        final int totalProtein = calculateTotal(day, "Protein");
        final int totalFat = calculateTotal(day, "Fat");
        JLabel success = new JLabel("Macros added to goals!");

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
        allmeals.add(success);
        allmeals.revalidate();
        allmeals.repaint();

    }

    // MODIFIES: this
    // EFFECTS: Prompts the user to select a type of goal to add.
    // NOTE: this is literally the shortest I can make this method.
    @SuppressWarnings("methodlength")
    public void goalMenu() {

        JFrame addgoal = initFrame("Add Goal");

        JLabel type = new JLabel("Select a type of goal: ");

        JButton calgoal = createButton("Calorie Goal", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calorieGoal(addgoal);
            }
        });
        JButton carbgoal = createButton("Carb Goal", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carbGoal(addgoal);
            }
        });

        JButton progoal = createButton("Protein Goal", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proGoal(addgoal);
            }
        });
        JButton fatgoal = createButton("Fat Goal", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fatGoal(addgoal);
            }
        });

        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addgoal.dispose();
            }
        });
        addgoal.add(type);
        addgoal.add(calgoal);
        addgoal.add(carbgoal);
        addgoal.add(progoal);
        addgoal.add(fatgoal);
        addgoal.add(back);

    }

    // REQUIRES: calField must be an integer.
    // EFFECTS: prompts the user to enter the values of their calorie goal.
    public void calorieGoal(JFrame addgoal) {

        JLabel whatcal = new JLabel("What is your calorie goal?");
        JTextField calField = new JTextField(10);
        JLabel calsuccess = new JLabel("Calorie Goal Added!");
        JButton calButton = new JButton("Add Calorie Goal");

        addgoal.add(whatcal);
        addgoal.add(calField);
        addgoal.add(calButton);
        addgoal.revalidate();
        addgoal.repaint();

        calButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addgoal.add(calsuccess);
                Goal calorie = new Goal("Calorie Goal", Integer.parseInt(calField.getText()));
                goallist.addGoal(calorie);
                calButton.setEnabled(false);
                addgoal.revalidate();
                addgoal.repaint();
            }
        });
    }

    // REQUIRES: carbField must be an integer.
    // EFFECTS: prompts the user to enter the values of their carb goal.
    public void carbGoal(JFrame addgoal) {
        JLabel whatcarb = new JLabel("What is your carb goal (grams?");
        JTextField carbField = new JTextField(10);
        JLabel carbsuccess = new JLabel("Carbohydrate Goal Added!");
        JButton carbButton = new JButton("Add Carb Goal");
        addgoal.add(whatcarb);
        addgoal.add(carbField);
        addgoal.add(carbButton);
        addgoal.revalidate();
        addgoal.repaint();

        carbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addgoal.add(carbsuccess);
                Goal carbohydrate = new Goal("Carb Goal", Integer.parseInt(carbField.getText()));
                goallist.addGoal(carbohydrate);
                carbButton.setEnabled(false);
                addgoal.revalidate();
                addgoal.repaint();
            }
        });

    }

    // REQUIRES: proField must be an integer.
    // EFFECTS: prompts the user to enter the values of their protein goal.
    public void proGoal(JFrame addgoal) {
        JLabel whatpro = new JLabel("What is your protein goal (grams)?");
        JTextField proField = new JTextField(10);
        JLabel prosuccess = new JLabel("Potein Goal Added!");
        JButton proButton = new JButton("Add Protein Goal");

        addgoal.add(whatpro);
        addgoal.add(proField);
        addgoal.add(proButton);
        addgoal.revalidate();
        addgoal.repaint();

        proButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addgoal.add(prosuccess);
                Goal protein = new Goal("Protein Goal", Integer.parseInt(proField.getText()));
                goallist.addGoal(protein);
                proButton.setEnabled(false);
                addgoal.revalidate();
                addgoal.repaint();
            }
        });
    }

    // REQUIRES: fatField must be an integer.
    // EFFECTS: prompts the user to enter the values of their fat goal.
    public void fatGoal(JFrame addgoal) {
        JLabel whatfat = new JLabel("What is your fat goal (grams)?");
        JTextField fatField = new JTextField(10);
        JLabel fatsuccess = new JLabel("Fat Goal Added!");
        JButton fatButton = new JButton("Add Fat Goal");
        addgoal.add(whatfat);
        addgoal.add(fatField);
        addgoal.add(fatButton);
        addgoal.revalidate();
        addgoal.repaint();

        fatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addgoal.add(fatsuccess);
                Goal fat = new Goal("Fat Goal", Integer.parseInt(fatField.getText()));
                goallist.addGoal(fat);
                fatButton.setEnabled(false);
                addgoal.revalidate();
                addgoal.repaint();
            }
        });
    }

    // EFFECTS: displays main goal menu with options to manage goals.
    // NOTE: this is the shortest I can make this method
    @SuppressWarnings("methodlength")
    public void viewGoalProgress() {
        JFrame viewgoals = initFrame("View Goals");
        JButton edit = createButton("Edit a Goal", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editmenu();
                viewgoals.dispose();
            }
        });
        JButton delete = createButton("Delete a Goal", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMenu();
                viewgoals.dispose();
            }
        });
        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewgoals.dispose();
            }
        });
        displayGoalGraphs(viewgoals);
        viewgoals.add(edit);
        viewgoals.add(delete);
        viewgoals.add(back);

    }

    // EFFECTS: Displays a progress bar of the users goals
    public void displayGoalGraphs(JFrame viewgoals) {
        for (int i = 0; i < goallist.getSize(); i++) {
            JLabel g = new JLabel(goallist.getGoal(i).toString());
            JProgressBar progressBar = new JProgressBar(0, goallist.getGoal(i).getGoal());
            progressBar.setValue(goallist.getGoal(i).getGoalProgress());
            progressBar.setStringPainted(true);
            viewgoals.add(g);
            viewgoals.add(progressBar);
            viewgoals.revalidate();
            viewgoals.repaint();
        }
    }

    // EFFECTS: displays goals as buttons and prompts user to select one to edit.
    public void editmenu() {
        JFrame editgoals = initFrame("Edit Goals");
        JLabel select = new JLabel("Select a goal you wish to edit");
        editgoals.add(select);
        for (int i = 0; i < goallist.getSize(); i++) {
            Goal goal = goallist.getGoal(i);
            JButton g = createButton(goallist.getGoal(i).toString(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editGoalFunction(editgoals, goal);
                }
            });
            editgoals.add(g);
            editgoals.revalidate();
            editgoals.repaint();
        }
    }

    // REQUIRES: progressField and goalField must be integers.
    // EFFECTS: displays the fields for editing current goal.
    // NOTE: Same issue with other methods which use fields. Inconvenient to split
    // into two methods.
    @SuppressWarnings("methodlength")
    public void editGoalFunction(JFrame editgoals, Goal goal) {
        JLabel newgoal = new JLabel("What is your new goal?");
        JLabel goalprogress = new JLabel("What is the current goal progress?");
        JTextField progressField = new JTextField(10);
        JTextField goalField = new JTextField(10);
        JButton confirm = new JButton("Confirm");
        JLabel success = new JLabel("Goal Successfully Edited");
        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editgoals.dispose();
                viewGoalProgress();
            }
        });
        editgoals.add(newgoal);
        editgoals.add(goalField);
        editgoals.add(goalprogress);
        editgoals.add(progressField);
        editgoals.add(confirm);
        editgoals.add(back);
        editgoals.revalidate();
        editgoals.repaint();

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goal.setGoal(Integer.parseInt(goalField.getText()));
                goal.setGoalProgress(Integer.parseInt(progressField.getText()));
                editgoals.add(success);
                confirm.setEnabled(false);
                editgoals.revalidate();
                editgoals.repaint();
            }
        });
    }

    // EFFECTS: displays goals as buttons and prompts user to delete.
    public void deleteMenu() {
        JFrame deletegoals = initFrame("Delete Goals");

        JLabel select = new JLabel("Select goal you wish to delete");
        JButton back = createButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletegoals.dispose();
            }
        });
        deletegoals.add(select);
        deleteGoalFunction(deletegoals);
        deletegoals.add(back);
    }

    // EFFECTS: deletes goal and displays "goal successfully deleted".
    public void deleteGoalFunction(JFrame deletegoals) {
        JLabel success = new JLabel("Goal Successfully Deleted!");
        for (int i = 0; i < goallist.getSize(); i++) {
            int j = i;
            JButton g = new JButton(goallist.getGoal(i).toString());
            deletegoals.add(g);
            deletegoals.revalidate();
            deletegoals.repaint();
            g.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    goallist.removeGoal(j);
                    deletegoals.add(success);
                    g.setEnabled(false);
                    deletegoals.revalidate();
                    deletegoals.repaint();
                }

            });
        }

    }

    // EFFECTS: Displays the weekly overview of days and meals.
    public void viewWeekly() {
        JFrame weekly = initFrame("View Weekly");
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weekly.dispose();
            }
        });
        viewWeeklyFunction(weekly);
        weekly.add(back);
    }

    // EFFECTS: decides number of days to display.
    public void viewWeeklyFunction(JFrame weekly) {
        if (alltime.size() <= 7) {
            viewToString(alltime.size(), weekly);
        } else {
            viewToString(7, weekly);
        }
    }

    // EFFECTS: Produces label of days and meals.
    public void viewToString(int a, JFrame weekly) {
        for (int i = 0; i < a; i++) {
            JLabel days = new JLabel("Date: " + alltime.get(i).getDate());
            weekly.add(days);
            weekly.revalidate();
            weekly.repaint();
            for (int j = 0; j < alltime.get(i).getSize(); j++) {
                JLabel meals = new JLabel("\t - " + alltime.get(i).getMealString(j));
                weekly.add(meals);
                weekly.revalidate();
                weekly.repaint();
            }
        }
    }

    // EFFECTS: displays all days and meals ever added
    public void viewAllTime() {

        JFrame weekly = initFrame("View All Time");

        JButton back = new JButton("Back");

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weekly.dispose();
            }
        });
        for (int i = 0; i < alltime.size(); i++) {
            JLabel days = new JLabel("Date: " + alltime.get(i).getDate());
            weekly.add(days);
            weekly.revalidate();
            weekly.repaint();
            for (int j = 0; j < alltime.get(i).getSize(); j++) {
                JLabel meals = new JLabel("\t - " + alltime.get(i).getMealString(j));
                weekly.add(meals);
                weekly.revalidate();
                weekly.repaint();
            }
        }

        weekly.add(back);

    }

}
