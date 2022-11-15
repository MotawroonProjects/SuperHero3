package com.superhero.models;

import java.io.Serializable;

public class SendOrderBody implements Serializable {
    String jsonrpc = "2.0";
    String id="";
    Params1 params=new Params1();

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Params1 getParams() {
        return params;
    }

    public void setParams(Params1 params) {
        this.params = params;
    }
}

