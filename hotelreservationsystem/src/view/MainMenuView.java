package view;

import java.util.Scanner;

public class MainMenuView {

    private final BookingView bookingView = new BookingView();
    private final PaymentView paymentView = new PaymentView();
    private final RoomView roomView = new RoomView();
    private final HistoryView historyView = new HistoryView();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            int choice = readInt("Select an option: ");

            switch (choice) {

                case 1:
                    bookingMenu();
                    break;

                case 2:
                    paymentMenu();
                    break;

                case 3:
                    roomMenu();
                    break;

                case 4:
                    historyMenu();
                    break;

                case 0:
                    exit = true;
                    System.out.println("Exiting Hotel Reservation System. Goodbye.");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /* ========================= MENUS ========================= */

    private void printMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Booking Management");
        System.out.println("2. Payment Management");
        System.out.println("3. Room Management");
        System.out.println("4. View History");
        System.out.println("0. Exit");
    }

    private void bookingMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- BOOKING MENU ---");
            System.out.println("1. Create Booking");
            System.out.println("2. View All Bookings");
            System.out.println("3. View Booking By ID");
            System.out.println("4. Cancel Booking");
            System.out.println("0. Back");

            int choice = readInt("Select an option: ");

            switch (choice) {
                case 1:
                    bookingView.createBooking();
                    break;
                case 2:
                    bookingView.viewAllBookings();
                    break;
                case 3:
                    bookingView.viewBookingById();
                    break;
                case 4:
                    bookingView.cancelBooking();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void paymentMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- PAYMENT MENU ---");
            System.out.println("1. Make Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. View Payments By Booking");
            System.out.println("4. View Payment By ID");
            System.out.println("0. Back");

            int choice = readInt("Select an option: ");

            switch (choice) {
                case 1:
                    paymentView.makePayment();
                    break;
                case 2:
                    paymentView.viewAllPayments();
                    break;
                case 3:
                    paymentView.viewPaymentsByBooking();
                    break;
                case 4:
                    paymentView.viewPaymentById();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void roomMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- ROOM MENU ---");
            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. View Available Rooms");
            System.out.println("4. View Available Rooms By Type");
            System.out.println("5. Update Room Availability");
            System.out.println("6. Delete Room");
            System.out.println("0. Back");

            int choice = readInt("Select an option: ");

            switch (choice) {
                case 1:
                    roomView.addRoom();
                    break;
                case 2:
                    roomView.viewAllRooms();
                    break;
                case 3:
                    roomView.viewAvailableRooms();
                    break;
                case 4:
                    roomView.viewAvailableRoomsByType();
                    break;
                case 5:
                    roomView.updateRoomAvailability();
                    break;
                case 6:
                    roomView.deleteRoom();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void historyMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- HISTORY MENU ---");
            System.out.println("1. View Booking History");
            System.out.println("2. View Payment History By Booking");
            System.out.println("3. View All Payment History");
            System.out.println("0. Back");

            int choice = readInt("Select an option: ");

            switch (choice) {
                case 1:
                    historyView.viewBookingHistory();
                    break;
                case 2:
                    historyView.viewPaymentHistoryByBooking();
                    break;
                case 3:
                    historyView.viewAllPaymentHistory();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    /* ========================= HELPER ========================= */

    private int readInt(String message) {
        System.out.print(message);
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}

