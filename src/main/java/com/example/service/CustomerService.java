package com.example.service;

import com.example.domain.Customer;
import com.example.model.CustomerModel;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("customerService")
public class  CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerModel saveCustomer(CustomerModel customer) {
        Customer customerModel = populateCustomerEntity(customer);
        return populateCustomerModel(customerRepository.save(customerModel));
    }

    public boolean deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
        return true;
    }

    public List<CustomerModel> getAllCustomers() {
        List<CustomerModel> customers = new ArrayList<>();
        List<Customer> customerList = customerRepository.findAll();
        customerList.forEach(customer -> {
            customers.add(populateCustomerModel(customer));
        });
        return customers;
    }

    public CustomerModel getCustomerById(Long customerId) {
        return populateCustomerModel( customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found")));
    }

    private CustomerModel populateCustomerModel(final Customer customer){
        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(customer.getId());
        customerModel.setName(customer.getName());
        customerModel.setEmail(customer.getEmail());
        return  customerModel;
    }

    private Customer populateCustomerEntity(CustomerModel customerModel){
        Customer customer = new Customer();
        customer.setId(customerModel.getId());
        customer.setName(customerModel.getName());
        customer.setEmail(customerModel.getEmail());
        return  customer;
    }
}