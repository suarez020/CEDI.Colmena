package com.crystal.colmenacedi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("source")
    @Expose
    private String source;

    public Errors(Boolean status, Integer code, String source) {
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
        return "Errors{" +
                "status=" + status +
                ", code=" + code +
                ", source='" + source + '\'' +
                '}';
    }
}
