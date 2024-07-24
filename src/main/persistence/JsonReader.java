package persistence;

import model.Daily;
import model.Goal;
import model.GoalList;
import model.Meal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// Code mostly taken from WorkRoom app demo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads goallist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GoalList readGoalList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGoalList(jsonObject);
    }

    // EFFECTS: reads Daily from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Daily readDaily() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDaily(jsonObject);
    }

    
    

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses GoalList from JSON object and returns it
    private GoalList parseGoalList(JSONObject jsonObject) {

        GoalList gl = new GoalList();
        addGoals(gl, jsonObject);
        return gl;
    }

    // EFFECTS: parses Daily from JSON object and returns it
    private Daily parseDaily(JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        Daily d = new Daily(date);
        addMeals(d, jsonObject);
        return d;
    }

    // MODIFIES: wr
    // EFFECTS: parses meals from JSON object and adds them to the day
    private void addMeals(Daily d, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("log");
        for (Object json : jsonArray) {
            JSONObject nextMeal = (JSONObject) json;
            addMeal(d, nextMeal);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses meal from JSON object and adds it to day
    private void addMeal(Daily d, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int calories = jsonObject.getInt("calories");
        int carbohydrates = jsonObject.getInt("carbohydrates");
        int proteins = jsonObject.getInt("proteins");
        int fats = jsonObject.getInt("fats");
        Meal m = new Meal(name, calories, carbohydrates, proteins, fats);
        d.addMeal(m);
    }

    // MODIFIES: wr
    // EFFECTS: parses goals from JSON object and adds them to workroom
    private void addGoals(GoalList gl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("goallist");
        for (Object json : jsonArray) {
            JSONObject nextGoal = (JSONObject) json;
            addGoal(gl, nextGoal);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses goal from JSON object and adds it to goallist
    private void addGoal(GoalList gl, JSONObject jsonObject) {
        String objective = jsonObject.getString("objective");
        int goal = jsonObject.getInt("goal");
        Goal g = new Goal(objective, goal);
        gl.addGoal(g);
    }
}

