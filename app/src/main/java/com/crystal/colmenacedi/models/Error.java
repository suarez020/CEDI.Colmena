package com.crystal.colmenacedi.models;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("status")
    private Boolean status;

    @SerializedName("code")
    private Integer code;

    @SerializedName("source")
    private String source;

    public Error(Boolean status, Integer code, String source) {
        this.status = status;
        this.code = code;
        this.source = source;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Error{" +
                "\nstatus=" + status +
                ", \ncode=" + code +
                ", \nsource='" + source + '\'' +
                "\n"+'}';
    }
}
