package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.HotelReservationException;
import exception.InvalidInputException;
import model.entity.Customer;
import util.DBConnectionUtil;

public class CustomerDAO {

    private static final String INSERT_CUSTOMER_SQL =
            "INSERT INTO customer (name, email, phone_number) VALUES (?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM customer WHERE customer_id = ?";

    private static final String SELECT_BY_EMAIL_SQL =
            "SELECT * FROM customer WHERE email = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM customer";

    private static final String UPDATE_CUSTOMER_SQL =
            "UPDATE customer SET name = ?, email = ?, phone_number = ? WHERE customer_id = ?";

    private static final String DELETE_CUSTOMER_SQL =
            "DELETE FROM customer WHERE customer_id = ?";

   
    public void createCustomer(Customer customer) {
        validateCustomer(customer);

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER_SQL)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhoneNumber());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to create customer", e);
        }
    }

    
    public Customer getCustomerById(int customerId) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, customerId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to retrieve customer", e);
        }

        throw new InvalidInputException("Customer not found with ID: " + customerId);
    }

    
    public Customer getCustomerByEmail(String email) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_SQL)) {

            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to retrieve customer by email", e);
        }

        throw new InvalidInputException("Customer not found with email: " + email);
    }

   
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to retrieve customers", e);
        }

        return customers;
    }

    
    public void updateCustomer(Customer customer) {
        validateCustomer(customer);

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_SQL)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhoneNumber());
            statement.setInt(4, customer.getCustomerId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new InvalidInputException("Customer not found with ID: " + customer.getCustomerId());
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to update customer", e);
        }
    }

   
    public void deleteCustomer(int customerId) {
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_SQL)) {

            statement.setInt(1, customerId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new InvalidInputException("Customer not found with ID: " + customerId);
            }

        } catch (SQLException e) {
            throw new HotelReservationException("Failed to delete customer", e);
        }
    }

    
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhoneNumber(rs.getString("phone_number"));
        return customer;
    }

    
    private void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null");
        }
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("Customer email cannot be empty");
        }
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().trim().isEmpty()) {
            throw new InvalidInputException("Customer phone number cannot be empty");
        }
    }
}

