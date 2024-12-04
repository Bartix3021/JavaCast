package com.projectpacks.backend.objectStructure;

import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("1h")
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
