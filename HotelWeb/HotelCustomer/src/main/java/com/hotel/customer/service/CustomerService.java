package com.hotel.customer.service;

import com.hotel.common.entity.Customer;
import com.hotel.common.entity.User;
import com.hotel.customer.exception.CustomerAlreadyExistsException;
import com.hotel.customer.model.dto.request.CustomerRequest;
import com.hotel.customer.model.dto.request.command.UpdateCustomerCommand;
import com.hotel.customer.model.dto.response.CustomerInfo;
import com.hotel.customer.model.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException;
    CustomerResponse login (CustomerRequest userRequest) throws Exception;
    List<Customer> getCustomers();
    void deleteCustomer(String email);
    void updateCustomer(UpdateCustomerCommand command);
    Customer getCustomer(String email);
    CustomerInfo getCustomerInfo(String email);
}
