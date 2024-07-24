package ui;

import java.io.FileNotFoundException;

//Main class. Run to start application.
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            new NutritionLog();
        }
        catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");  
        }

    }
}
