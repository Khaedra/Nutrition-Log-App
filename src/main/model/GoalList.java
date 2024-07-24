package model;

import java.util.ArrayList;

import model.Goal;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//Represents a list of all current goals
public class GoalList implements Writable{

    private ArrayList<Goal> goallist;

    public GoalList() {
        goallist = new ArrayList<Goal>();
    }
    // MODIFIES: this
    // EFFECTS: Adds a goal to the end of the list
    public void addGoal(Goal goal) {
        goallist.add(goal);
    }
    // MODIFIES: this
    // EFFECTS: Removes a goal from the specified index
    public void removeGoal(int index) {
        goallist.remove(index);
    }

    // EFFECTS: Returns the number of current goals
    public int getSize() {
        return goallist.size();
    }

    // EFFECTS: Returns the goal at the specified index.
    public Goal getGoal(int index) {
        return goallist.get(index);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("goallist", goallist);
        return json;
    }

    // EFFECTS: returns goals in this goallist as a JSON array
    private JSONArray goalstoJson() {
        JSONArray jsonArray = new JSONArray();

        for (Goal g : goallist) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }

}
