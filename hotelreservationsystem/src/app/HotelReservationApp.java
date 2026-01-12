package app;

import view.MainMenuView;

public class HotelReservationApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("  HOTEL RESERVATION SYSTEM - STARTED  ");
        System.out.println("=====================================");

        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.start();

        System.out.println("=====================================");
        System.out.println("  HOTEL RESERVATION SYSTEM - STOPPED  ");
        System.out.println("=====================================");
    }
}

