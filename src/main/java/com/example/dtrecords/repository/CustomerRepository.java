package com.example.dtrecords.repository;

import com.example.dtrecords.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    Customer findCustomerByEmail(String email);
}
