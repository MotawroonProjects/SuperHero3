package com.superhero.models;

import java.io.Serializable;

public class LoginBody implements Serializable {
    String jsonrpc = "2.0";
    Params params=new Params();

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}

