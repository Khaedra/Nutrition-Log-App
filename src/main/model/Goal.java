package model;

//Represents a single goal. A goal has an objective, a goal number,
// the current progress towards the goal, and a status of being compelete or not. 
public class Goal {

    private String objective;
    private int goal;
    private int goalProgress;
    private boolean status;

    // Creates a goal
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

    // increases goal progress by amount
    public void increaseGoalProgress(int amount) {
        goalProgress += amount;
    }

    // returns true if goal is reached
    public boolean overGoal() {
        status = true;
        return goalProgress >= goal;

    }

    // Returns a summary of the goal progress/goal.
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

}
