package com.alamara.ecommerce.customer;

import com.alamara.ecommerce.customer.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(@Valid CustomerRequest request) {
        var customer=repository.save(mapper.toMapper(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer=repository.findById(request.id()).orElseThrow(()-> new CustomerNotFoundException(
                format("Cannot update customer:: No customer found with the provided ID:: %s",request.id())
        ));
        mergeCustomer(customer,request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }if(StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }if(request.address() !=null){
            customer.setAddress(request.address());
        }
    }
}
