package ru.galuzin.store.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.galuzin.store.domain.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	Customer findByName(String name);
	Customer findByEmail(String email);
	List<Customer> findAll();
}
