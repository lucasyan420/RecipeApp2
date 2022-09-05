package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ibm.recipesapp.R;
import com.ibm.recipesapp.Rewards.Pet;
import com.ibm.recipesapp.User.User;

// PetCreationActivity

public class PetCreationActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    User user;
    Pet userPet;
    String selectedPet = "";
    EditText petNameEditText;
    ImageView parrotImageView, dogImageView, unicornImageView, hamsterImageView, pigImageView, catImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_creation);
        
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        try{
            user = (User) getIntent().getSerializableExtra("user");
            System.out.println(user.toString());
            System.out.println("testing");
            System.out.println(user.getUserPet());
            userPet = user.getUserPet();
            System.out.println(userPet.toString() + "ttttttt");
            System.out.println("hi");
        } catch(Exception err)
        {
            err.printStackTrace();
        }

        petNameEditText = findViewById(R.id.name_EditText_PetCreationActivity);

        parrotImageView = findViewById(R.id.parrot_ImageView_PetCreationActivity);
        dogImageView = findViewById(R.id.dog_ImageView_PetCreationActivity);
        unicornImageView = findViewById(R.id.unicorn_ImageView_PetCreationActivity);
        hamsterImageView = findViewById(R.id.hamster_ImageView_PetCreationActivity);
        pigImageView = findViewById(R.id.pig_ImageView_PetCreationActivity);
        catImageView = findViewById(R.id.cat_ImageView_PetCreationActivity);
    }
    
    public void selectedPet(View view){
        switch (view.getId()){
            case R.id.parrot_ImageView_PetCreationActivity:
                enlargeImage(findViewById(R.id.parrot_ImageView_PetCreationActivity));
                restoreImage();
                selectedPet = "parrot";
                break;

            case R.id.dog_ImageView_PetCreationActivity:
                enlargeImage(findViewById(R.id.dog_ImageView_PetCreationActivity));
                restoreImage();
                selectedPet = "dog";
                break;

            case R.id.unicorn_ImageView_PetCreationActivity:
                enlargeImage(findViewById(R.id.unicorn_ImageView_PetCreationActivity));
                restoreImage();
                selectedPet = "unicorn";
                break;

            case R.id.hamster_ImageView_PetCreationActivity:
                enlargeImage(findViewById(R.id.hamster_ImageView_PetCreationActivity));
                restoreImage();
                selectedPet = "hamster";
                break;

            case R.id.pig_ImageView_PetCreationActivity:
                enlargeImage(findViewById(R.id.pig_ImageView_PetCreationActivity));
                restoreImage();
                selectedPet = "pig";
                break;

            case R.id.cat_ImageView_PetCreationActivity:
                enlargeImage(findViewById(R.id.cat_ImageView_PetCreationActivity));
                restoreImage();
                selectedPet = "cat";
                break;
        }
    }

    private void enlargeImage(View view) {
        view.requestLayout();
        view.getLayoutParams().height = 500;
        view.getLayoutParams().width = 500;
    }

    public void reduceImage(View view)
    {
        view.requestLayout();
        view.getLayoutParams().height = 300;
        view.getLayoutParams().width = 300;
    }

    public void restoreImage()
    {
        switch (selectedPet)
        {
            case "":
                break;
            case "parrot":
                reduceImage(findViewById(R.id.parrot_ImageView_PetCreationActivity));
                break;
            case "dog":
                reduceImage(findViewById(R.id.dog_ImageView_PetCreationActivity));
                break;
            case "unicorn":
                reduceImage(findViewById(R.id.unicorn_ImageView_PetCreationActivity));
                break;
            case "hamster":
                reduceImage(findViewById(R.id.hamster_ImageView_PetCreationActivity));
                break;
            case "pig":
                reduceImage(findViewById(R.id.pig_ImageView_PetCreationActivity));
                break;
            case "cat":
                reduceImage(findViewById(R.id.cat_ImageView_PetCreationActivity));
                break;
        }
    }

    public void changePetName(View view)
    {
        try{
            System.out.println("this is a test");

            String petName = petNameEditText.getText().toString();
            if(petName.equals("")){
                Toast.makeText(getApplicationContext(), "Add pet name", Toast.LENGTH_LONG).show();
            }
            else{
                userPet.setPetName(petName);
                user.setUserPet(userPet);
                user.setUserSelectedPet(selectedPet);

                firestore.collection("users")
                        .document(mAuth.getCurrentUser().getUid())
                        .set(user).addOnCompleteListener(task ->
                        {
                            //to be completed
                        });

                goTaskActivity();
            }
        } catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    private void goTaskActivity() {
        Intent intent = new Intent(this, RecipesActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}