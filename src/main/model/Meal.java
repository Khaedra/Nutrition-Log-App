package model;
import org.json.JSONObject;
import persistence.Writable;

// Represents a single meal eaten. Includes a name, calories, carbohydrates, proteins, and fats.

public class Meal implements Writable {

    private String name;
    private int calories;
    private int carbohydrates;
    private int proteins;
    private int fats;

    // EFFECTS: creates a meal with name, calories, carbohydrates, proteins, and fats

    public Meal(String name, int calories, int carbohydrates, int proteins, int fats) {
        this.name = name;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fats = fats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    // EFFECTS: Returns a string summarizing a meal.
    public String toString() {
        String summary = "Name: " + name + "\n Calories: " + calories
                + "\n Carbohydrates: " + carbohydrates + "g\n Protein: " + proteins + "g\n Fat: " + fats + "g";

        return summary;
    }

    // Creates a json object representing a meal.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("calories", calories);
        json.put("carbohydrates", carbohydrates);
        json.put("proteins", proteins);
        json.put("fats", fats);
        return json;
    }

}
