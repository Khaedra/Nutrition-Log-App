package model;

// Represents a single meal eaten. Includes a name, calories, carbohydrates, proteins, and fats.
//
public class Meal {

  
    private String name;
    private int calories;
    private int carbohydrates;
    private int proteins;
    private int fats; 

    //EFFECTS: creates a meal with name, calories, carbohydrates, proteins, and fats
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

    //EFFECTS: sets the name of the meal to name
    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    // Sets the calories to calories 
    public void setCalories(int calories) {
        this.calories = calories;
    }


    public int getCarbohydrates() {
        return carbohydrates;
    }

    //Sets the carbohydrates to carbohydrates
    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }


    public int getProteins() {
        return proteins;
    }

    //Sets the protein to proteins
    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }
    //Sets the fats to fats
    public void setFats(int fats) {
        this.fats = fats;
    }

    //Returns a string summarizing a meal. 
    public String toString() {
        String summary = "Name: " + name + "\n Calories: " + calories + 
        "\n Carbohydrates: " + carbohydrates + "\n Protein: " + proteins + "\n Fat: " + fats;

        return summary;
    }

}

   

