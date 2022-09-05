package com.ibm.recipesapp.Rewards;

import java.io.Serializable;

//TeddyBear
public class TeddyBear implements Serializable {
    private String teddyBearName;
    private int teddyBearAmount;
    private int teddyBearCost;

    public TeddyBear()
    {
        this.teddyBearName = "Teddy Bear";
        this.teddyBearAmount = 0;
        this.teddyBearCost = 1000;
    }

    public TeddyBear(String teddyBearName, int teddyBearAmount, int teddyBearCost) {
        this.teddyBearName = teddyBearName;
        this.teddyBearAmount = teddyBearAmount;
        this.teddyBearCost = teddyBearCost;
    }

    public String getTeddyBearName() {
        return teddyBearName;
    }

    public void setTeddyBearName(String teddyBearName) {
        this.teddyBearName = teddyBearName;
    }

    public int getTeddyBearAmount() {
        return teddyBearAmount;
    }

    public void setTeddyBearAmount(int teddyBearAmount) {
        this.teddyBearAmount = teddyBearAmount;
    }

    public int getTeddyBearCost() {
        return 1000;
    }

    public void setTeddyBearCost(int teddyBearCost) {
        this.teddyBearCost = teddyBearCost;
    }

    @Override
    public String toString() {
        return "TeddyBear{" +
                "teddyBearName='" + teddyBearName + '\'' +
                ", teddyBearAmount=" + teddyBearAmount +
                ", teddyBearCost=" + teddyBearCost +
                '}';
    }
}
