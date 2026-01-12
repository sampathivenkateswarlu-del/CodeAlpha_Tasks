package view;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import exception.BookingNotFoundException;
import exception.HotelReservationException;
import model.dao.BookingDAO;
import model.dao.RoomDAO;
import model.entity.Booking;
import model.entity.Room;
import model.enums.BookingStatus;
import model.enums.RoomType;
import util.DateUtil;
import util.PriceCalculator;

public class BookingView {
	private final BookingDAO bookingDAO = new BookingDAO();
	private final RoomDAO roomDAO = new RoomDAO();
	private final Scanner scanner = new Scanner(System.in);

	/** * Entry point for booking flow. */
	public void createBooking() {
		try {
			System.out.println("\n=== CREATE NEW BOOKING ===");
			int customerId = readInt("Enter Customer ID: ");
			RoomType roomType = readRoomType();
			List<Room> availableRooms = roomDAO.getAvailableRoomsByType(roomType);
			if (availableRooms.isEmpty()) {
				System.out.println("No available rooms for selected type.");
				return;
			}
			Room selectedRoom = availableRooms.get(0);
			LocalDate checkInDate = readDate("Enter Check-in Date (YYYY-MM-DD): ");
			LocalDate checkOutDate = readDate("Enter Check-out Date (YYYY-MM-DD): ");
			validateDates(checkInDate, checkOutDate);
			int days = (int) DateUtil.daysBetween(checkInDate, checkOutDate);
			double totalAmount = PriceCalculator.calculate(roomType, days);
			Booking booking = new Booking();
			booking.setCustomerId(customerId);
			booking.setRoomId(selectedRoom.getRoomId());
			booking.setCheckInDate(checkInDate);
			booking.setCheckOutDate(checkOutDate);
			booking.setTotalAmount(totalAmount);
			booking.setStatus(BookingStatus.CREATED);
			bookingDAO.createBooking(booking);
			roomDAO.updateRoomAvailability(selectedRoom.getRoomId(), false);
			System.out.println("Booking created successfully.");
			System.out.println("Room ID : " + selectedRoom.getRoomId());
			System.out.println("Total Amount : ₹" + totalAmount);
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}
	}

	/** * Displays all bookings. */
	public void viewAllBookings() {
		try {
			List<Booking> bookings = bookingDAO.getAllBookings();
			if (bookings.isEmpty()) {
				System.out.println("No bookings found.");
				return;
			}
			System.out.println("\n=== BOOKING LIST ===");
			for (Booking booking : bookings) {
				printBooking(booking);
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/** * Cancels a booking. */
	public void cancelBooking() {
		try {
			System.out.println("\n=== CANCEL BOOKING ===");
			int bookingId = readInt("Enter Booking ID: ");
			Booking booking = bookingDAO.getBookingById(bookingId);
			bookingDAO.updateBookingStatus(bookingId, BookingStatus.CANCELLED);
			roomDAO.updateRoomAvailability(booking.getRoomId(), true);
			System.out.println("Booking cancelled successfully.");
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

	private LocalDate readDate(String message) {
		System.out.print(message);
		return LocalDate.parse(scanner.nextLine());
	}

	private void validateDates(LocalDate checkIn, LocalDate checkOut) {
		if (DateUtil.isPastDate(checkIn)) {
			throw new HotelReservationException("Check-in date cannot be in the past");
		}
		if (!checkOut.isAfter(checkIn)) {
			throw new HotelReservationException("Check-out date must be after check-in date");
		}
	}

	private void printBooking(Booking booking) {
		System.out.println("-----------------------------------");
		System.out.println("Booking ID : " + booking.getBookingId());
		System.out.println("Customer ID : " + booking.getCustomerId());
		System.out.println("Room ID : " + booking.getRoomId());
		System.out.println("Check-in : " + booking.getCheckInDate());
		System.out.println("Check-out : " + booking.getCheckOutDate());
		System.out.println("Amount : ₹" + booking.getTotalAmount());
		System.out.println("Status : " + booking.getStatus());
	}

	public void viewBookingById() {
		try {
			System.out.print("Enter Booking ID: ");
			int bookingId = scanner.nextInt();
			scanner.nextLine();
			Booking booking = bookingDAO.getBookingById(bookingId);
			if (booking == null) {
				System.out.println("Booking not found for ID: " + bookingId);
				return;
			}
			System.out.println("\n=== BOOKING DETAILS ===");
			System.out.println("-----------------------------------");
			System.out.println("Booking ID : " + booking.getBookingId());
			System.out.println("Customer ID : " + booking.getCustomerId());
			System.out.println("Room ID : " + booking.getRoomId());
			System.out.println("Check-In : " + booking.getCheckInDate());
			System.out.println("Check-Out : " + booking.getCheckOutDate());
			System.out.println("Total Amount : ₹" + booking.getTotalAmount());
			System.out.println("Status : " + booking.getStatus());
		} catch (BookingNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Invalid input. Please try again.");
			scanner.nextLine();
		}
	}
}
