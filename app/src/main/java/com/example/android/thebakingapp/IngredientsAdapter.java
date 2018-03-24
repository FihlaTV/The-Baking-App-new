package com.example.android.thebakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Arjun Vidyarthi on 11-Mar-18.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientAdapterViewHolder> {
    private String LOG_TAG;
    private ArrayList<RecipeIngredients> recipeIngredients = null;
    private final IngredientAdapterOnClickHandler handler;
    private Context activity_context;

    public ArrayList<RecipeIngredients> getList() {
        return recipeIngredients;
    }

    public IngredientsAdapter(Context context, IngredientAdapterOnClickHandler handler) {
        this.handler = handler;
        activity_context = context;
    }

    public interface IngredientAdapterOnClickHandler {
        void onClick(RecipeIngredients thisRecipeIngs);
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredients> ings) {
        recipeIngredients = ings;
    }

    @Override
    public IngredientsAdapter.IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int idForListItem = R.layout.ing_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(idForListItem, parent, shouldAttachToParentImmediately);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientAdapterViewHolder holder, int position) {
        if (recipeIngredients == null) {
            Log.e(LOG_TAG, "Recipe names are null!");
            return;
        }

        String currentIngName = recipeIngredients.get(position).getIngredient();
        String currentRecipeMeasure = recipeIngredients.get(position).getMeasure();
        String currentRecipeQuant = recipeIngredients.get(position).getQuantity();

        holder.ingName.setText(currentIngName.toUpperCase());
        holder.ingMeasure.setText(currentRecipeMeasure);
        holder.ingQuant.setText(currentRecipeQuant);

    }

    @Override
    public int getItemCount() {
        if (recipeIngredients == null)
            return 0;

        return recipeIngredients.size();
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ing_name)
        TextView ingName;

        @BindView(R.id.ing_quant)
        TextView ingQuant;

        @BindView(R.id.ing_unit)
        TextView ingMeasure;


        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (recipeIngredients != null) {
                RecipeIngredients thisRecipeIng = recipeIngredients.get(adapterPosition);
                handler.onClick(thisRecipeIng);
            }
        }
    }
}
