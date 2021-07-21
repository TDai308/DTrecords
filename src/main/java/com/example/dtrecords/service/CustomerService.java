package com.example.dtrecords.service;

import com.example.dtrecords.model.Customer;

public interface CustomerService extends GeneralService<Customer>{

    Customer findCustomerByEmail(String email);
}
