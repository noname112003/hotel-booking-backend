package com.hotel.customer.service.impl;

import com.hotel.common.entity.Customer;
import com.hotel.common.entity.Role;
import com.hotel.common.entity.User;
import com.hotel.customer.exception.CustomerAlreadyExistsException;
import com.hotel.customer.model.dto.request.CustomerRequest;
import com.hotel.customer.model.dto.request.command.UpdateCustomerCommand;
import com.hotel.customer.model.dto.response.CustomerInfo;
import com.hotel.customer.model.dto.response.CustomerResponse;
import com.hotel.customer.repository.CustomerRepository;
import com.hotel.customer.security.jwt.JwtProvider;
import com.hotel.customer.security.user_principle.HotelCustomerDetails;
import com.hotel.customer.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public CustomerResponse login(CustomerRequest customerRequest) throws Exception {
        try {

            Authentication authentication ;
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(customerRequest.getEmail(),customerRequest.getPassword()));
            HotelCustomerDetails userPrincipal = (HotelCustomerDetails) authentication.getPrincipal();
            return CustomerResponse.builder()
                    .token(jwtProvider.generateToken(userPrincipal))
                    .id(userPrincipal.getId())
                    .email(userPrincipal.getEmail())
                    .name(userPrincipal.getName())
                    .build();

        } catch (BadCredentialsException e) {
            // Handle incorrect email or password scenario
            throw new Exception("Email hoặc password không chính xác . Vui lòng thử lại .");
        } catch (DisabledException e) {
            // Handle account disabled scenario
            throw new Exception("Tài khoản của bạn đã bị khóa ");
        }catch (AuthenticationException authenticationException){
            System.err.println(authenticationException);
            throw new Exception("Xác thực không thành công. Vui lòng kiểm tra thông tin đăng nhập của bạn.");

        }
    }

    @Override
    public Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException {
        if (customerRepository.existsByEmail(customer.getEmail())){
            throw new CustomerAlreadyExistsException(customer.getEmail() + " already exists");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        System.out.println(customer.getPassword());


        Customer newCustomer = new Customer() ;
        newCustomer.setName(customer.getName());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPassword(customer.getPassword());
        newCustomer.setPhoneNumber(customer.getPhoneNumber());

        return customerRepository.save(newCustomer);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCustomer(String email) {
        Customer customer = getCustomer(email);
        if (customer != null){
            customerRepository.deleteByEmail(email);
        }

    }

    @Override
    public void updateCustomer(UpdateCustomerCommand command) {
        // Kiểm tra xem customer có tồn tại không
        if (customerRepository.existsByEmail(command.getEmail())) {
            // Nếu tồn tại, thực hiện cập nhật
            customerRepository.updateCustomerByEmail(command.getName(), command.getPhoneNumber(),
                    command.getEmail(), command.getIdentification());
        } else {
            throw new UsernameNotFoundException("Customer with email does not exist.");
        }
    }

    @Override
    public Customer getCustomer(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return customer;

    }

    @Override
    public CustomerInfo getCustomerInfo(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found!");
        }
        return CustomerInfo.convertDTO(customer);
    }
}
