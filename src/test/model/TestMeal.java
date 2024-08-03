package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMeal {

    Meal pizza;

    @BeforeEach
    void runBefore() {
        pizza = new Meal("Pizza", 800, 36, 12, 10);
    }

    @Test
    void testConstructor() {
        assertEquals("Pizza", pizza.getName());
        assertEquals(800, pizza.getCalories());
        assertEquals(36, pizza.getCarbohydrates());
        assertEquals(12, pizza.getProteins());
        assertEquals(10, pizza.getFats());
    }

    @Test
    void testSetName() {
        assertEquals("Pizza", pizza.getName());
        pizza.setName("mac");
        assertEquals("mac", pizza.getName());
    }
    @Test
    void testSetCalories() {
        assertEquals(800, pizza.getCalories());
        pizza.setCalories(600);
        assertEquals(600, pizza.getCalories());
    }
    @Test
    void testSetCarbohydrates() {
        assertEquals(36, pizza.getCarbohydrates());
        pizza.setCarbohydrates(20);
        assertEquals(20, pizza.getCarbohydrates());
    }
    @Test
    void testSetFats() {
        assertEquals(10, pizza.getFats());
        pizza.setFats(20);
        assertEquals(20, pizza.getFats());
    }
    @Test
    void testSetProteins() {
        assertEquals(12, pizza.getProteins());
        pizza.setProteins(20);
        assertEquals(20, pizza.getProteins());
    }

    @Test
    void testToString() {
    assertEquals(" Name: Pizza\n Calories: 800\n Carbohydrates: 36g\n Protein: " + 
    "12g\n Fat: 10g", pizza.toString());
    }
}
