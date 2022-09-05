package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ibm.recipesapp.R;
import com.ibm.recipesapp.Recipe.Recipe;
import com.ibm.recipesapp.User.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

// AddDefaultRecipeActivity

public class AddDefaultRecipeActivity extends AppCompatActivity {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private TextView recipeName;
    private ImageView recipePhoto;

    private EditText recipeTime;
    private EditText recipePoints;
    private EditText recipeIngredients;
    private EditText recipeSteps;

    User user;

    String imageURL;
    String name;
    String recipeID;
    int time;
    int points;
    ArrayList<String> ingredients;
    ArrayList<String> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_default_recipe);

        user = (User) getIntent().getSerializableExtra("user");

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            imageURL = extras.getString("image");
            name = extras.getString("name");
        }

        recipeName = findViewById(R.id.recipeName_TextView_AddDefaultRecipeActivity);
        recipePhoto = findViewById(R.id.recipePhoto_ImageView_AddDefaultRecipeActivity);
        recipeTime = findViewById(R.id.recipeTime_TextInputEditText_AddDefaultRecipeActivity);
        recipePoints = findViewById(R.id.recipePoints_TextInputEditText_AddDefaultRecipeActivity);
        recipeIngredients = findViewById(R.id.recipeIngredients_TextInputEditText_AddDefaultRecipeActivity);
        recipeSteps = findViewById(R.id.recipeSteps_TextInputEditText_AddDefaultRecipeActivity);

        recipeName.setText(name);

        Glide.with(recipePhoto.getContext()).load(imageURL).centerCrop().into(recipePhoto);
    }

    public void addRecipe(View view)
    {
        try{
            time = Integer.parseInt(recipeTime.getText().toString());
            points = Integer.parseInt(recipePoints.getText().toString());
            recipeID = UUID.randomUUID().toString();

            String recipeIngredientsString = recipeIngredients.getText().toString();
            String recipeStepsString = recipeSteps.getText().toString();

            ingredients = new ArrayList<>(Arrays.asList(recipeIngredientsString.split("\\s*,\\s*")));
            steps = new ArrayList<>(Arrays.asList(recipeStepsString.split("\\s*,\\s*")));

            Recipe recipe = new Recipe(recipeID, name, time, points, imageURL, ingredients, steps);
            firestore.collection("users").document(user.getUserID()).update("userRecipes", FieldValue.arrayUnion(recipe));
            Toast.makeText(getApplicationContext(), "Added Recipe", Toast.LENGTH_LONG).show();
            clearPage();
        } catch (Exception err)
        {
            err.printStackTrace();
            Toast.makeText(getApplicationContext(), "Incorrect input. Only input numbers", Toast.LENGTH_LONG).show();
            recipeTime.setText(null);
            recipePoints.setText(null);
            recipeIngredients.setText(null);
            recipeSteps.setText(null);
        }
    }

    public void clearPage()
    {
        recipeTime.setText(null);
        recipePoints.setText(null);
        recipeIngredients.setText(null);
        recipeSteps.setText(null);
        recipePhoto.setImageDrawable(null);
        back();
    }

    public void back(){
        Intent goBack = new Intent(this, DefaultRecipeActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }

    public void backButton(View view){
        Intent goBack = new Intent(this, DefaultRecipeActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }
}