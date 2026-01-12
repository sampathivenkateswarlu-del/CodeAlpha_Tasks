package studentgradetracker_app;

import ui.ConsoleMenu;

public class StudentGradeTrackerApp {

    public static void main(String[] args) {

        try {
            ConsoleMenu consoleMenu = new ConsoleMenu();
            consoleMenu.start();
        } catch (Exception e) {
            System.out.println("Application failed to start.");
            System.out.println("Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

