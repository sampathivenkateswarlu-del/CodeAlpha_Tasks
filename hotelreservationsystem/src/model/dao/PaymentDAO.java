package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exception.HotelReservationException;
import exception.InvalidPaymentException;
import model.entity.Payment;
import model.enums.PaymentStatus;
import util.DBConnectionUtil;

public class PaymentDAO {

    private static final String INSERT_PAYMENT_SQL =
            "INSERT INTO payment (booking_id, amount, status, payment_date) VALUES (?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM payment WHERE payment_id = ?";

    private static final String SELECT_BY_BOOKING_ID_SQL =
            "SELECT * FROM payment WHERE booking_id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM payment";

    private static final String UPDATE_STATUS_SQL =
            "UPDATE payment SET status = ? WHERE payment_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM payment WHERE payment_id = ?";

    /**
     * Creates a new payment record.
     */
    public void createPayment(Payment payment) {
        validatePayment(payment);

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PAYMENT_SQL)) {

            statement.setInt(1, payment.getBookingId());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getStatus().name());
            statement.setTimestamp(4, Timestamp.valueOf(payment.getPaymentDate()));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to create payment", e);
        }
    }

    /**
     * Retrieves a payment by ID.
     */
    public Payment getPaymentById(int paymentId) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, paymentId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPayment(rs);
                }
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to retrieve payment", e);
        }

        throw new InvalidPaymentException("Payment not found with ID: " + paymentId);
    }

    /**
     * Retrieves payments by booking ID.
     */
    public List<Payment> getPaymentsByBookingId(int bookingId) {
        List<Payment> payments = new ArrayList<>();

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_BOOKING_ID_SQL)) {

            statement.setInt(1, bookingId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapResultSetToPayment(rs));
                }
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to retrieve payments for booking ID: " + bookingId, e);
        }

        return payments;
    }

    /**
     * Retrieves all payments.
     */
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to retrieve payments", e);
        }

        return payments;
    }

    /**
     * Updates payment status.
     */
    public void updatePaymentStatus(int paymentId, PaymentStatus status) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_SQL)) {

            statement.setString(1, status.name());
            statement.setInt(2, paymentId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new InvalidPaymentException("Payment not found with ID: " + paymentId);
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to update payment status", e);
        }
    }

    /**
     * Deletes payment by ID.
     */
    public void deletePayment(int paymentId) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setInt(1, paymentId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new InvalidPaymentException("Payment not found with ID: " + paymentId);
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to delete payment", e);
        }
    }

    /**
     * Maps ResultSet to Payment entity.
     */
    private Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(rs.getInt("payment_id"));
        payment.setBookingId(rs.getInt("booking_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setStatus(PaymentStatus.valueOf(rs.getString("status")));

        Timestamp timestamp = rs.getTimestamp("payment_date");
        payment.setPaymentDate(timestamp != null ? timestamp.toLocalDateTime() : LocalDateTime.now());

        return payment;
    }

    /**
     * Validates payment object.
     */
    private void validatePayment(Payment payment) {
        if (payment == null) {
            throw new InvalidPaymentException("Payment cannot be null");
        }
        if (payment.getBookingId() <= 0) {
            throw new InvalidPaymentException("Invalid booking ID");
        }
        if (payment.getAmount() <= 0) {
            throw new InvalidPaymentException("Payment amount must be greater than zero");
        }
        if (payment.getStatus() == null) {
            throw new InvalidPaymentException("Payment status cannot be null");
        }
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }
    }
}

