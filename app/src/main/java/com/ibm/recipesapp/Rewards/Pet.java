package com.ibm.recipesapp.Rewards;

import java.io.Serializable;

//Pet

public class Pet implements Serializable {
    String petName;
    String petID;
    int petPoints;
    Diamond petDiamond;
    TeddyBear petTeddyBear;
    Snack petSnack;

    public Pet() {
        this.petName = null;
        this.petID = null;
        this.petDiamond = new Diamond();
        this.petTeddyBear = new TeddyBear();
        this.petSnack = new Snack();
    }

    public Pet(String petName) {
        this.petName = petName;
        this.petID = null;
        this.petDiamond = new Diamond();
        this.petTeddyBear = new TeddyBear();
        this.petSnack = new Snack();
    }

    public Pet(String petName, String petID) {
        this.petName = petName;
        this.petID = petID;
        this.petDiamond = new Diamond();
        this.petTeddyBear = new TeddyBear();
        this.petSnack = new Snack();
    }

    public Pet(String petName, String petID, int petPoints, Diamond petDiamond, TeddyBear petTeddyBear, Snack petSnack) {
        this.petName = petName;
        this.petID = petID;
        this.petPoints = petPoints;
        this.petDiamond = petDiamond;
        this.petTeddyBear = petTeddyBear;
        this.petSnack = petSnack;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetID() {
        return petID;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public int getPetPoints() {
        return petPoints;
    }

    public void addPetPoints(int newPoints) {
        this.petPoints += newPoints;
    }

    public void minusPetPoints(int newPoints) {this.petPoints -= newPoints; }

    public Diamond getPetDiamond() {
        return petDiamond;
    }

    public void setPetDiamond(Diamond petDiamond) {
        this.petDiamond = petDiamond;
    }

    public TeddyBear getPetTeddyBear() {
        return petTeddyBear;
    }

    public void setPetTeddyBear(TeddyBear petTeddyBear) {
        this.petTeddyBear = petTeddyBear;
    }

    public Snack getPetSnack() {
        return petSnack;
    }

    public void setPetSnack(Snack petSnack) {
        this.petSnack = petSnack;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petName='" + petName + '\'' +
                ", petID='" + petID + '\'' +
                ", petPoints=" + petPoints +
                ", petDiamond=" + petDiamond +
                ", petTeddyBear=" + petTeddyBear +
                ", petSnack=" + petSnack +
                '}';
    }
}
