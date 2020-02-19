package ru.galuzin.store.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.galuzin.store.domain.CommerceOrder;

@Repository
public interface CommerceOrderRepository extends CrudRepository<CommerceOrder, Long> {
}
