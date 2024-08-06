package model;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNutritionLog {

    private NutritionLog nl;
    private Daily m;

    @BeforeEach
    void runBefore() {
        nl = new NutritionLog();
        m = new Daily("july");
    }


    @Test
    void testConstructor() {
        assertTrue(nl.getNutritionLog().isEmpty());
        assertEquals(0, nl.getNutritionLog().size());
    }

    @Test
    void testGetMeal() {
        nl.add(m);
        assertEquals(m, nl.get(0));
        assertEquals(1, nl.size());

    }
}   
