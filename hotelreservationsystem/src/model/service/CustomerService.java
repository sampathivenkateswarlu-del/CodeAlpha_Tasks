package model.service;

import java.util.List;

import exception.InvalidInputException;
import model.dao.CustomerDAO;
import model.entity.Customer;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    
    public void createCustomer(String name, String email, String phoneNumber) {

        validateCustomerInput(name, email, phoneNumber);

        Customer customer = new Customer();
        customer.setName(name.trim());
        customer.setEmail(email.trim());
        customer.setPhoneNumber(phoneNumber.trim());

        customerDAO.createCustomer(customer);
    }

    
    public Customer getCustomerById(int customerId) {
        if (customerId <= 0) {
            throw new InvalidInputException("Invalid customer ID");
        }
        return customerDAO.getCustomerById(customerId);
    }

    
    public Customer getCustomerByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Email cannot be empty");
        }
        return customerDAO.getCustomerByEmail(email.trim());
    }

    
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    
    public void updateCustomer(int customerId,
                               String name,
                               String email,
                               String phoneNumber) {

        if (customerId <= 0) {
            throw new InvalidInputException("Invalid customer ID");
        }

        validateCustomerInput(name, email, phoneNumber);

        Customer existingCustomer = customerDAO.getCustomerById(customerId);

        existingCustomer.setName(name.trim());
        existingCustomer.setEmail(email.trim());
        existingCustomer.setPhoneNumber(phoneNumber.trim());

        customerDAO.updateCustomer(existingCustomer);
    }

    
    public void deleteCustomer(int customerId) {
        if (customerId <= 0) {
            throw new InvalidInputException("Invalid customer ID");
        }
        customerDAO.deleteCustomer(customerId);
    }

    
    private void validateCustomerInput(String name, String email, String phoneNumber) {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Customer email cannot be empty");
        }

        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Invalid email format");
        }

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidInputException("Customer phone number cannot be empty");
        }

        if (!phoneNumber.matches("\\d{10,15}")) {
            throw new InvalidInputException("Invalid phone number format");
        }
    }
}
