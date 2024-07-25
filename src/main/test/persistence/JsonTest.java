package test.persistence;

import model.Meal;
import model.Daily;
import model.Goal;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMeal(String name, String calories, String carbohydrate, String protein, String fat, Meal meal) {
        assertEquals(name, meal.getName());
        assertEquals(calories, meal.getCalories());
        assertEquals(carbohydrate, meal.getCarbohydrates());
        assertEquals(protein, meal.getProteins());
        assertEquals(fat, meal.getFats());
    }
    protected void checkGoal(String objective, int goal, Goal g) {
        assertEquals(objective, g.getObjective());
        assertEquals(goal, g.getGoal());

    }

    protected void checkDaily(String date, Daily day) {
        assertEquals(date, day.getDate());

    }
}