package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a single goal. A goal has an objective, a goal number,
// the current progress towards the goal, and a status of being compelete or not. 
public class Goal implements Writable {

    private String objective;
    private int goal;
    private int goalProgress;
    private boolean status;

    // Creates a goal with an objetive, goal, goal progress, and status.
    public Goal(String objective, int goal) {
        this.objective = objective;
        this.goal = goal;
        goalProgress = 0;
        status = false;

    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getGoalProgress() {
        return goalProgress;
    }

    public void setGoalProgress(int goalProgress) {
        this.goalProgress = goalProgress;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: increases goal progress by amount.
    public void increaseGoalProgress(int amount) {
        goalProgress += amount;
    }

    // EFFECTS: returns true if goal is reached
    public boolean overGoal() {
        status = true;
        return goalProgress >= goal;

    }

    // EFFECTS: Returns a string summary of goal and goal progress.
    // if the goal progress > goal, mark as COMPLETE
    public String toString() {

        String result = "";
        if (overGoal()) {
            result = objective + ": COMPLETE";
        } else {
            result = objective + ": " + goalProgress + "/" + goal;
        }

        return result;
    }

    // EFFECTS: Creates a json object representing a goal.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("objective", objective);
        json.put("goal", goal);
        json.put("goalProgress", goalProgress);
        json.put("status", status);
        return json;
    }

}
