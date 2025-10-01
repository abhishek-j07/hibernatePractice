package com.orm;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "STUDENT_ADDRESS")
public class StudentAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private int addressId;

    @Column(length = 50, name = "STREET")
    private String street;

    @Column(length = 50, name = "PINCODE")
    private int pincode;

    @Column(length = 50, name = "CITY")
    private String city;

    @Column(name = "DATE_ADDED")
    private Date dateAdded;

    @Transient
    private double x;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Lob
    @Column(name = "IMAGE", columnDefinition = "LONGBLOB")
    private byte[] image;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
