package ru.galuzin.store.controller;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.galuzin.store.domain.Book;
import ru.galuzin.store.domain.Customer;
import ru.galuzin.store.dto.CustomerDto;
import ru.galuzin.store.service.BookService;
import ru.galuzin.store.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerConroller {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    CustomerService customerService;

    @Autowired
    DozerBeanMapper mapper;

    @GetMapping(value="/{id}")
    public CustomerDto getCustomer(@PathVariable("id") Long id) {
        log.info("get customer request");
        Customer one = customerService.getOne(id);
        CustomerDto dto = mapper.map(one, CustomerDto.class);
        log.info("customer dozer = {}", dto);
        return dto;
    }
}
