package ru.galuzin.store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.galuzin.store.domain.Book;
import ru.galuzin.store.domain.Customer;
import ru.galuzin.store.repository.CustomerRepository;

@Service
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createUser(Customer customer/*, Set<UserRole> userRoles*/) {
        Customer localCustomer = customerRepository.findByEmail(customer.getEmail());
        if(localCustomer != null) {
            LOG.info("User with email {} already exist. Nothing will be done. ", customer.getEmail());
        } else {
            localCustomer = customerRepository.save(customer);
        }
        return localCustomer;
    }

    public Customer findByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    public Customer getOne(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

}
