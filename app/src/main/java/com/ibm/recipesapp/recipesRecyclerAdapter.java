package com.ibm.recipesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ibm.recipesapp.Recipe.Recipe;

import java.util.ArrayList;

// RecipesRecyclerAdapter

public class recipesRecyclerAdapter extends RecyclerView.Adapter<recipesRecyclerAdapter.ViewHolder> {
    private ArrayList<Recipe> recipeList;
    private recipesRecyclerAdapter.RecyclerViewClickListener listener;

    public recipesRecyclerAdapter(ArrayList<Recipe> recipeList, RecyclerViewClickListener listener){
        this.recipeList = recipeList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView recipeName;
        private ImageView recipePhoto;

        public ViewHolder(final View view){
            super(view);
            recipeName = view.findViewById(R.id.recipeName_TextView_RecipesItems);
            recipePhoto = view.findViewById(R.id.recipePhoto_ImageView_RecipeItems);

            recipeName.setText("Hello world");

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_items, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageString = recipeList.get(position).getRecipeImage();
        String nameString = recipeList.get(position).getRecipeName();

        holder.recipeName.setText(nameString);
        Glide.with(holder.recipePhoto.getContext()).load(imageString).centerCrop().into(holder.recipePhoto);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }
}
