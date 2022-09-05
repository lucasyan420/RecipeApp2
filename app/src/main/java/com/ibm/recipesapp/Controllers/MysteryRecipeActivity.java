package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.recipesapp.R;
import com.ibm.recipesapp.User.User;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// MysteryRecipeActivity
public class MysteryRecipeActivity extends AppCompatActivity {
    User user;
    TextView currentTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;
    int currentHour;
    String currentTimePeriod = "";
    String favouriteCuisine = "";
    String mysteryRecipeName = "";

    ImageView mysteryRecipeImage;
    ImageView yesImageView;
    ImageView noImageView;

    TextView chosenRecipe;
    TextView chooseFavouriteRecipe;
    TextView recommendedRecipe;

    Button mysteryRecipeButton;

    int ESAPoints = 0;
    int SAMPoints = 0;
    int EPoints = 0;
    int LAPoints = 0;
    int APoints = 0;

    int count;
    int yesCount;
    int noCount;

    int[] recipeImages = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6,
            R.drawable.la1, R.drawable.la2, R.drawable.la3, R.drawable.la4, R.drawable.la5, R.drawable.la6,
            R.drawable.e1, R.drawable.e2, R.drawable.e3, R.drawable.e4, R.drawable.e5, R.drawable.e6,
            R.drawable.sam1, R.drawable.sam2, R.drawable.sam3, R.drawable.sam4, R.drawable.sam5, R.drawable.sam6,
            R.drawable.esa1, R.drawable.esa2, R.drawable.esa3, R.drawable.esa4, R.drawable.esa5, R.drawable.esa6
    };

    int[] ESABreakfastImages = {R.drawable.esa_b_1, R.drawable.esa_b_2, R.drawable.esa_b_3};
    String[] ESABreakfastNames = {"Dim Sum", "Onigiri", "Beef Pho"};
    int[] ESALunchImages = {R.drawable.esa_l_1, R.drawable.esa_l_2, R.drawable.esa_l_3};
    String[] ESALunchNames = {"Dumplings", "Unagi Don", "Bibimbap"};
    int[] ESADinnerImages = {R.drawable.esa_d_1, R.drawable.esa_d_2, R.drawable.esa_d_3};
    String[] ESADinnerNames = {"Fish", "Sukiyaki", "Pad Thai"};
    int[] ESASnackImages = {R.drawable.esa_s_1, R.drawable.esa_s_2, R.drawable.esa_s_3};
    String[] ESASnackNames = {"Egg Tarts", "Tamagoyaki", "Chicken Satay"};

    int[] SAMBreakfastImages = {R.drawable.sam_b_1, R.drawable.sam_b_2, R.drawable.sam_b_3};
    String[] SAMBreakfastNames = {"Upma", "Shakshuka", "Greek Yogurt"};
    int[] SAMLunchImages = {R.drawable.sam_l_1, R.drawable.sam_l_2, R.drawable.sam_l_3};
    String[] SAMLunchNames = {"Lamb Shawarma", "Biryani", "Couscous Salad"};
    int[] SAMDinnerImages = {R.drawable.sam_d_1, R.drawable.sam_d_2, R.drawable.sam_d_3};
    String[] SAMDinnerNames = {"Chicken Tikka Masala", "Baked Cod", "Tajine"};
    int[] SAMSnackImages = {R.drawable.sam_s_1, R.drawable.sam_s_2, R.drawable.sam_s_3};
    String[] SAMSnackNames = {"Samosa", "Baklava", "Pita"};

    int[] EBreakfastImages = {R.drawable.e_b_1, R.drawable.e_b_2, R.drawable.e_b_3};
    String[] EBreakfastNames = {"Full English", "Frittata", "Belgian Waffles"};
    int[] ELunchImages = {R.drawable.e_l_1, R.drawable.e_l_2, R.drawable.e_l_3};
    String[] ELunchNames = {"Neapolitan Pizza", "Schnitzel", "Smørrebrød"};
    int[] EDinnerImages = {R.drawable.e_d_1, R.drawable.e_d_2, R.drawable.e_d_3};
    String[] EDinnerNames = {"Seafood Paella", "Chicken Confit", "Lasagne"};
    int[] ESnackImages = {R.drawable.e_s_1, R.drawable.e_s_2, R.drawable.e_s_3};
    String[] ESnackNames = {"Croquettes", "French Onion Soup", "Cheese Fondue"};

    int[] LABreakfastImages = {R.drawable.la_b_1, R.drawable.la_b_2, R.drawable.la_b_3};
    String[] LABreakfastNames = {"Chilaquiles", "Mangú", "Gallo Pinto"};
    int[] LALunchImages = {R.drawable.la_l_1, R.drawable.la_l_2, R.drawable.la_l_3};
    String[] LALunchNames = {"Mole de Pollo", "Ropa Vieja", "Ceviche"};
    int[] LADinnerImages = {R.drawable.la_d_1, R.drawable.la_d_2, R.drawable.la_d_3};
    String[] LADinnerNames = {"Picanha", "Beef Enchiladas", "Arroz con Frijoles"};
    int[] LASnackImages = {R.drawable.la_s_1, R.drawable.la_s_2, R.drawable.la_s_3};
    String[] LASnackNames = {"Pan Dulce", "Cassava Pone", "Empanadas"};

    int[] ABreakfastImages = {R.drawable.a_b_1, R.drawable.a_b_2, R.drawable.a_b_3};
    String[] ABreakfastNames = {"Chicken & Waffle", "Pancakes", "Hash Browns"};
    int[] ALunchImages = {R.drawable.a_l_1, R.drawable.a_l_2, R.drawable.a_l_3};
    String[] ALunchNames = {"Cob Salad", "Mac N Cheese", "Philly Cheese Steak"};
    int[] ADinnerImages = {R.drawable.a_d_1, R.drawable.a_d_2, R.drawable.a_d_3};
    String[] ADinnerNames = {"Gumbo", "BBQ Brisket", "Pot Roast"};
    int[] ASnackImages = {R.drawable.a_s_1, R.drawable.a_s_2, R.drawable.a_s_3};
    String[] ASnackNames = {"Hot Dog", "Apple Pie", "French Fries"};

    Random random;
    int randomMysterySlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystery_recipe);
        mysteryRecipeImage = findViewById(R.id.mysteryRecipe_ImageView_MysteryRecipeActivity);

        yesImageView = findViewById(R.id.yes_ImageView_MysteryRecipeActivity);
        noImageView = findViewById(R.id.no_ImageView_MysteryRecipeActivity);
        chosenRecipe = findViewById(R.id.chosenRecipe_TextView_MysteryRecipeActivity);
        chosenRecipe.setVisibility(View.INVISIBLE);
        chooseFavouriteRecipe = findViewById(R.id.chooseFavouriteRecipe_TextView_MysteryRecipeActivity);
        recommendedRecipe = findViewById(R.id.recommendedRecipe_TextView_MysteryRecipeActivity);
        recommendedRecipe.setVisibility(View.INVISIBLE);
        mysteryRecipeButton = findViewById(R.id.discoverMysteryRecipe_Button_MysteryRecipeActivity);
        mysteryRecipeButton.setVisibility(View.INVISIBLE);

        shuffleRecipeImages();

        count = 0;
        yesCount = 0;
        noCount = 0;

        random = new Random();
        randomMysterySlot = random.nextInt(2);

        mysteryRecipeImage.setImageResource(R.drawable.mystery_box);

        user = (User) getIntent().getSerializableExtra("user");
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss ");
        time = simpleDateFormat.format(calendar.getTime());
        timePeriod(time);
        System.out.println("Current time period: " + currentTimePeriod);
    }

    public void timePeriod(String time)
    {
        currentHour = Integer.parseInt(time.substring(0, 2));
        if(6 < currentHour && currentHour< 11){
            currentTimePeriod = "breakfast";
        }
        else if(11 < currentHour && currentHour < 14){
            currentTimePeriod = "lunch";
        }
        else if(14 < currentHour && currentHour < 17){
            currentTimePeriod = "snack";
        }
        else if(17 < currentHour && currentHour < 21){
            currentTimePeriod = "dinner";
        }
        else{
            currentTimePeriod = "snack";
        }
    }

    public void noImage(View view)
    {
        if(count < 30)
        {
            mysteryRecipeImage.setImageResource(recipeImages[count]);
            count++;
            noCount++;
        }
        else{
            mysteryRecipeImage.setImageResource(R.drawable.mystery_box);
            Toast.makeText(getApplicationContext(), "You have completed the survey. Click the discover mystery recipe button!", Toast.LENGTH_SHORT).show();
            mysteryRecipeButton.setVisibility(View.VISIBLE);
            findFavouriteCuisine();
        }
    }

    public void yesImage(View view)
    {
        if(count < 30)
        {
            mysteryRecipeImage.setImageResource(recipeImages[count]);

            if(recipeImages[count] == R.drawable.a1 || recipeImages[count] == R.drawable.a2 ||
                    recipeImages[count] == R.drawable.a3 || recipeImages[count] == R.drawable.a4 ||
                    recipeImages[count] == R.drawable.a5 || recipeImages[count] == R.drawable.a6)
            {
                APoints += 5;
                EPoints += 2;
                LAPoints += 2;
            }
            else if(recipeImages[count] == R.drawable.la1 || recipeImages[count] == R.drawable.la2 || recipeImages[count] == R.drawable.la3 || recipeImages[count] == R.drawable.la4 || recipeImages[count] == R.drawable.la5 || recipeImages[count] == R.drawable.la6)
            {
                LAPoints += 5;
                APoints += 2;
                ESAPoints += 2;
            }
            else if(recipeImages[count] == R.drawable.e1 || recipeImages[count] == R.drawable.e2 || recipeImages[count] == R.drawable.e3 || recipeImages[count] == R.drawable.e4 || recipeImages[count] == R.drawable.e5 || recipeImages[count] == R.drawable.e6)
            {
                EPoints += 5;
                SAMPoints += 2;
                APoints += 2;
            }
            else if(recipeImages[count] == R.drawable.sam1 || recipeImages[count] == R.drawable.sam2 || recipeImages[count] == R.drawable.sam3 || recipeImages[count] == R.drawable.sam4 || recipeImages[count] == R.drawable.sam5 || recipeImages[count] == R.drawable.sam6)
            {
                SAMPoints += 5;
                ESAPoints += 2;
                EPoints += 2;
            }
            else if(recipeImages[count] == R.drawable.esa1 || recipeImages[count] == R.drawable.esa2 || recipeImages[count] == R.drawable.esa3 || recipeImages[count] == R.drawable.esa4 || recipeImages[count] == R.drawable.esa5 || recipeImages[count] == R.drawable.esa6)
            {
                ESAPoints += 5;
                SAMPoints += 2;
                LAPoints += 2;
            }

            System.out.println(ESAPoints + "   " + SAMPoints + "   " + EPoints + "    " + LAPoints + "   " + APoints + "   ");
            System.out.println(count);
            count++;
            yesCount++;
        }
        else{
            mysteryRecipeImage.setImageResource(R.drawable.mystery_box);
            Toast.makeText(getApplicationContext(), "You have completed the survey. Click the discover mystery recipe button!", Toast.LENGTH_SHORT).show();
            mysteryRecipeButton.setVisibility(View.VISIBLE);
            findFavouriteCuisine();
        }
    }

    public void findFavouriteCuisine()
    {
        int[] allNumbers = {ESAPoints, SAMPoints, EPoints, LAPoints, APoints};
        Arrays.sort(allNumbers);
        System.out.println("Max number" + allNumbers[4]);
        if(ESAPoints == allNumbers[4])
        {
            favouriteCuisine = "ESA";
        }
        else if(SAMPoints == allNumbers[4])
        {
            favouriteCuisine = "SAM";
        }
        else if(EPoints == allNumbers[4])
        {
            favouriteCuisine = "E";
        }
        else if(LAPoints == allNumbers[4])
        {
            favouriteCuisine = "LA";
        }
        else if(APoints == allNumbers[4])
        {
            favouriteCuisine = "A";
        }
        System.out.println(favouriteCuisine);
    }

    public void findMysteryRecipe()
    {
        if(favouriteCuisine == "ESA" && noCount < 30)
        {
            if(currentTimePeriod == "breakfast")
            {
                mysteryRecipeImage.setImageResource(ESABreakfastImages[randomMysterySlot]);
                mysteryRecipeName = ESABreakfastNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "lunch")
            {
                mysteryRecipeImage.setImageResource(ESALunchImages[randomMysterySlot]);
                mysteryRecipeName = ESALunchNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "dinner")
            {
                mysteryRecipeImage.setImageResource(ESADinnerImages[randomMysterySlot]);
                mysteryRecipeName = ESADinnerNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "snack")
            {
                mysteryRecipeImage.setImageResource(ESASnackImages[randomMysterySlot]);
                mysteryRecipeName = ESASnackNames[randomMysterySlot];
            }
        }
        else if(favouriteCuisine == "SAM" && noCount < 30)
        {
            if(currentTimePeriod == "breakfast")
            {
                mysteryRecipeImage.setImageResource(SAMBreakfastImages[randomMysterySlot]);
                mysteryRecipeName = SAMBreakfastNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "lunch")
            {
                mysteryRecipeImage.setImageResource(SAMLunchImages[randomMysterySlot]);
                mysteryRecipeName = SAMLunchNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "dinner")
            {
                mysteryRecipeImage.setImageResource(SAMDinnerImages[randomMysterySlot]);
                mysteryRecipeName = SAMDinnerNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "snack")
            {
                mysteryRecipeImage.setImageResource(SAMSnackImages[randomMysterySlot]);
                mysteryRecipeName = SAMSnackNames[randomMysterySlot];
            }
        }
        else if(favouriteCuisine == "E" && noCount < 30)
        {
            if(currentTimePeriod == "breakfast")
            {
                mysteryRecipeImage.setImageResource(EBreakfastImages[randomMysterySlot]);
                mysteryRecipeName = EBreakfastNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "lunch")
            {
                mysteryRecipeImage.setImageResource(ELunchImages[randomMysterySlot]);
                mysteryRecipeName = ELunchNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "dinner")
            {
                mysteryRecipeImage.setImageResource(EDinnerImages[randomMysterySlot]);
                mysteryRecipeName = EDinnerNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "snack")
            {
                mysteryRecipeImage.setImageResource(ESnackImages[randomMysterySlot]);
                mysteryRecipeName = ESnackNames[randomMysterySlot];
            }
        }
        else if(favouriteCuisine == "LA" && noCount < 30)
        {
            if(currentTimePeriod == "breakfast")
            {
                mysteryRecipeImage.setImageResource(LABreakfastImages[randomMysterySlot]);
                mysteryRecipeName = LABreakfastNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "lunch")
            {
                mysteryRecipeImage.setImageResource(LALunchImages[randomMysterySlot]);
                mysteryRecipeName = LALunchNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "dinner")
            {
                mysteryRecipeImage.setImageResource(LADinnerImages[randomMysterySlot]);
                mysteryRecipeName = LADinnerNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "snack")
            {
                mysteryRecipeImage.setImageResource(LASnackImages[randomMysterySlot]);
                mysteryRecipeName = LASnackNames[randomMysterySlot];
            }
        }
        else if(favouriteCuisine == "A" && noCount < 30)
        {
            if(currentTimePeriod == "breakfast")
            {
                mysteryRecipeImage.setImageResource(ABreakfastImages[randomMysterySlot]);
                mysteryRecipeName = ABreakfastNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "lunch")
            {
                mysteryRecipeImage.setImageResource(ALunchImages[randomMysterySlot]);
                mysteryRecipeName = ALunchNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "dinner")
            {
                mysteryRecipeImage.setImageResource(ADinnerImages[randomMysterySlot]);
                mysteryRecipeName = ADinnerNames[randomMysterySlot];
            }
            else if(currentTimePeriod == "snack")
            {
                mysteryRecipeImage.setImageResource(ASnackImages[randomMysterySlot]);
                mysteryRecipeName = ASnackNames[randomMysterySlot];
            }
        }
    }

    public void discoverMysteryRecipe(View view)
    {
        yesImageView.setVisibility(View.INVISIBLE);
        noImageView.setVisibility(View.INVISIBLE);
        findMysteryRecipe();
        chosenRecipe.setText(mysteryRecipeName);

        String favouriteCuisineFull = "";

        if(yesCount >= 30)
        {
            System.out.println("yes count" + yesCount);
            chosenRecipe.setVisibility(View.VISIBLE);
            recommendedRecipe.setVisibility(View.VISIBLE);
            chooseFavouriteRecipe.setText("Randomly recommended recipe");
        } else if (noCount >= 30)
        {
            System.out.println("no count" + noCount);
            recommendedRecipe.setVisibility(View.INVISIBLE);
            chosenRecipe.setVisibility(View.INVISIBLE);
            chooseFavouriteRecipe.setText("No recommended recipe");
        }
        else if (noCount < 30 && yesCount < 30){
            chosenRecipe.setVisibility(View.VISIBLE);
            if(favouriteCuisine.equals("ESA")){
                favouriteCuisineFull = "East and Southeast Asian";
            }
            else if(favouriteCuisine.equals("SAM")){
                favouriteCuisineFull = "South Asian and Mediterranean";
            }
            else if(favouriteCuisine.equals("E")){
                favouriteCuisineFull = "European";
            }
            else if(favouriteCuisine.equals("LA")){
                favouriteCuisineFull = "Latin American";
            }
            else if(favouriteCuisine.equals("A")){
                favouriteCuisineFull = "American";
            }
            chooseFavouriteRecipe.setText("Time for " + currentTimePeriod + "\n" + "Favourite Cuisine: " + favouriteCuisineFull);
            recommendedRecipe.setVisibility(View.VISIBLE);
        }
    }

    public void shuffleRecipeImages()
    {
        Random generator = new Random();
        for(int i = 0; i < recipeImages.length; i++)
        {
            int randomIndex = generator.nextInt(recipeImages.length);
            int temp = recipeImages[i];
            recipeImages[i] = recipeImages[randomIndex];
            recipeImages[randomIndex] = temp;
            System.out.println("All numbers" + i);
        }
    }

    public void backButton(View view)
    {
        Intent backButton = new Intent(this, RecipesActivity.class);
        backButton.putExtra("user", user);
        startActivity(backButton);
    }
}