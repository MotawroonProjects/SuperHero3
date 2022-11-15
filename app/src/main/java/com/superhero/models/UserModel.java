package com.superhero.models;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {

     String jsonrpc;
     User result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public User getResult() {
        return result;
    }

    public static class User implements Serializable {
        List<String> user_id;

        public List<String> getUser_id() {
            return user_id;
        }
    }
}
