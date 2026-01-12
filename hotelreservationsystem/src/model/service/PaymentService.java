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

    
    public void makePayment(int bookingId, double amount) {

        if (bookingId <= 0) {
            throw new InvalidInputException("Invalid booking ID");
        }

        if (amount <= 0) {
            throw new InvalidPaymentException("Payment amount must be greater than zero");
        }

        
        Booking booking = bookingDAO.getBookingById(bookingId);

     
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new InvalidPaymentException("Cannot make payment for a cancelled booking");
        }

       
        if (amount != booking.getTotalAmount()) {
            throw new InvalidPaymentException(
                    "Payment amount must be exactly: " + booking.getTotalAmount());
        }

       
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());

       
        paymentDAO.createPayment(payment);
    }

    
    public Payment getPaymentById(int paymentId) {
        if (paymentId <= 0) {
            throw new InvalidPaymentException("Invalid payment ID");
        }
        return paymentDAO.getPaymentById(paymentId);
    }

    
    public List<Payment> getPaymentsByBookingId(int bookingId) {
        if (bookingId <= 0) {
            throw new InvalidInputException("Invalid booking ID");
        }
        return paymentDAO.getPaymentsByBookingId(bookingId);
    }

    
    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }

    
    public void updatePaymentStatus(int paymentId, PaymentStatus status) {

        if (paymentId <= 0) {
            throw new InvalidPaymentException("Invalid payment ID");
        }

        if (status == null) {
            throw new InvalidPaymentException("Payment status cannot be null");
        }

        paymentDAO.updatePaymentStatus(paymentId, status);
    }

    
    public void deletePayment(int paymentId) {
        if (paymentId <= 0) {
            throw new InvalidPaymentException("Invalid payment ID");
        }
        paymentDAO.deletePayment(paymentId);
    }
}
