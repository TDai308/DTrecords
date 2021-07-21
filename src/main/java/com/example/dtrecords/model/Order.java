package com.example.dtrecords.model;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Vinyl vinyl;
    private Integer quantity;
    private Float price;
    private String delivery;
    private String ordercode;
    @Column(name = "datepurchase")
    private String dateTime;

    public Order() {
    }

    public Order(String name, String phone, String email, String address, Vinyl vinyl, Integer quantity, Float price, String delivery, String ordercode, String dateTime) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.vinyl = vinyl;
        this.quantity = quantity;
        this.price = price;
        this.delivery = delivery;
        this.ordercode = ordercode;
        this.dateTime = dateTime;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Vinyl getVinyl() {
        return vinyl;
    }

    public void setVinyl(Vinyl vinyl) {
        this.vinyl = vinyl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
