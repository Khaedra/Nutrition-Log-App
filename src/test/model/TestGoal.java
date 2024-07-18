package model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class TestGoal {
    Goal goal;
    Goal goal2; 

    @BeforeEach
    void runBefore () {
        goal = new Goal("water", 2);
        
    }

    @Test
    void testConstructor() {
        assertEquals("water", goal.getObjective());
        assertEquals(2, goal.getGoal());
        assertFalse(goal.isStatus());
        assertEquals(0, goal.getGoalProgress());
    }
    @Test
    void testSetObjective() {
        assertEquals("water", goal.getObjective());
        goal.setObjective("coke");
        assertEquals("coke", goal.getObjective());
    }

    @Test
    void testSetGoal() {
        assertEquals(2, goal.getGoal());
        goal.setGoal(3);
        assertEquals(3, goal.getGoal());
    }
    @Test
    void testSetGoalProgress() {
        assertEquals(0, goal.getGoalProgress());
        goal.setGoalProgress(1);
        assertEquals(1, goal.getGoalProgress());
    }

    @Test
    void testSetStatus() {
        assertFalse(goal.isStatus());
        goal.setStatus(true);
        assertTrue(goal.isStatus());
    }
    @Test 
    void testIncreaseGoalProgress() {
        assertEquals(0, goal.getGoalProgress());
        goal.increaseGoalProgress(1);
        assertEquals(1, goal.getGoalProgress());
        goal.increaseGoalProgress(2);
        assertEquals(3, goal.getGoalProgress());
    }

    @Test 
    void overGoal() {
        assertEquals(0, goal.getGoalProgress());
        assertEquals(2, goal.getGoal());
        assertFalse(goal.overGoal());
        goal.increaseGoalProgress(1);
        assertFalse(goal.overGoal());
        goal.increaseGoalProgress(1);
        assertTrue(goal.overGoal());
        goal.increaseGoalProgress(1);
        assertTrue(goal.overGoal());
        
    }

    @Test 
    void testToString () {
        assertEquals("water: 0/2", goal.toString());
        goal.setGoalProgress(3);
        assertEquals("water: COMPLETE", goal.toString());

    }
    

}
