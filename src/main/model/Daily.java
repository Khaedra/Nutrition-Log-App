package model;

import model.Meal;
import java.util.ArrayList;
import model.Daily;

//Represents a days worth of meals. Includes a date. 
public class Daily {

    private ArrayList<Meal> log;
    private String date;

    // Creates a new Day with a date
    public Daily(String date) {
        log = new ArrayList<Meal>();
        this.date = date;
    }

    // MODIFIES: this
    // EFFECTS: adds a meal to the daily meals
    public void addMeal(Meal meal) {
        log.add(meal);
    }

    // MODIFIES: this
    // EFFECTS: adds a meal to a specific spot in the day
    public void addMealSpecific(int index, Meal meal) {
        log.add(index, meal);
    }

    // returns amount of meals in this day
    public int getSize() {
        return log.size();
    }

    // Returns a summary of a specific meal
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

    public void setDate(String date) {
        this.date = date;
    }

    // Removes meal at index
    public void remove(int index) {
        log.remove(index);
    }

    // Returns list of meals in the day
    public ArrayList<Meal> getLog() {
        return log;

    }

}
