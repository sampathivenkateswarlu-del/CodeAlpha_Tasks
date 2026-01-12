package view;

import java.util.List;
import java.util.Scanner;
import exception.HotelReservationException;
import model.dao.BookingDAO;
import model.dao.PaymentDAO;
import model.entity.Booking;
import model.entity.Payment;

public class HistoryView {
	private final BookingDAO bookingDAO = new BookingDAO();
	private final PaymentDAO paymentDAO = new PaymentDAO();
	private final Scanner scanner = new Scanner(System.in);

	
	public void viewBookingHistory() {
		try {
			List<Booking> bookings = bookingDAO.getAllBookings();
			if (bookings.isEmpty()) {
				System.out.println("No booking history available.");
				return;
			}
			System.out.println("\n=== BOOKING HISTORY ===");
			for (Booking booking : bookings) {
				printBooking(booking);
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	
	public void viewPaymentHistoryByBooking() {
		try {
			System.out.print("Enter Booking ID: ");
			int bookingId = scanner.nextInt();
			scanner.nextLine();
			List<Payment> payments = paymentDAO.getPaymentsByBookingId(bookingId);
			if (payments.isEmpty()) {
				System.out.println("No payment history found for this booking.");
				return;
			}
			System.out.println("\n=== PAYMENT HISTORY FOR BOOKING ID: " + bookingId + " ===");
			for (Payment payment : payments) {
				printPayment(payment);
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	
	public void viewAllPaymentHistory() {
		try {
			List<Payment> payments = paymentDAO.getAllPayments();
			if (payments.isEmpty()) {
				System.out.println("No payment history available.");
				return;
			}
			System.out.println("\n=== ALL PAYMENT HISTORY ===");
			for (Payment payment : payments) {
				printPayment(payment);
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	 private void printBooking(
			Booking booking) {
		System.out.println("-----------------------------------");
		System.out.println("Booking ID : " + booking.getBookingId());
		System.out.println("Customer ID : " + booking.getCustomerId());
		System.out.println("Room ID : " + booking.getRoomId());
		System.out.println("Check-in : " + booking.getCheckInDate());
		System.out.println("Check-out : " + booking.getCheckOutDate());
		System.out.println("Amount : ₹" + booking.getTotalAmount());
		System.out.println("Status : " + booking.getStatus());
	}

	private void printPayment(Payment payment) {
		System.out.println("-----------------------------------");
		System.out.println("Payment ID : " + payment.getPaymentId());
		System.out.println("Booking ID : " + payment.getBookingId());
		System.out.println("Amount : ₹" + payment.getAmount());
		System.out.println("Status : " + payment.getStatus());
		System.out.println("Payment Date : " + payment.getPaymentDate());
	}
}
