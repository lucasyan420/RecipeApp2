package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.ibm.recipesapp.R;
import com.ibm.recipesapp.Recipe.Recipe;
import com.ibm.recipesapp.User.User;
import com.ibm.recipesapp.defaultRecipesRecyclerAdapter;
import com.ibm.recipesapp.recipesRecyclerAdapter;

import java.util.ArrayList;

// DefaultRecipeActivity
public class DefaultRecipeActivity extends AppCompatActivity {
    private ArrayList<Recipe> recipeList;
    private RecyclerView recyclerView;
    private defaultRecipesRecyclerAdapter.RecyclerViewClickListener listener;
    User user;

    private String tacosImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Ftaco.png?alt=media&token=e09fe08b-6c7d-4202-adf0-7ef71605142d";
    private String sushiImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fsushi.png?alt=media&token=cf1ac2d6-2efb-4c05-b687-6820fe6db9ca";
    private String steakImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fsteak.png?alt=media&token=18cde75b-126a-4d46-9c03-b24337466f36";
    private String saladImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fsalad.png?alt=media&token=9cef00b8-3288-41bf-8243-d35ac4c627c6";
    private String puddingImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fpudding.png?alt=media&token=8f082d53-3cbb-4b7e-bca2-37450fce768f";
    private String pizzaImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fpizza.png?alt=media&token=74852482-989c-46b9-a452-ee48a21be8ba";
    private String pastaImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fpasta.png?alt=media&token=3d83b41b-9cc2-4888-9799-b1e3622954a6";
    private String paellaImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fpaella.png?alt=media&token=8d5b5aef-d5d3-4a52-8efd-0ae5ea704902";
    private String noodlesImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fnoodles.png?alt=media&token=6216f1c1-0b60-4ef5-a487-29be8b75312d";
    private String soupImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fhot-soup.png?alt=media&token=48999707-a1bf-4f03-9c7e-fb87e59a3e53";
    private String burgerImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fhamburger.png?alt=media&token=797336c5-2d57-4b90-ba0c-702bdd54ac79";
    private String friedRiceImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Ffried-rice.png?alt=media&token=983e5356-c47d-4d53-87b6-0225c338485f";
    private String friedChickenImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Ffried-chicken.png?alt=media&token=cb4ef03d-517b-4325-b7dc-27d4f6ade7d2";
    private String fishAndChipsImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Ffish-and-chips.png?alt=media&token=b12ee40d-cdcb-4fe1-834a-d35a705194b6";
    private String dumplingsImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fdumpling.png?alt=media&token=bcb3bd02-45db-4d37-bd1f-e1c0e9904e85";
    private String crepeImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fcrepe.png?alt=media&token=4955eb34-4637-45b2-82a1-94a7f9a046c5";
    private String roastChickenImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fchicken-leg.png?alt=media&token=d2436cd9-4b94-4c73-9bb9-83fa8752b6a3";
    private String cakeImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fcake.png?alt=media&token=c57ae408-342b-4c86-bb13-c4772b19497c";
    private String barbequeImageURL = "https://firebasestorage.googleapis.com/v0/b/recipeapp-53903.appspot.com/o/default_recipe_images%2Fbarbecue.png?alt=media&token=88346244-6ce7-44f9-8d38-d7562194377d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_recipe);

        recyclerView = findViewById(R.id.recyclerView_RecyclerView_DefaultRecipeActivity);
        user = (User) getIntent().getSerializableExtra("user");
        recipeList = new ArrayList<>();
        setRecipeInfo();
        setAdapter();
    }

    private void setAdapter(){
        setOnClickListener();

        defaultRecipesRecyclerAdapter adapter = new defaultRecipesRecyclerAdapter(recipeList,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener(){
        listener = (view, position) ->
        {
            Intent goToAddDefaultRecipeActivity = new Intent(getApplicationContext(),
                    AddDefaultRecipeActivity.class);
            goToAddDefaultRecipeActivity.putExtra("image", recipeList.get(position).getRecipeImage());
            goToAddDefaultRecipeActivity.putExtra("name", recipeList.get(position).getRecipeName());
            goToAddDefaultRecipeActivity.putExtra("user", user);
            startActivity(goToAddDefaultRecipeActivity);
        };
    }

    private void setRecipeInfo(){
        recipeList.add(new Recipe(null, "Tacos", 0, 0, tacosImageURL, null, null));
        recipeList.add(new Recipe(null, "Sushi", 0, 0, sushiImageURL, null, null));
        recipeList.add(new Recipe(null, "Steak", 0, 0, steakImageURL, null, null));
        recipeList.add(new Recipe(null, "Salad", 0, 0, saladImageURL, null, null));
        recipeList.add(new Recipe(null, "Pudding", 0, 0, puddingImageURL, null, null));
        recipeList.add(new Recipe(null, "Pizza", 0, 0, pizzaImageURL, null, null));
        recipeList.add(new Recipe(null, "Pasta", 0, 0, pastaImageURL, null, null));
        recipeList.add(new Recipe(null, "Paella", 0, 0, paellaImageURL, null, null));
        recipeList.add(new Recipe(null, "Noodles", 0, 0, noodlesImageURL, null, null));
        recipeList.add(new Recipe(null, "Soup", 0, 0, soupImageURL, null, null));
        recipeList.add(new Recipe(null, "Burger", 0, 0, burgerImageURL, null, null));
        recipeList.add(new Recipe(null, "Fried Rice", 0, 0, friedRiceImageURL, null, null));
        recipeList.add(new Recipe(null, "Fried Chicken", 0, 0, friedChickenImageURL, null, null));
        recipeList.add(new Recipe(null, "Fish and Chips", 0, 0, fishAndChipsImageURL, null, null));
        recipeList.add(new Recipe(null, "Dumplings", 0, 0, dumplingsImageURL, null, null));
        recipeList.add(new Recipe(null, "Crepe", 0, 0, crepeImageURL, null, null));
        recipeList.add(new Recipe(null, "Roast Chicken", 0, 0, roastChickenImageURL, null, null));
        recipeList.add(new Recipe(null, "Cake", 0, 0, cakeImageURL, null, null));
        recipeList.add(new Recipe(null, "Barbeque", 0, 0, barbequeImageURL, null, null));
    }

    public void goToAddRecipeActivity(View view){
        Intent goToAddRecipeActivity = new Intent(this, AddRecipeActivity.class);
        goToAddRecipeActivity.putExtra("user", user);
        startActivity(goToAddRecipeActivity);
    }

    public void backButton(View view){
        Intent goBack = new Intent(this, RecipesActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }
}