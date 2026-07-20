package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.ResponseState;

public class SystemResponse {

    String Key;
    String Value;
    String Description;
    ResponseState State;

    public SystemResponse () {super ();}

    public SystemResponse(String key, String value, String description, ResponseState state) {
        Key = key;
        Value = value;
        Description = description;
        State = state;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ResponseState getState() {
        return State;
    }

    public void setState(ResponseState state) {
        State = state;
    }
}
