package model;

import model.Meal;
import java.util.ArrayList;
import model.Daily; 

public class Daily {

    private ArrayList<Meal> log;
    private String date;


    // Creates a new Day with a date
    public Daily(String date) {
        log = new ArrayList<Meal>();
        this.date = date; 
    }


    //MODIFIES: this
    //EFFECTS: adds a meal to the daily meals 
    public void addMeal(Meal meal) {
        log.add(meal);
    }

    //MODIFIES: this
    //EFFECTS: adds a meal to a specific spot in the day
    public void addMealSpecific(int index, Meal meal) {
        log.add(index, meal);
    }

    
    public int getSize() {
        return log.size();
    }

    //Returns a summary of a meal
    public String getMealString(int index) {
        return (log.get(index)).toString();
    }

    // Returns the meal object at index 
    public Meal getMeal(int index) {
        return log.get(index);
    }


    public String getDate() {
        return date;
    }

    //MODIFIES: this
    //EFFECTS: sets the date to the argument. 
    public void setDate(String date) {
        this.date = date;
    }

    //Removes meal at index
    public void remove (int index) {
        log.remove(index);
    }

}
