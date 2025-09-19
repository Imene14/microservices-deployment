package com.ms.ecommerce.Customer;

import ch.qos.logback.core.util.StringUtil;
import com.ms.ecommerce.Exception.CustomerNotFoundException.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
       return customer.getId();
    }

    public String updateCustomer(CustomerRequest request) {
         var customer = repository.findById(request.id())
                 .orElseThrow(() -> new CustomerNotFoundException(
                     String.format("Cannot update customer with this id ", request.id())
                 ));
         mergeCustomer(customer, request);
         repository.save(customer);

        return null;
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
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

    public List<CustomerResponse> findAllCustomer() {
        return repository.findAll().stream().map(mapper::fromCustomer).collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.existsById(customerId);
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId).map(mapper::fromCustomer).orElseThrow(() -> new CustomerNotFoundException(format("No customer founded with thid id:: %s", customerId)));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }

}
