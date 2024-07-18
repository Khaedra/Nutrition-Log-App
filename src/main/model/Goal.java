package model;

public class Goal {

    private String objective; 
    private int goal;
    private int goalProgress;

    public Goal(String objective, int goal){
        this.objective = objective;
        this.goal = goal;
        goalProgress = 0; 

    }

}
