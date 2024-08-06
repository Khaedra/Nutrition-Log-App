package test.persistence;

import model.Daily;
import model.Goal;
import model.GoalList;

import model.NutritionLog;
import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderGLNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GoalList gl = reader.readGoalList();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNLNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            NutritionLog nl = reader.readNutritionLog();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testGLEmpty() {
        JsonReader reader = new JsonReader("./data/testGLempty.json");
        try {
            GoalList gl = reader.readGoalList();
            assertEquals(0, gl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGoalList() {
        JsonReader reader = new JsonReader("./data/testGoalListGeneral.json");
        try {
            GoalList gl = reader.readGoalList();
            ArrayList<Goal> goals = gl.getGoalList();
            assertEquals(2, gl.getSize());
            checkGoal("Calorie Goal", 100, goals.get(0));
            checkGoal("Protein Goal", 20, goals.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralNutritionLog() {
        JsonReader reader = new JsonReader("./data/testNutritionListGeneral.json");
        try {
            NutritionLog nl = reader.readNutritionLog();
            ArrayList<Daily> alltime = nl.getNutritionLog();
            assertEquals(2, nl.size());
            checkDaily("july 12", alltime.get(0));
            checkDaily("july 15", alltime.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}


// JButton back = createButton("Back", new ActionListener() {
//     @Override
//     public void actionPerformed(ActionEvent e) {
//         displaydaymenu.dispose();
//     }
// });