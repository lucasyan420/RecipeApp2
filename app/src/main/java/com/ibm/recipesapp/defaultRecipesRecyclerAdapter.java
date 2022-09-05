package com.ibm.recipesapp;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibm.recipesapp.Recipe.Recipe;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

//DefaultRecipesRecyclerAdapter
public class defaultRecipesRecyclerAdapter extends RecyclerView.Adapter<defaultRecipesRecyclerAdapter.ViewHolder> {
    private ArrayList<Recipe> recipeList;
    private defaultRecipesRecyclerAdapter.RecyclerViewClickListener listener;

    public defaultRecipesRecyclerAdapter(ArrayList<Recipe> recipeList, RecyclerViewClickListener listener){
        this.recipeList = recipeList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView recipeName;
        private ImageView recipeImage;

        public ViewHolder(final View view){
            super(view);

            recipeName = view.findViewById(R.id.recipeName_TextView_RecipesItems);
            recipeName.setMovementMethod(new ScrollingMovementMethod());
            recipeImage = view.findViewById(R.id.defaultRecipe_ImageView_DefaultRecipesItems);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.default_recipes_items, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String recipeName = recipeList.get(position).getRecipeName();
        String recipeImageString = recipeList.get(position).getRecipeImage();
        holder.recipeName.setText(recipeName);
        Glide.with(holder.recipeImage.getContext()).load(recipeImageString).centerCrop().into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }
}
