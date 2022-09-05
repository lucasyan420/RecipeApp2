package com.ibm.recipesapp.Recipe;

import java.io.Serializable;
import java.util.ArrayList;

// Recipe

public class Recipe implements Serializable {
    String recipeID;
    String recipeName;
    int recipeTime;
    int recipePoints;
    String recipeImage;
    ArrayList<String> recipeIngredients;
    ArrayList<String> recipeSteps;

    public Recipe(){

    }

    public Recipe(String recipeID, String recipeName, int recipeTime, int recipePoints, String recipeImage,
                  ArrayList<String> recipeIngredients, ArrayList<String> recipeSteps) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeTime = recipeTime;
        this.recipePoints = recipePoints;
        this.recipeImage = recipeImage;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeTime() {
        return recipeTime;
    }

    public void setRecipeTime(int recipeTime) {
        this.recipeTime = recipeTime;
    }

    public ArrayList<String> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<String> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public ArrayList<String> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(ArrayList<String> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public int getRecipePoints() {
        return recipePoints;
    }

    public void setRecipePoints(int recipePoints) {
        this.recipePoints = recipePoints;
    }

    @Override
    public String toString() {
        return "Recipe:" +
                "Name = " + recipeName + '\n' +
                "Time = " + recipeTime + '\n' +
                "Points = " + recipePoints + "\n" +
                "Ingredients = " + recipeIngredients + "\n" +
                "Steps = " + recipeSteps;
    }
}
