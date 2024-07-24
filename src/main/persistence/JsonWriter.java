package persistence;
import model.Daily;
import model.GoalList;
import model.NutritionLog;

import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
// Most code taken from workroom application. 
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of goallist to file
    public void writeGoalList(GoalList gl) {
        JSONObject json = gl.toJson();
        saveToFile(json.toString(TAB));
    }
    // MODIFIES: this
    // EFFECTS: writes JSON representation of daily to file
    public void writeDaily(Daily d) {
        JSONObject json = d.toJson();
        saveToFile(json.toString(TAB));
    }
    public void writeNutritionLog(NutritionLog nl) {
        JSONObject json = nl.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

