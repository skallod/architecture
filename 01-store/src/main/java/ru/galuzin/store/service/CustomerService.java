package ru.galuzin.store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.galuzin.store.domain.CommerceOrder;
import ru.galuzin.store.domain.Customer;
import ru.galuzin.store.repository.CustomerRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    private final EntityManager em;

    public CustomerService(CustomerRepository customerRepository,
        EntityManager em) {
        this.customerRepository = customerRepository;
        this.em = em;
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

    @Transactional
    public Customer findCustomerWithOrders(String email){
        Customer customer = customerRepository.findByEmail(email);
//        EntityGraph graph = this.em.getEntityGraph("graph.Customer.orderSet");
//        Map<String,Object> hints = new HashMap<>();
//        hints.put("javax.persistence.fetchgraph", graph);
//        Customer customer1 = this.em.find(Customer.class, customer.getId(), hints);
        Set<CommerceOrder> orderSet = customer.getOrderSet();
        LOG.trace("size {}",orderSet.size());
        return customer;
    }
}
