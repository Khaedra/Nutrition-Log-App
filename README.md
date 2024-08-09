# Job Application Organizer

# Daily Nutrition Tracker
## For Gourmands, Epicures, and Computer Science Students

This application is designed to help keep track of what foods and macronutrients a user consumes everyday. Users will be able to set targets or keep a journal log to record meals and review progress. Options to set calorie or water goals are available, and recommended values of nutrients based on age and weight are also available. 

This is especially of interest to **students** since studies often don't allow sufficient time to prepare healthy meals and are easily forgettable in the face of deadlines and exams. Spending 5 minutes a day logging food may drastically help a student fix or change their eating habits and overall wellbeing. 

This friendly interface will also allow you to log multiple days and view weeks worth of progress. Handy for people who forget or want a reminder of past meals. 


## User Stories 

1. As a user, I want to be able to add a meal which includes calories, fat, carbohydrates, protein etc. to my daily log.  
2. As a user, I want to be able to see an overview of the meals I ate today, this week, or all time. 
3. As a user, I want to be able to set a goal and be notified when I reach my goal.
4. As a user, I want to be able to edit or delete meals if I make a mistake. 
5. As a user, I want to be able to view a summary of total macronutrients consumed every day.
6. As a user, I want to be able to have the option to save the current state of my days and goals.
7. As a user, I want to be able to load my days and goals from file. 

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by selecting "New Day" and entering a date. This will add a day (X) to a list of days (Y). You can view this by clicking "Back", and then "View Weekly" or "All Time Overview" to see your day added. 
- In addtion you can click "Select Day", select your day, and then "add a meal". This will add a Meal(X) to a list of Meals (y).
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by adding a meal. Enter a name, calories, carbs, protein, and fat. On this screen, you will also find the visual component; An infographic of what foods contain what macros. After adding a meal, select "back" and then "Edit a meal". Select your meal and now you will be prompted to change its details. Once modified, this you can see that the meal has been edited by clicking "Edit meal" again. This fulfills the second requirement. 
- You can locate my visual component by selecting "Add a meal" as mentioned earlier. An infographic sourced by Health.mil displays which foods contain a significant amount of carbs, protein, and fat. 
- You can save the state of my application by selecting "Save log to file". This will automatically write all current data (days, meals, goals) to the file. 
- You can reload the state of my application by selecting "Load log from file". This will load your last saved state of the application. 

# Representative Event Log
Thu Aug 08 19:36:18 PDT 2024
Added day: july


Thu Aug 08 19:36:22 PDT 2024
Added meal:  Name: mac
 Calories: 2
 Carbohydrates: 2g
 Protein: 2g
 Fat: 2g


Thu Aug 08 19:36:24 PDT 2024
Removed/Edited Meal


Thu Aug 08 19:36:27 PDT 2024
Edited Meal:  Name: cheese
 Calories: 4
 Carbohydrates: 4g
 Protein: 4g
 Fat: 4g


Thu Aug 08 19:36:31 PDT 2024
Removed/Edited Meal


Thu Aug 08 19:36:39 PDT 2024
Added meal:  Name: cracker
 Calories: 2
 Carbohydrates: 2g
 Protein: 2g
 Fat: 2g


Thu Aug 08 19:36:45 PDT 2024
Added goal: Calorie Goal: 0/2


Thu Aug 08 19:36:56 PDT 2024
Edited goal progress: 1


Thu Aug 08 19:37:03 PDT 2024
Added goal: Carb Goal: 0/4


Thu Aug 08 19:37:06 PDT 2024
Removed goal


Thu Aug 08 19:37:10 PDT 2024
Increased goal progress by: 2

# Possible Improvements
After creating my UML class diagram and coding my GUI, a big component that could be improved is the Goal class. When adding a goal, the user is given the option to add 4 different types of goals which I all created under the same umbrella. In hindsight, I should've made an abstract class Goal and 4 subclasses matching the specific types of goals. That way, a lot of repeat code could have been removed and also made the implementation of the GUI shorter and easier to read. 

As well, I definitely could have split up my visual application class into multiple classes that all handle a specific part of the application. As it is right now, it handles every function the application can do, but it might make sense to have classes handle different functions of each button. For example, the select day button offers many more functions once clicked and would make sense if it was seperate from the main menu. 
 