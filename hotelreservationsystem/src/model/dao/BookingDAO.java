package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.BookingNotFoundException;
import exception.HotelReservationException;
import model.entity.Booking;
import model.enums.BookingStatus;
import util.DBConnectionUtil;

public class BookingDAO {

    private static final String INSERT_BOOKING_SQL =
            "INSERT INTO booking (customer_id, room_id, check_in_date, check_out_date, total_amount, status) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM booking WHERE booking_id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM booking";

    private static final String UPDATE_STATUS_SQL =
            "UPDATE booking SET status = ? WHERE booking_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM booking WHERE booking_id = ?";

    /**
     * Creates a new booking record.
     */
    public void createBooking(Booking booking) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOKING_SQL)) {

            statement.setInt(1, booking.getCustomerId());
            statement.setInt(2, booking.getRoomId());
            statement.setDate(3, Date.valueOf(booking.getCheckInDate()));
            statement.setDate(4, Date.valueOf(booking.getCheckOutDate()));
            statement.setDouble(5, booking.getTotalAmount());
            statement.setString(6, booking.getStatus().name());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to create booking", e);
        }
    }

    /**
     * Retrieves a booking by its ID.
     */
    public Booking getBookingById(int bookingId) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, bookingId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBooking(rs);
                }
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to retrieve booking", e);
        }

        throw new BookingNotFoundException("Booking not found with ID: " + bookingId);
    }

    /**
     * Retrieves all bookings.
     */
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to retrieve bookings", e);
        }

        return bookings;
    }

    /**
     * Updates booking status.
     */
    public void updateBookingStatus(int bookingId, BookingStatus status) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_SQL)) {

            statement.setString(1, status.name());
            statement.setInt(2, bookingId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new BookingNotFoundException("Booking not found with ID: " + bookingId);
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to update booking status", e);
        }
    }

    /**
     * Deletes a booking by ID.
     */
    public void deleteBooking(int bookingId) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setInt(1, bookingId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new BookingNotFoundException("Booking not found with ID: " + bookingId);
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to delete booking", e);
        }
    }

    /**
     * Maps ResultSet to Booking entity.
     */
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setCustomerId(rs.getInt("customer_id"));
        booking.setRoomId(rs.getInt("room_id"));
        booking.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
        booking.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
        booking.setTotalAmount(rs.getDouble("total_amount"));
        booking.setStatus(BookingStatus.valueOf(rs.getString("status")));
        return booking;
    }
}

