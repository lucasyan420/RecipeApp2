package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ibm.recipesapp.R;
import com.ibm.recipesapp.Recipe.Recipe;
import com.ibm.recipesapp.Rewards.Pet;
import com.ibm.recipesapp.User.User;
import com.ibm.recipesapp.Util.PrefUtil;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


// CookingActivity

public class CookingActivity extends AppCompatActivity {
    User user;
    String userID;
    String recipeID;
    int position;
    Pet userPet;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private CountDownTimer timer = null;
    private Long timerLengthSeconds = 0L;
    private Long secondsRemaining = 0L;
    private TimerState timerState = TimerState.Stopped;
    private FloatingActionButton playButton, pauseButton, stopButton;
    private MaterialProgressBar progressCountdown;
    private TextView timeDisplay;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private int timeInt;
    private String pointsRewarded;
    private String name;
    private String imageURL;

    private TextView recipeNameTextView;
    private ImageView recipeImageView;
    private TextView recipePointsTextView;
    private ListView recipeIngredientsListView;
    private ListView recipeStepsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);

        playButton = findViewById(R.id.play_FloatingActionButton_CookingActivity);
        pauseButton = findViewById(R.id.pause_FloatingActionButton_CookingActivity);
        stopButton = findViewById(R.id.stop_FloatingActionButton_CookingActivity);
        progressCountdown = findViewById(R.id.progressBar_MaterialProgressBar_CookingActivity);
        timeDisplay = findViewById(R.id.timeDisplay_TextView_CookingActivity);
        recipeIngredientsListView = findViewById(R.id.ingredients_ListView_CookingActivity);
        recipeStepsListView = findViewById(R.id.steps_ListView_CookingActivity);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        recipeID = (String) getIntent().getSerializableExtra("Recipe ID");
        userID = (String) getIntent().getSerializableExtra("User ID");
        position = (int) getIntent().getSerializableExtra("Position");
        user = (User) getIntent().getSerializableExtra("User");
        userPet = user.getUserPet();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            pointsRewarded = extras.getString("Points");
            name = extras.getString("Name");
            imageURL = extras.getString("Image");
        }

        recipeNameTextView = findViewById(R.id.cookingPage_TextView_CookingActivity);
        recipeNameTextView.setText(name);
        recipeImageView = findViewById(R.id.recipePhoto_ImageView_CookingActivity);
        Glide.with(getApplicationContext()).load(imageURL).centerCrop().into(recipeImageView);
        recipePointsTextView = findViewById(R.id.points_TextView_CookingActivity);
        recipePointsTextView.setText(pointsRewarded);

        updateIngredientsList();
        updateStepsList();

        playButton.setOnClickListener(view ->
        {
            startTimer();
            timerState = TimerState.Running;
            updateButtons();
        });
        pauseButton.setOnClickListener(view ->
        {
            timer.cancel();
            timerState = TimerState.Paused;
            updateButtons();
        });
        stopButton.setOnClickListener(view ->
        {
            timer.cancel();
            onTimerFinished();
        });

    }

    public void updateIngredientsList()
    {
        ArrayList<String> ingredientsList = (ArrayList<String>) getIntent().getSerializableExtra("Recipe Ingredients");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientsList);
        recipeIngredientsListView.setAdapter(adapter);
    }

    public void updateStepsList()
    {
        ArrayList<String> stepsList = (ArrayList<String>) getIntent().getSerializableExtra("Recipe Steps");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stepsList);
        recipeStepsListView.setAdapter(adapter);
    }

    public enum TimerState
    {
        Stopped, Paused, Running
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initialiseTimer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if(timerState == TimerState.Running)
        {
            timer.cancel();
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this);
        PrefUtil.setSecondsRemaining(secondsRemaining, this);
        PrefUtil.setTimerState(timerState, this);
    }

    private void initialiseTimer()
    {
        timerState = PrefUtil.getTimerState(this);
        if(timerState == TimerState.Stopped)
        {
            setNewTimerLength();
        } else
        {
            setPreviousTimerLength();
        }

        secondsRemaining = (timerState == TimerState.Running || timerState == TimerState.Paused) ?
                PrefUtil.getSecondsRemaining(this) : timerLengthSeconds;
        if(secondsRemaining <= 0)
        {
            onTimerFinished();
        } else if(timerState == TimerState.Running)
        {
            startTimer();
        }
        updateButtons();
        updateCountdownUI();
    }

    private void onTimerFinished()
    {
        timerState = TimerState.Stopped;
        setNewTimerLength();
        progressCountdown.setProgress(0);
        PrefUtil.setSecondsRemaining(timerLengthSeconds, this);
        secondsRemaining = timerLengthSeconds;
        updateButtons();
        updateCountdownUI();
    }

    private void startTimer()
    {
        timerState = TimerState.Running;
        timer = new CountDownTimer(secondsRemaining * 1000, 1000)
        {
            @Override
            public void onTick(long l) {
                secondsRemaining = l/1000;
                updateCountdownUI();
            }

            @Override
            public void onFinish() {
                onTimerFinished();
            }
        }.start();
    }

    private void setNewTimerLength()
    {
        String time = "0";
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            time = extras.getString("Time");
            timeInt = Integer.parseInt(time);
            System.out.println("TIME VALUE HERE" + timeInt);
        }
        timerLengthSeconds = (timeInt * 60L);
        progressCountdown.setMax(timerLengthSeconds.intValue());
    }

    private void setPreviousTimerLength()
    {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this);
        progressCountdown.setMax(timerLengthSeconds.intValue());
    }

    private void updateCountdownUI()
    {
        int minutesUntilFinished = secondsRemaining.intValue() / 60;
        int secondsInMinuteUntilFinished = secondsRemaining.intValue() - minutesUntilFinished * 60;
        String secondsStr = Integer.toString(secondsInMinuteUntilFinished);
        String newStr = secondsStr.length() == 2 ? secondsStr : "0" + secondsStr;
        timeDisplay.setText(minutesUntilFinished + ":" + newStr);
        progressCountdown.setProgress((timerLengthSeconds.intValue() - secondsRemaining.intValue()));
    }

    private void updateButtons()
    {
        switch (timerState)
        {
            case Running:
                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
                break;

            case Paused:
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(true);
                break;

            case Stopped:
                playButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
                break;
        }
    }

    public void updateTotalPoints(View view)
    {
        Toast.makeText(this, name + " recipe cooked, points have been added", Toast.LENGTH_LONG).show();

        initialiseTimer();

        Long secondsLeft = PrefUtil.getSecondsRemaining(this);
        Long originalSeconds = PrefUtil.getPreviousTimerLengthSeconds(this);

        double secondsLeftInt = secondsLeft.intValue();
        double originalSecondsInt = originalSeconds.intValue();
        double taskPercentage = 1 - (((originalSecondsInt - secondsLeftInt) / originalSecondsInt));

        userPet.addPetPoints(Integer.parseInt(pointsRewarded));

        String recipeID = (String) getIntent().getSerializableExtra("Recipe ID");
        for (com.ibm.recipesapp.Recipe.Recipe recipe : user.getUserRecipes())
        {
            if(recipeID.equals(recipe.getRecipeID()))
            {
//                user.getUserRecipes().remove(recipe);
                firestore.collection("users").document(user.getUserID()).set(user);
                break;
            }
        }
        Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);
        startActivity(intent);
    }

    public void backButton(View view){
        Intent goBack = new Intent(this, RecipesActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }
}