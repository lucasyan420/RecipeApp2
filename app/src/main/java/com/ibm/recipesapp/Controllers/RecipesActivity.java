package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ibm.recipesapp.R;
import com.ibm.recipesapp.Recipe.Recipe;
import com.ibm.recipesapp.Rewards.Pet;
import com.ibm.recipesapp.User.User;
import com.ibm.recipesapp.recipesRecyclerAdapter;

import java.util.ArrayList;


// RecipesActivity

public class RecipesActivity extends AppCompatActivity {
    private ArrayList<Recipe> recipeList;
    private RecyclerView recyclerView;
    private TextView noRecipes;
    private ImageView noRecipesImage;
    private TextView header;

    private recipesRecyclerAdapter.RecyclerViewClickListener listener;
    recipesRecyclerAdapter adapter;

    Pet userPet;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser mUser;
    User user;
    String intentTime;
    ImageView goPetActivityButton;
    boolean hasRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        recipeList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        
        recyclerView = findViewById(R.id.recyclerView_RecyclerView_RecipesActivity);
        goPetActivityButton = findViewById(R.id.goToPetActivity_ImageView_RecipesActivity);
        noRecipes = findViewById(R.id.noRecipes_TextView_RecipesActivity);
        noRecipesImage = findViewById(R.id.cooking_ImageView_RecipesActivity);
        noRecipesImage.setVisibility(View.INVISIBLE);
        header = findViewById(R.id.recipes_TextView_RecipesActivity);
        
        intentTime = (String) getIntent().getSerializableExtra("Time");
        
        getUser();
    }

    private void getUser() {
        try
        {
            firestore.collection("users").document(mUser.getUid()).get()
                    .addOnCompleteListener(task ->
                    {
                        DocumentSnapshot ds = task.getResult();
                        if (task.isSuccessful())
                        {
                            user = ds.toObject(User.class);
                            userPet = user.getUserPet();
                            Log.d("USER OBJECT", "user name: " + user.getUserName());
                            recipeList = user.getUserRecipes();

                            if (recipeList.size() == 0)
                            {
                                hasRecipes = false;
                                showNoRecipes();
                                noRecipesImage.setVisibility(View.VISIBLE);
                            } else
                            {
                                hasRecipes = true;
                            }
                            setAdapter();
                            setPetImage(user);

                            header.setText(user.getUserName() + "'s Recipes");
                        }
                    });
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "error getting user", Toast.LENGTH_SHORT).show();
        }
    }

    public void showNoRecipes() {
        if (hasRecipes == false)
        {
            noRecipes.setText("Start Creating Recipes");
        } else
        {
            noRecipes.setText(null);
        }
    }

    private void setAdapter(){
        setOnClickListener();
        adapter = new recipesRecyclerAdapter(recipeList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener(){
        listener = (v, position) ->
        {
            Intent intent = new Intent(getApplicationContext(), CookingActivity.class);
            intent.putExtra("Time", String.valueOf(recipeList.get(position).getRecipeTime()));
            intent.putExtra("Points", String.valueOf(recipeList.get(position).getRecipePoints()));
            intent.putExtra("Name", recipeList.get(position).getRecipeName());
            intent.putExtra("Image", recipeList.get(position).getRecipeImage());
            intent.putExtra("Recipe ID", recipeList.get(position).getRecipeID());
            intent.putExtra("Recipe Ingredients", recipeList.get(position).getRecipeIngredients());
            intent.putExtra("Recipe Steps", recipeList.get(position).getRecipeSteps());
            intent.putExtra("User", user);
            intent.putExtra("Pet", userPet);
            intent.putExtra("User ID", user.getUserID());
            intent.putExtra("Position", position);
            startActivity(intent);
        };
    }

    private void setPetImage(User user) {
        switch(user.getUserSelectedPet())
        {
            case "dog":
                goPetActivityButton.setImageResource(R.drawable.dog);
                break;
            case "cat":
                goPetActivityButton.setImageResource(R.drawable.cat);
                break;
            case "hamster":
                goPetActivityButton.setImageResource(R.drawable.hamster);
                break;
            case "unicorn":
                goPetActivityButton.setImageResource(R.drawable.unicorn);
                break;
            case "parrot":
                goPetActivityButton.setImageResource(R.drawable.parrot);
                break;
            case "pig":
                goPetActivityButton.setImageResource(R.drawable.pig);
                break;
        }
    }

    public void goToPetActivity(View view){
        Intent goToPetActivity = new Intent(this, PetActivity.class);
        goToPetActivity.putExtra("user", user);
        startActivity(goToPetActivity);
    }

    public void goToDefaultRecipeActivity(View view){
        Intent goToDefaultRecipeActivity = new Intent(this, DefaultRecipeActivity.class);
        goToDefaultRecipeActivity.putExtra("user", user);
        startActivity(goToDefaultRecipeActivity);
    }

    public void goToMysteryRecipeActivity(View view){
        Intent goToMysteryRecipeActivity = new Intent(this, MysteryRecipeActivity.class);
        goToMysteryRecipeActivity.putExtra("user", user);
        startActivity(goToMysteryRecipeActivity);
    }

    public void signOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}