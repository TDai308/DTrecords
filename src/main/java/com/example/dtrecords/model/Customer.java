package com.example.dtrecords.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Validator {

    @Id
    @GeneratedValue
    private Long userID;
    private String username;
    private String password;
    private String name;
    private String phonenumber;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(String username, String password, String name, String phonenumber, String email, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;
        String phonenumber = customer.getPhonenumber();
        ValidationUtils.rejectIfEmpty(errors,"phonenumber","phonenumber.empty");
        if (phonenumber.length()>11 || phonenumber.length()<10) {
            errors.rejectValue("phonenumber","phonenumber.length");
        }
        if (!phonenumber.startsWith("0")) {
            errors.rejectValue("phonenumber","phonenumber.startsWith");
        }
        if (!phonenumber.matches("(^$|[0-9]*$)")){
            errors.rejectValue("phonenumber", "phonenumber.matches");
        }
    }
}
