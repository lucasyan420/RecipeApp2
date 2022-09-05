package com.ibm.recipesapp.User;

import com.ibm.recipesapp.Recipe.Recipe;
import com.ibm.recipesapp.Rewards.Pet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

//User
public class User implements Serializable {
    String userID;
    String userName;
    String userEmail;
    String userSelectedPet;
    Pet userPet;
    ArrayList<Recipe> userRecipes;
    int userPoints;

    public User() {
    }

    public User(String userID, String userName, String userEmail) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userSelectedPet = "";
        this.userPet = new Pet("", UUID.randomUUID().toString());
        this.userRecipes = new ArrayList<>();
        this.userPoints = 0;
    }

    public User(String userID, String userName, String userEmail, String userSelectedPet, Pet userPet, ArrayList<Recipe> userRecipes, int userPoints) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userSelectedPet = userSelectedPet;
        this.userPet = userPet;
        this.userRecipes = userRecipes;
        this.userPoints = userPoints;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSelectedPet() {
        return userSelectedPet;
    }

    public void setUserSelectedPet(String userSelectedPet) {
        this.userSelectedPet = userSelectedPet;
    }

    public Pet getUserPet() {
        return userPet;
    }

    public void setUserPet(Pet userPet) {
        this.userPet = userPet;
    }

    public ArrayList<Recipe> getUserRecipes() {
        return userRecipes;
    }

    public void setUserRecipes(ArrayList<Recipe> userRecipes) {
        this.userRecipes = userRecipes;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userSelectedPet='" + userSelectedPet + '\'' +
                ", userPet=" + userPet +
                ", userRecipes=" + userRecipes +
                ", userPoints=" + userPoints +
                '}';
    }
}
