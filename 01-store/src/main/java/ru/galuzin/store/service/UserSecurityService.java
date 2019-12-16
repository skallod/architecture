package ru.galuzin.store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.galuzin.store.domain.Customer;
import ru.galuzin.store.domain.security.UserDetailsImpl;
import ru.galuzin.store.repository.CustomerRepository;

@Service
public class UserSecurityService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

    private CustomerRepository customerRepository;

    @Autowired
    public UserSecurityService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByName(name);
        if(null == customer) {
            LOG.warn("Username {} not found", name);
            throw new UsernameNotFoundException("Username "+name+" not found");
        }
        return new UserDetailsImpl(customer);
    }
}

