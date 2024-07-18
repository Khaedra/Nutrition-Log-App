package model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class TestGoalList {
    GoalList goallist; 
    Goal goal; 

    @BeforeEach
    void runBefore() {
        goallist = new GoalList();
        goal = new Goal("water", 2);

    }

    @Test
    void testConstructor() {
        assertEquals(0, goallist.getSize());

    }

    @Test
    void testAddGoal() {
        assertEquals(0, goallist.getSize());
        goallist.addGoal(goal);
        assertEquals(1, goallist.getSize());
        assertEquals(goal, goallist.getGoal(0));
    }

    @Test
    void testRemoveGoal() {
        assertEquals(0, goallist.getSize());
        goallist.addGoal(goal);
        assertEquals(1, goallist.getSize());
        goallist.removeGoal(0);
        assertEquals(0, goallist.getSize());
    }

    


}
