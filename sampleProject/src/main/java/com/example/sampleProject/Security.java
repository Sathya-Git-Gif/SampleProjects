package com.example.sampleProject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Security {
    @JsonProperty("maturityDate")
    private String maturityDate;
    @JsonProperty("secSID")
    private Integer secSID;

    public Integer getSecSID() {
        return secSID;
    }

    public void setSecSID(Integer secSID) {
        this.secSID = secSID;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    @Override
    public String toString() {
        return "Security{" + "secSID=" + secSID + ", maturityDate='" + maturityDate + '\'' + '}';
    }
}
