package com.ibm.recipesapp.Rewards;

import java.io.Serializable;

//Snack

public class Snack implements Serializable {
    private String snackName;
    private int snackAmount;
    private int snackCost;

    public Snack()
    {
        this.snackName = "snack";
        this.snackAmount = 0;
        this.snackCost = 500;
    }

    public Snack(String snackName){
        this.snackName = "snack";
    }

    public Snack(String snackName, int snackAmount, int snackCost) {
        this.snackName = snackName;
        this.snackAmount = snackAmount;
        this.snackCost = snackCost;
    }

    public String getName() {
        return snackName;
    }

    public void setName(String name) {
        this.snackName = name;
    }

    public int getSnackAmount() {
        return snackAmount;
    }

    public void setSnackAmount(int snackAmount) {
        this.snackAmount = snackAmount;
    }

    public int getSnackCost() {
        return snackCost;
    }

    public void setSnackCost(int snackCost) {
        this.snackCost = snackCost;
    }

    @Override
    public String toString() {
        return "Snack{" +
                "snackName='" + snackName + '\'' +
                ", snackAmount=" + snackAmount +
                ", snackCost=" + snackCost +
                '}';
    }
}
