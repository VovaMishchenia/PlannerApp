package com.example.plannerapp.dto;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class BedRequestDTO {
//    private  String email;
//    private String password;
//    private String invalid;
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getInvalid() {
//        return invalid;
//    }
//
//    public void setInvalid(String invalid) {
//        this.invalid = invalid;
//    }

    private String type;
    private String title;
    private int status;
    private String traceId;
    private Hashtable<String, String[]> errors;

    public BedRequestDTO() {
        this.errors = new Hashtable<String, String[]>();
    }

    public Hashtable<String, String[]> getErrors() {
        return errors;
    }

    public void setErrors(Hashtable<String, String[]> errors) {
        this.errors = errors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }


}
