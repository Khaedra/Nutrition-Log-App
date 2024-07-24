package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class NutritionLog implements Writable{
    private ArrayList<Daily> alltime;

    public NutritionLog() {
        alltime = new ArrayList<Daily>();
    }

    public void add(Daily day) {
        alltime.add(day);
    }

    public int size(){
        return alltime.size();
    }

    public Daily get(int index) {
        return alltime.get(index);
    }
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("alltime", alltime);
        return json;
    }

    // EFFECTS: returns days in this nutritionlog as a JSON array
    private JSONArray daystoJson() {
        JSONArray jsonArray = new JSONArray();

        for (Daily d : alltime) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

    

}
