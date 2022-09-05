package com.ibm.recipesapp.Rewards;

import java.io.Serializable;

// Diamond

public class Diamond implements Serializable {
    private String diamondName;
    private int diamondAmount;
    private int diamondCost;

    public Diamond()
    {
        this.diamondName = "diamond";
        this.diamondAmount = 0;
        this.diamondCost = 100;
    }

    public Diamond(String diamondName){
        this.diamondName = "diamond";
    }

    public Diamond (String diamondName, int diamondAmount, int diamondCost){
        this.diamondName = "diamond";
        this.diamondAmount = diamondAmount;
        this.diamondCost = diamondCost;
    }

    public String getName() {
        return diamondName;
    }

    public void setName(String name) {
        this.diamondName = name;
    }

    public int getDiamondAmount() {
        return diamondAmount;
    }

    public void setDiamondAmount(int diamondAmount) {
        this.diamondAmount = diamondAmount;
    }

    public int getDiamondCost() {
        return diamondCost;
    }

    public void setDiamondCost(int diamondCost) {
        this.diamondCost = diamondCost;
    }

    @Override
    public String toString() {
        return "Diamond{" +
                "diamondName='" + diamondName + '\'' +
                ", diamondAmount=" + diamondAmount +
                ", diamondCost=" + diamondCost +
                '}';
    }
}
