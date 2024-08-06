package com.abid.ecommerce.customer;


import com.abid.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(customerMapper.toCustomer(request));

        return customer.getId();
    }

    public void udpateCustomer(CustomerRequest request) {

        var customer= customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Connot update customer:: No customer found with the provided ID:: %s",request.id())
                ));
        mergerCustomer(customer, request);
        customerRepository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if (request.address() != null){
            customer.setAddress(request.address());
        }

    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(()-> new CustomerNotFoundException(format("No Customer found with the provided ID:: %s", customerId)));
    }

    public Void deleteCustomerById(String customerId) {
        customerRepository.deleteById(customerId);
        return null;
    }
}
