package test.persistence;


import org.junit.jupiter.api.Test;

import model.Daily;
import model.Goal;
import model.GoalList;
import model.NutritionLog;

import java.io.IOException;
import java.util.List;
import persistence.JsonWriter;
import persistence.JsonReader;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testGoalListInvalidFile() {
        try {
            GoalList gl = new GoalList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            GoalList gl = new GoalList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGoalList.json");
            writer.open();
            writer.writeGoalList(gl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGoalList.json");
            gl = reader.readGoalList();
            assertEquals(0, gl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyNutritionLog() {
        try {
            NutritionLog nl = new NutritionLog();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyNutritionLog.json");
            writer.open();
            writer.writeNutritionLog(nl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyNutritionLog.json");
            nl = reader.readNutritionLog();
            assertEquals(0, nl.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGoalList() {
        try {
            GoalList gl = new GoalList();
            gl.addGoal(new Goal("Calorie Goal", 100));
            gl.addGoal(new Goal("Protein Goal", 20));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGoalList.json");
            writer.open();
            writer.writeGoalList(gl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGoalList.json");
            gl = reader.readGoalList();
            List<Goal> thingies = gl.getGoalList();
            assertEquals(2, thingies.size());
            checkGoal("Calorie Goal", 100,  thingies.get(0));
            checkGoal("Protein Goal", 20, thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralNutritionLog() {
        try {
            NutritionLog nl = new NutritionLog();
            nl.add(new Daily("june 12"));
            nl.add(new Daily("june 15"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralNutritionLog.json");
            writer.open();
            writer.writeNutritionLog(nl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralNutritionLog.json");
            nl = reader.readNutritionLog();
            List<Daily> thingies = nl.getNutritionLog();
            assertEquals(2, thingies.size());
            checkDaily("june 12",  thingies.get(0));
            checkDaily("june 15", thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}