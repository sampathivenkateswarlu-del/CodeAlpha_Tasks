package view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import exception.HotelReservationException;
import exception.InvalidPaymentException;
import model.dao.BookingDAO;
import model.dao.PaymentDAO;
import model.entity.Booking;
import model.entity.Payment;
import model.enums.BookingStatus;
import model.enums.PaymentStatus;

public class PaymentView {
	private final PaymentDAO paymentDAO = new PaymentDAO();
	private final BookingDAO bookingDAO = new BookingDAO();
	private final Scanner scanner = new Scanner(System.in);

	
	public void makePayment() {
		try {
			System.out.println("\n=== MAKE PAYMENT ===");
			int bookingId = readInt("Enter Booking ID: ");
			Booking booking = bookingDAO.getBookingById(bookingId);
			if (booking.getStatus() == BookingStatus.CANCELLED) {
				System.out.println("Cannot make payment for a cancelled booking.");
				return;
			}
			if (booking.getStatus() == BookingStatus.CONFIRMED) {
				System.out.println("Payment already completed for this booking.");
				return;
			}
			System.out.println("Total Amount to Pay: ₹" + booking.getTotalAmount());
			System.out.print("Confirm payment? (yes/no): ");
			String confirmation = scanner.nextLine();
			if (!confirmation.equalsIgnoreCase("yes")) {
				System.out.println("Payment cancelled by user.");
				return;
			}
			Payment payment = new Payment();
			payment.setBookingId(bookingId);
			payment.setAmount(booking.getTotalAmount());
			payment.setStatus(PaymentStatus.SUCCESS);
			payment.setPaymentDate(LocalDateTime.now());
			paymentDAO.createPayment(payment);
			bookingDAO.updateBookingStatus(bookingId, BookingStatus.CONFIRMED);
			System.out.println("Payment successful.");
			System.out.println("Booking status updated to CONFIRMED.");
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}
	}

	
	public void viewAllPayments() {
		try {
			List<Payment> payments = paymentDAO.getAllPayments();
			if (payments.isEmpty()) {
				System.out.println("No payments found.");
				return;
			}
			System.out.println("\n=== PAYMENT LIST ===");
			for (Payment payment : payments) {
				printPayment(payment);
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	
	public void viewPaymentsByBooking() {
		try {
			int bookingId = readInt("Enter Booking ID: ");
			List<Payment> payments = paymentDAO.getPaymentsByBookingId(bookingId);
			if (payments.isEmpty()) {
				System.out.println("No payments found for this booking.");
				return;
			}
			System.out.println("\n=== PAYMENTS FOR BOOKING ID: " + bookingId + " ===");
			for (Payment payment : payments) {
				printPayment(payment);
			}
		} catch (HotelReservationException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	 private int readInt(String message) {
		System.out.print(message);
		int value = scanner.nextInt();
		scanner.nextLine();
		return value;
	}

	private void printPayment(Payment payment) {
		System.out.println("-----------------------------------");
		System.out.println("Payment ID : " + payment.getPaymentId());
		System.out.println("Booking ID : " + payment.getBookingId());
		System.out.println("Amount : ₹" + payment.getAmount());
		System.out.println("Status : " + payment.getStatus());
		System.out.println("Payment Date : " + payment.getPaymentDate());
	}

	public void viewPaymentById() {
		try {
			System.out.print("Enter Payment ID: ");
			int paymentId = scanner.nextInt();
			scanner.nextLine();
			Payment payment = paymentDAO.getPaymentById(paymentId);
			if (payment == null) {
				System.out.println("No payment found with ID: " + paymentId);
				return;
			}
			System.out.println("\n=== PAYMENT DETAILS ===");
			System.out.println("-----------------------------------");
			System.out.println("Payment ID : " + payment.getPaymentId());
			System.out.println("Booking ID : " + payment.getBookingId());
			System.out.println("Amount : ₹" + payment.getAmount());
			System.out.println("Status : " + payment.getStatus());
			System.out.println("Paid On : " + payment.getPaymentDate());
		} catch (InvalidPaymentException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Invalid input. Please try again.");
			scanner.nextLine();
		}
	}
}
