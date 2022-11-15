package com.superhero.models;

import java.io.Serializable;
import java.util.List;

public class OrderDataModel  implements Serializable {
    String jsonrpc;
    Result result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public Result getResult() {
        return result;
    }

    public class Result implements Serializable {
    private List<OrderModel> data;


    public List<OrderModel> getData() {
        return data;
    }
}
}

