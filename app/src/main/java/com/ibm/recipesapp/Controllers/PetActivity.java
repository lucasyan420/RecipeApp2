package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ibm.recipesapp.R;
import com.ibm.recipesapp.Rewards.Pet;
import com.ibm.recipesapp.User.User;

import java.util.Timer;
import java.util.TimerTask;


// PetActivity
public class PetActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    User user;
    Pet pet;

    String currentAction = "";
    int counter = 0;

    TextView petNameTextView, rewardTextView, diamondTextView, snackTextView, teddyBearTextView;
    TextView diamondCountTextView, snackCountTextView, teddyBearCountTextview;
    ImageView petImageView, diamondImageView, snackImageView, teddyBearImageView;
    Button buyRewardButton;
    ProgressBar wealthProgressBar, hungerProgressBar, happinessProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        user = (User) getIntent().getSerializableExtra("user");
        pet = user.getUserPet();

        petNameTextView = findViewById(R.id.petName_TextView_PetActivity);
        rewardTextView = findViewById(R.id.points_TextView_PetActivity);
        diamondTextView = findViewById(R.id.diamondCost_TextView_PetActivity);
        snackTextView = findViewById(R.id.snackCost_TextView_PetActivity);
        teddyBearTextView = findViewById(R.id.teddyBearCost_TextView_PetActivity);
        diamondCountTextView = findViewById(R.id.diamondCount_TextView_PetActivity);
        snackCountTextView = findViewById(R.id.snackCount_TextView_PetActivity);
        teddyBearCountTextview = findViewById(R.id.teddyBearCount_TextView_PetActivity);
        petImageView = findViewById(R.id.petImage_ImageView_PetActivity);
        diamondImageView = findViewById(R.id.diamond_ImageView_PetActivity);
        snackImageView = findViewById(R.id.snack_ImageView_PetActivity);
        teddyBearImageView = findViewById(R.id.teddyBear_ImageView_PetActivity);
        buyRewardButton = findViewById(R.id.buyButton);
        wealthProgressBar = findViewById(R.id.wealth_ProgressBar_PetActivity);
        hungerProgressBar = findViewById(R.id.hunger_ProgressBar_PetActivity);
        happinessProgressBar = findViewById(R.id.happiness_ProgressBar_PetActivity);

        rewardTextView.setText(Integer.toString(user.getUserPet().getPetPoints()));

        petNameTextView.setText(pet.getPetName());
        diamondCountTextView.setText(Integer.toString(pet.getPetDiamond().getDiamondAmount()));
        snackCountTextView.setText(Integer.toString(pet.getPetSnack().getSnackAmount()));
        teddyBearCountTextview.setText(Integer.toString(pet.getPetTeddyBear().getTeddyBearAmount()));

        setPetImage(user);
        progressBar();
        petPressed();
    }

    private void setPetImage(User user) {
        switch(user.getUserSelectedPet())
        {
            case "dog":
                petImageView.setImageResource(R.drawable.dog);
                break;
            case "cat":
                petImageView.setImageResource(R.drawable.cat);
                break;
            case "hamster":
                petImageView.setImageResource(R.drawable.hamster);
                break;
            case "unicorn":
                petImageView.setImageResource(R.drawable.unicorn);
                break;
            case "parrot":
                petImageView.setImageResource(R.drawable.parrot);
                break;
            case "pig":
                petImageView.setImageResource(R.drawable.pig);
                break;
        }
    }

    public void selectedAction(View view){
        switch(view.getId()){
            case R.id.diamond_ImageView_PetActivity:
                enlargeImage(findViewById(R.id.diamond_ImageView_PetActivity));
                restoreImage();
                buyRewardButton.setText("Buy Diamonds");
                currentAction = "diamond";
                break;

            case R.id.snack_ImageView_PetActivity:
                enlargeImage(findViewById(R.id.snack_ImageView_PetActivity));
                restoreImage();
                buyRewardButton.setText("Buy Snacks");
                currentAction = "snack";
                break;

            case R.id.teddyBear_ImageView_PetActivity:
                enlargeImage(findViewById(R.id.teddyBear_ImageView_PetActivity));
                restoreImage();
                buyRewardButton.setText("Buy Teddy Bears");
                currentAction = "teddy bear";
                break;
        }
    }

    public void restoreImage(){
        switch(currentAction)
        {
            case "":
                break;

            case "teddy bear":
                reduceImage(findViewById(R.id.teddyBear_ImageView_PetActivity));
                break;

            case "diamond":
                reduceImage(findViewById(R.id.diamond_ImageView_PetActivity));

            case "snack":
                reduceImage(findViewById(R.id.snack_ImageView_PetActivity));
        }
    }

    public void enlargeImage(View view)
    {
        view.requestLayout();
        view.getLayoutParams().height = 200;
        view.getLayoutParams().width = 200;
    }

    public void reduceImage(View view)
    {
        view.requestLayout();
        view.getLayoutParams().height = 150;
        view.getLayoutParams().width = 150;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void petPressed()
    {
        petImageView.setOnTouchListener((view, motionEvent) ->
        {
            Log.i("Touchevent", "touch detected");

            int eventType = motionEvent.getActionMasked();

            switch(eventType)
            {
                case MotionEvent.ACTION_DOWN:
                    petImageView.requestLayout();
                    petImageView.getLayoutParams().height = petImageView.getLayoutParams().height + 100;
                    petImageView.getLayoutParams().width = petImageView.getLayoutParams().height + 100;
                    petClicked();
                    break;

                case MotionEvent.ACTION_UP:
                    petImageView.requestLayout();
                    petImageView.getLayoutParams().height = petImageView.getLayoutParams().height - 100;
                    petImageView.getLayoutParams().width = petImageView.getLayoutParams().height - 100;
            }
            return true;
        });
    }

    public void buyItem(View view)
    {
        switch(currentAction)
        {
            case "teddy bear":
                buyTeddyBears();
                updateUser();
                break;

            case "snack":
                buySnacks();
                updateUser();
                break;

            case "diamond":
                buyDiamonds();
                updateUser();
                break;
        }
    }

    private void petClicked() {
        switch(currentAction)
        {
            case "":
                break;

            case "teddy bear":
                updateUserTeddyBear();
                break;

            case "diamond":
                updateUserDiamond();
                break;

            case "snack":
                updateUserSnack();
                break;
        }
    }

    public void updateUser()
    {
        firestore.collection("users").document(mAuth.getCurrentUser().getUid()).set(user).addOnCompleteListener(task -> {

        });
    }

    public void updateUserTeddyBear(){
        if(pet.getPetTeddyBear().getTeddyBearAmount() >= 1)
        {
            happinessProgressBar.incrementProgressBy(10);
            pet.getPetTeddyBear().setTeddyBearAmount(pet.getPetTeddyBear().getTeddyBearAmount() - 1);
            user.setUserPet(pet);

            teddyBearCountTextview.setText(String.valueOf(pet.getPetTeddyBear().getTeddyBearAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough teddy bears!", Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    public void updateUserDiamond(){
        if(pet.getPetDiamond().getDiamondAmount() >= 1)
        {
            wealthProgressBar.incrementProgressBy(10);
            pet.getPetDiamond().setDiamondAmount(pet.getPetDiamond().getDiamondAmount() - 1);
            user.setUserPet(pet);

            diamondCountTextView.setText(String.valueOf(pet.getPetDiamond().getDiamondAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough diamonds!", Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    public void updateUserSnack(){
        if(pet.getPetSnack().getSnackAmount() >= 1)
        {
            hungerProgressBar.incrementProgressBy(10);
            pet.getPetSnack().setSnackAmount(pet.getPetSnack().getSnackAmount() - 1);
            user.setUserPet(pet);

            snackCountTextView.setText(String.valueOf(pet.getPetSnack().getSnackAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough snacks", Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    public void buyTeddyBears()
    {
        if(pet.getPetPoints() - pet.getPetTeddyBear().getTeddyBearCost() >= 0)
        {
            pet.getPetTeddyBear().setTeddyBearAmount(pet.getPetTeddyBear().getTeddyBearAmount() + 1);
            pet.minusPetPoints(pet.getPetTeddyBear().getTeddyBearCost());
            user.setUserPet(pet);
            rewardTextView.setText(Integer.toString(pet.getPetPoints()));
            teddyBearCountTextview.setText(String.valueOf(pet.getPetTeddyBear().getTeddyBearAmount()));
            Toast.makeText(getApplicationContext(), "You bought a teddy bear for " + pet.getPetName(), Toast.LENGTH_LONG).show();
            restoreImage();
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough points!", Toast.LENGTH_LONG).show();
        }
    }

    public void buySnacks()
    {
        if(pet.getPetPoints() - pet.getPetSnack().getSnackCost() >= 0)
        {
            pet.getPetSnack().setSnackAmount(pet.getPetSnack().getSnackAmount() +1);
            pet.minusPetPoints(pet.getPetSnack().getSnackCost());
            user.setUserPet(pet);
            rewardTextView.setText(Integer.toString(pet.getPetPoints()));
            snackCountTextView.setText(String.valueOf(pet.getPetSnack().getSnackAmount()));
            Toast.makeText(getApplicationContext(), "You bought 1 snack for " + pet.getPetName(), Toast.LENGTH_LONG).show();
            restoreImage();
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough points", Toast.LENGTH_LONG).show();
        }
    }

    public void buyDiamonds()
    {
        if(pet.getPetPoints() - pet.getPetDiamond().getDiamondCost() >= 0)
        {
            pet.getPetDiamond().setDiamondAmount(pet.getPetDiamond().getDiamondAmount() + 1);
            pet.minusPetPoints(pet.getPetDiamond().getDiamondCost());
            user.setUserPet(pet);
            rewardTextView.setText(Integer.toString(pet.getPetPoints()));
            diamondCountTextView.setText(String.valueOf(pet.getPetDiamond().getDiamondAmount()));
            Toast.makeText(getApplicationContext(), "You bought 1 diamond for " + pet.getPetName(), Toast.LENGTH_LONG).show();
            restoreImage();
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough points", Toast.LENGTH_LONG).show();
        }
    }

    private void progressBar() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                happinessProgressBar.setProgress(counter);
                hungerProgressBar.setProgress(counter);
                wealthProgressBar.setProgress(counter);

                if(counter == 100)
                {
                    timer.cancel();
                    decreaseProgressBars();
                }
            }
        };

        timer.schedule(timerTask, 0, 20);
    }

    public void decreaseProgressBars()
    {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Thread thread = new Thread(() ->
                {
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    happinessProgressBar.setProgress(happinessProgressBar.getProgress() - 1);
                    hungerProgressBar.setProgress(hungerProgressBar.getProgress() - 1);
                    wealthProgressBar.setProgress(wealthProgressBar.getProgress() - 1);

                });
                thread.start();
            }
        };

        timer.schedule(timerTask, 0, 10000);
    }

    public void backButton(View view)
    {
        Intent backButton = new Intent(this, RecipesActivity.class);
        backButton.putExtra("user", user);
        startActivity(backButton);
    }
}