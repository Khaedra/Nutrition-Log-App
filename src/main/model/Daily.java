package model;

import java.util.ArrayList;
import model.Daily;


//Represents a days worth of meals. Includes a date. 
public class Daily {

    private ArrayList<Meal> log;
    private String date;

    // Creates a new Day with a date and empty list of meals
    public Daily(String date) {
        log = new ArrayList<Meal>();
        this.date = date;
    }

    // Creates a new day given a log of meals and date.
    public Daily(String date, ArrayList<Meal> log) {
        this.log = log;
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

    // EFFECTS: returns amount of meals in this day
    public int getSize() {
        return log.size();
    }

    // EFFECTS: Returns a string summary of a specific meal
    public String getMealString(int index) {
        return (log.get(index)).toString();
    }

    // EFFECTS: Returns the meal object at index
    public Meal getMeal(int index) {
        return log.get(index);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // REQUIRES: index is valid
    // EFFECTS: Removes meal at index
    public void remove(int index) {
        log.remove(index);
    }

    public ArrayList<Meal> getLog() {
        return log;
    }

    // // EFFECTS: Creates a new json object out of Daily.
    // @Override
    // public JSONObject toJson() {
    //     JSONObject json = new JSONObject();
    //     json.put("log", log);
    //     json.put("date", date);
    //     return json;
    // }

    // EFFECTS: returns meals in this day as a JSON array
    // private JSONArray mealstoJson() {
    //     JSONArray jsonArray = new JSONArray();
    //     for (Meal m : log) {
    //         jsonArray.put(m.toJson());
    //     }
    //     return jsonArray;
    // }

}
