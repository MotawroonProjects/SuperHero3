package com.superhero.models;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    int  id;
     String name;
     String customer;
     String phone;
     String expected_closing;
     String latitude;
     String longitude;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCustomer() {
        return customer;
    }

    public String getPhone() {
        return phone;
    }

    public String getExpected_closing() {
        return expected_closing;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
