package model;

import java.util.ArrayList;

import model.Goal;

//Represents a list of all current goals
public class GoalList {

    private ArrayList<Goal> goallist;

    public GoalList() {
        goallist = new ArrayList<Goal>();
    }

    // Adds a goal to the end of the list
    public void addGoal(Goal goal) {
        goallist.add(goal);
    }

    // Removes a goal from the specified index
    public void removeGoal(int index) {
        goallist.remove(index);
    }

    // Returns the number of current goals
    public int getSize() {
        return goallist.size();
    }

    // Returns the goal at the specified index.
    public Goal getGoal(int index) {
        return goallist.get(index);
    }

}
