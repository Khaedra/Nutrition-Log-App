package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDaily {

    Daily day; 
    Meal meal;
    Meal meal2;
    Meal meal3;

    @BeforeEach
    void runBefore() {
        day = new Daily("july");
        meal2 = new Meal("mac", 0, 0, 0, 0);
        meal = new Meal("pizza", 0, 0, 0, 0);
        meal3 = new Meal("Pizza", 800, 36, 12, 10);
    }

    @Test
    void testConstructor() {
        assertEquals(0, day.getSize());
        assertEquals("july", day.getDate());
        assertTrue(day.getLog().isEmpty());
    }


    @Test
    void testAddMeal() {
        day.addMeal(meal);
        assertEquals(meal, day.getMeal(0));
        assertEquals(1, day.getSize());

    }

    @Test 
    void testGetMealString() {
        day.addMeal(meal3);
        assertEquals(day.getMealString(0), "Name: Pizza\n Calories: 800\n Carbohydrates: 36g\n Protein: " + 
    "12g\n Fat: 10g");

    }

    @Test 
    void testSetDate() {
        assertEquals(day.getDate(), "july");
        day.setDate("may");
        assertEquals(day.getDate(), "may");
    }


    @Test
    void testAddMealSpecific() {
        day.addMeal(meal);
        day.addMealSpecific(0, meal2);
        assertEquals(meal2, day.getMeal(0));
        assertEquals(meal, day.getMeal(1));
        assertEquals(2, day.getSize());
    }

    @Test
    void testRemove() {
        day.addMeal(meal);
        assertEquals(meal, day.getMeal(0));
        assertEquals(1, day.getSize());
        day.remove(0);
        assertEquals(0, day.getSize());

    }




}
