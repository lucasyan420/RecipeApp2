package com.ibm.recipesapp.Controllers;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ibm.recipesapp.R;
import com.ibm.recipesapp.Recipe.Recipe;
import com.ibm.recipesapp.User.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

// AddRecipeActivity

public class AddRecipeActivity extends AppCompatActivity {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageReference;
    User user;

    private String recipeUUID;

    private EditText recipeName, recipeTime, recipePoints, recipeIngredients, recipeSteps;
    private String nameString, timeString, pointsString, ingredientsString, stepsString;
    private ArrayList<String> ingredientsArrayList, stepsArrayList;

    private ImageView recipePhoto;
    private Uri imageUri;
    public String photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        user = (User) getIntent().getSerializableExtra("user");

        recipeName = findViewById(R.id.recipeName_TextInputEditText_AddRecipeActivity);
        recipeTime = findViewById(R.id.recipeTime_TextInputEditText_AddRecipeActivity);
        recipePoints = findViewById(R.id.recipePoints_TextInputEditText_AddRecipeActivity);
        recipeIngredients = findViewById(R.id.recipeIngredients_TextInputEditText_AddRecipeActivity);
        recipeSteps = findViewById(R.id.recipeSteps_TextInputEditText_AddRecipeActivity);
        recipePhoto = findViewById(R.id.recipePhoto_ImageView_AddRecipeActivity);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        recipePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                choosePicture();
            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                }
            }
    );

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            recipePhoto.setImageURI(imageUri);
        }
    }

    public boolean formValid(){
        nameString = recipeName.getText().toString();
        timeString = recipeTime.getText().toString();
        pointsString = recipePoints.getText().toString();
        ingredientsString = recipeIngredients.getText().toString();
        stepsString = recipeSteps.getText().toString();

        ingredientsArrayList = new ArrayList<>(Arrays.asList(ingredientsString.split("\\s*,\\s*")));
        stepsArrayList = new ArrayList<>(Arrays.asList(stepsString.split("\\s*,\\s*")));

        if(!nameString.equals("") && !timeString.equals("") && !pointsString.equals("") && !ingredientsString.equals("") && !stepsString.equals(""))
        {
            return true;
        } else
        {
            Toast.makeText(this, "missing info", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void addRecipe(View view)
    {
        if(formValid())
        {

            try{
                Integer timeInt = Integer.parseInt(timeString);
                Integer pointsInt = Integer.parseInt(pointsString);

                recipeUUID = UUID.randomUUID().toString();

                final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
                progressBar.setVisibility(View.VISIBLE);

                final String randomKey = UUID.randomUUID().toString();
                StorageReference riversRef = storageReference.child("recipe_images/" + randomKey);

                riversRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    progressBar.setVisibility((View.INVISIBLE));
                    Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_SHORT).show();

                    riversRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        photoURI = String.valueOf(uri);
                        firestore.collection("userRecipes").document(recipeUUID).update("image", photoURI);

                        for(Recipe recipe : user.getUserRecipes())
                        {
                            if(recipe.getRecipeID().equals(recipeUUID))
                            {
                                recipe.setRecipeImage(photoURI);
                            }
                        }

                        Recipe recipe = new Recipe(recipeUUID, nameString, timeInt, pointsInt, photoURI, ingredientsArrayList, stepsArrayList);
                        firestore.collection( "userRecipes").document(recipe.getRecipeID()).set(recipe);
                        firestore.collection("users").document(user.getUserID()).update("userRecipes", FieldValue.arrayUnion(recipe));
                        Toast.makeText(getApplicationContext(), "Added recipe", Toast.LENGTH_LONG).show();
                        clearPage();
                    });
                })
                        .addOnFailureListener(e -> {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                        })
                        .addOnProgressListener(snapshot -> {
                            double progressPercent = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            int progress = (int) progressPercent;
                            progressBar.setProgress(progress, true);
                        });
            } catch (Exception err)
            {
                err.printStackTrace();
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            }
        } else
        {
            Toast.makeText(getApplicationContext(), "Fill in all fields correctly", Toast.LENGTH_LONG).show();
        }
    }

    public void clearPage()
    {
        recipeName.setText(null);
        recipeTime.setText(null);
        recipePoints.setText(null);
        recipePhoto.setImageDrawable(null);
        back();
    }

    public void back()
    {
        Intent goBackIntent = new Intent(this, DefaultRecipeActivity.class);
        goBackIntent.putExtra("user", user);
        startActivity(goBackIntent);
    }

    public void backButton(View view){
        Intent goBack = new Intent(this, DefaultRecipeActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }
}