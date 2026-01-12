package model.service;

import java.time.LocalDateTime;
import java.util.List;

import exception.InvalidInputException;
import exception.InvalidPaymentException;
import model.dao.BookingDAO;
import model.dao.PaymentDAO;
import model.entity.Booking;
import model.entity.Payment;
import model.enums.BookingStatus;
import model.enums.PaymentStatus;

public class PaymentService {

    private final PaymentDAO paymentDAO;
    private final BookingDAO bookingDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
        this.bookingDAO = new BookingDAO();
    }

    /**
     * Creates a payment for a booking.
     */
    public void makePayment(int bookingId, double amount) {

        if (bookingId <= 0) {
            throw new InvalidInputException("Invalid booking ID");
        }

        if (amount <= 0) {
            throw new InvalidPaymentException("Payment amount must be greater than zero");
        }

        // 1. Validate booking exists
        Booking booking = bookingDAO.getBookingById(bookingId);

        // 2. Validate booking status
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new InvalidPaymentException("Cannot make payment for a cancelled booking");
        }

        // 3. Validate payment amount
        if (amount != booking.getTotalAmount()) {
            throw new InvalidPaymentException(
                    "Payment amount must be exactly: " + booking.getTotalAmount());
        }

        // 4. Create payment entity
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());

        // 5. Persist payment
        paymentDAO.createPayment(payment);
    }

    /**
     * Retrieves payment by ID.
     */
    public Payment getPaymentById(int paymentId) {
        if (paymentId <= 0) {
            throw new InvalidPaymentException("Invalid payment ID");
        }
        return paymentDAO.getPaymentById(paymentId);
    }

    /**
     * Retrieves payments by booking ID.
     */
    public List<Payment> getPaymentsByBookingId(int bookingId) {
        if (bookingId <= 0) {
            throw new InvalidInputException("Invalid booking ID");
        }
        return paymentDAO.getPaymentsByBookingId(bookingId);
    }

    /**
     * Retrieves all payments.
     */
    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }

    /**
     * Updates payment status.
     */
    public void updatePaymentStatus(int paymentId, PaymentStatus status) {

        if (paymentId <= 0) {
            throw new InvalidPaymentException("Invalid payment ID");
        }

        if (status == null) {
            throw new InvalidPaymentException("Payment status cannot be null");
        }

        paymentDAO.updatePaymentStatus(paymentId, status);
    }

    /**
     * Deletes a payment by ID.
     */
    public void deletePayment(int paymentId) {
        if (paymentId <= 0) {
            throw new InvalidPaymentException("Invalid payment ID");
        }
        paymentDAO.deletePayment(paymentId);
    }
}
