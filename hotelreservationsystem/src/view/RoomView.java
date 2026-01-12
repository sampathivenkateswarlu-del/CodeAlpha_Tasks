package view;

import java.util.List;
import java.util.Scanner;
import exception.HotelReservationException;
import model.dao.RoomDAO;
import model.entity.Room;
import model.enums.RoomType;

public class RoomView {
	private final RoomDAO roomDAO = new RoomDAO();
	private final Scanner scanner = new Scanner(System.in);

	/** * Creates a new room. */
	public void addRoom() {
		try {
			System.out.println("\n=== ADD NEW ROOM ===");
			RoomType roomType = readRoomType();
			double pricePerDay = readDouble("Enter Price Per Day: ");
			Room room = new Room();
			room.setRoomType(roomType);
			room.setAvailable(true);
			room.setPricePerDay(pricePerDay);
			roomDAO.createRoom(room);
			System.out.println("Room added successfully.");
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}
	}

	/** * Displays all rooms. */
	public void viewAllRooms() {
		try {
			List<Room> rooms = roomDAO.getAllRooms();
			if (rooms.isEmpty()) {
				System.out.println("No rooms found.");
				return;
			}
			System.out.println("\n=== ROOM LIST ===");
			for (Room room : rooms) {
				printRoom(room);
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/** * Displays available rooms by type. */
	public void viewAvailableRoomsByType() {
		try {
			RoomType roomType = readRoomType();
			List<Room> rooms = roomDAO.getAvailableRoomsByType(roomType);
			if (rooms.isEmpty()) {
				System.out.println("No available rooms for selected type.");
				return;
			}
			System.out.println("\n=== AVAILABLE ROOMS (" + roomType + ") ===");
			for (Room room : rooms) {
				printRoom(room);
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/** * Updates room availability. */
	public void updateRoomAvailability() {
		try {
			System.out.println("\n=== UPDATE ROOM AVAILABILITY ===");
			int roomId = readInt("Enter Room ID: ");
			System.out.print("Is room available? (true/false): ");
			boolean available = scanner.nextBoolean();
			scanner.nextLine();
			roomDAO.updateRoomAvailability(roomId, available);
			System.out.println("Room availability updated successfully.");
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/** * Deletes a room. */
	public void deleteRoom() {
		try {
			int roomId = readInt("Enter Room ID to delete: ");
			roomDAO.deleteRoom(roomId);
			System.out.println("Room deleted successfully.");
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/* ========================= HELPER METHODS ========================= */ private RoomType readRoomType() {
		while (true) {
			System.out.println("Select Room Type:");
			System.out.println("1. STANDARD");
			System.out.println("2. DELUXE");
			System.out.println("3. SUITE");
			System.out.print("Choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				return RoomType.STANDARD;
			case 2:
				return RoomType.DELUXE;
			case 3:
				return RoomType.SUITE;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		}
	}

	private int readInt(String message) {
		System.out.print(message);
		int value = scanner.nextInt();
		scanner.nextLine();
		return value;
	}

	private double readDouble(String message) {
		System.out.print(message);
		double value = scanner.nextDouble();
		scanner.nextLine();
		return value;
	}

	private void printRoom(Room room) {
		System.out.println("-----------------------------------");
		System.out.println("Room ID : " + room.getRoomId());
		System.out.println("Room Type : " + room.getRoomType());
		System.out.println("Available : " + room.isAvailable());
		System.out.println("Price Per Day : ₹" + room.getPricePerDay());
	}

	public void viewAvailableRooms() {
		try {
			List<Room> rooms = roomDAO.getAvailableRooms();
			if (rooms.isEmpty()) {
				System.out.println("No available rooms at the moment.");
				return;
			}
			System.out.println("\n=== AVAILABLE ROOMS ===");
			for (Room room : rooms) {
				System.out.println("-----------------------------------");
				System.out.println("Room ID : " + room.getRoomId());
				System.out.println("Type : " + room.getRoomType());
				System.out.println("Price : ₹" + room.getPricePerDay());
				System.out.println("Available : " + (room.isAvailable() ? "YES" : "NO"));
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
