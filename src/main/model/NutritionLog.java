package model;

import java.util.ArrayList;

// import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a list of days the user has added. 
public class NutritionLog implements Writable {
    private ArrayList<Daily> alltime;

    public NutritionLog() {
        alltime = new ArrayList<Daily>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a day to the end of the list of days
    public void add(Daily day) {
        EventLog.getInstance().logEvent(new Event("Added day: " + day.getDate()));
        alltime.add(day);
    }

    // EFFECTS: returns the number of days added.
    public int size() {
        return alltime.size();
    }

    public Daily get(int index) {
        return alltime.get(index);
    }

    public ArrayList<Daily> getNutritionLog() {
        return alltime;
    }

    // EFFECTS: Creates a json object representing a nutrition log.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("alltime", alltime);
        return json;
    }

    // EFFECTS: returns days in this nutritionlog as a JSON array
    // private JSONArray daystoJson() {
    //     JSONArray jsonArray = new JSONArray();

    //     for (Daily d : alltime) {
    //         jsonArray.put(d.toJson());
    //     }

    //     return jsonArray;
    // }

}
