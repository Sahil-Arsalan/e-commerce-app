package com.alamara.ecommerce.customer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toMapper(@Valid CustomerRequest request) {
        if(request==null){
            return null;
        }
        return Customer.builder().
                id(request.id()).
                firstName(request.firstName()).
                lastName(request.lastName()).
                email(request.email()).
                address(request.address())
                .build();
    }

    public CustomerResponse fromCustomers(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress());
    }
}
