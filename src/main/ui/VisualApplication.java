package ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

import model.Daily;
import model.GoalList;
import model.Meal;
import model.NutritionLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualApplication {

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

    // Code for generating a GUI nutrition log application
    public VisualApplication() throws FileNotFoundException {

        init();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonWriter2 = new JsonWriter(JSON_STORE2);
        jsonReader = new JsonReader(JSON_STORE);
        jsonReader2 = new JsonReader(JSON_STORE2);
        displayMenu();

    }

    // EFFECTS: Initializes variables
    public void init() {
        programRunning = true;
        alltime = new NutritionLog();
        goallist = new GoalList();
    }

    // EFFECTS: Displays main menu with buttons
    public void displayMenu() {
        JFrame frame = new JFrame("Nutrition Log Application");
        frame.setSize(750, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel title = new JLabel("Welcome to the Nutrition Log");
        title.setPreferredSize(new Dimension(650, 100));
        title.setFont(new java.awt.Font("Times New Roman", 1, 18));

        JButton newday = new JButton("New Day");
        JButton selectday = new JButton("Select Day");
        JButton addgoal = new JButton("Add Goal");
        JButton viewgoal = new JButton("View Goal Progress");
        JButton weekly = new JButton("Weekly Overview");
        JButton all = new JButton("All Time Overview");
        JButton save = new JButton("Save log to file");
        JButton load = new JButton("Load log from file");
        JButton quit = new JButton("Quit application");
        JLabel nodays = new JLabel("No days added. Please add a day.");

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

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    public void newDay() {

        JFrame newdaymenu = new JFrame("Create new day");
        newdaymenu.setSize(750, 750);
        newdaymenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        newdaymenu.setVisible(true);

        JLabel enterdate = new JLabel("Enter Date: ");
        JLabel duplicatedate = new JLabel("Duplicate date. Try again.");
        JLabel success = new JLabel("New Day Successfully Added!");
        JLabel invalid = new JLabel("Invalid");
        JTextField date = new JTextField(10);
        JButton create = new JButton("Add Day");

        newdaymenu.setLayout(new FlowLayout());
        newdaymenu.add(enterdate);
        newdaymenu.add(date);
        newdaymenu.add(create);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean duplicate = false;

                if (date.getText().isEmpty()) {
                    newdaymenu.add(invalid);
                    newdaymenu.revalidate(); // Refresh the frame to show the new label
                    newdaymenu.repaint();
                } else {
                    for (Daily d : alltime.getNutritionLog()) {
                        if (d.getDate().equals(date.getText())) {
                            newdaymenu.add(duplicatedate);
                            newdaymenu.revalidate(); // Refresh the frame to show the new label
                            newdaymenu.repaint();
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate) {
                        Daily day = new Daily(date.getText());
                        alltime.add(day);
                        newdaymenu.add(success);
                        newdaymenu.revalidate(); // Refresh the frame to show the new label
                        newdaymenu.repaint();
                        newdaymenu.setVisible(false);
                        displayDayMenu(day);
                        newdaymenu.dispose();
                    }
                }
            }
        });
    }

    public void displayDayMenu(Daily day) {
        JFrame displaydaymenu = new JFrame(day.getDate());
        displaydaymenu.setSize(750, 750);
        displaydaymenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        displaydaymenu.setVisible(true);

        JButton addmeal = new JButton("Add a meal");
        JButton editmeal = new JButton("Edit a meal");
        JButton deletemeal = new JButton("Delete a meal");
        JButton viewmacros = new JButton("View Daily Macros");
        JButton back = new JButton("Back");
        JButton nomeals = new JButton("No meals added. Please add a meal.");

        displaydaymenu.setLayout(new FlowLayout());
        displaydaymenu.add(addmeal);
        displaydaymenu.add(editmeal);
        displaydaymenu.add(deletemeal);
        displaydaymenu.add(viewmacros);
        displaydaymenu.add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaydaymenu.dispose();
            }
        });

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

    public void addMeal(Daily day) {
        JFrame addMealMenu = new JFrame("Add Meal");
        addMealMenu.setSize(750, 750);
        addMealMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        addMealMenu.setVisible(true);

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
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMealMenu.dispose();
            }
        });

    }

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
                }
            });
        }

    }

    public void editMeals(Daily day) {
        JFrame allmeals = new JFrame(day.getDate() + " meals");
        allmeals.setSize(750, 750);
        allmeals.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        allmeals.setVisible(true);

        JLabel select = new JLabel("Select a meal you wish to edit: ");
        JButton back = new JButton("Back");
        allmeals.setLayout(new FlowLayout());
        allmeals.add(select);
        allmeals.add(back);

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
                    editMenu(day, index);
                    allmeals.dispose();
                }
            });

            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    allmeals.dispose();
                }
            });
        }
    }

    public void editMenu(Daily day, int index) {
        JFrame addMealMenu = new JFrame("Edit Meal");
        addMealMenu.setSize(750, 750);
        addMealMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        addMealMenu.setVisible(true);

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
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMealMenu.dispose();
            }
        });
    }

    public void deleteMeals(Daily day) {
        JFrame allmeals = new JFrame(day.getDate() + " meals");
        allmeals.setSize(750, 750);
        allmeals.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        allmeals.setVisible(true);

        JLabel select = new JLabel("Select a meal you wish to delete: ");
        JLabel success = new JLabel("Meal Successfully Deleted: ");
        JButton back = new JButton("Back");
        allmeals.setLayout(new FlowLayout());
        allmeals.add(select);
        allmeals.add(back);

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
                }
            });

            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    allmeals.dispose();
                }
            });
        }
    }

}
