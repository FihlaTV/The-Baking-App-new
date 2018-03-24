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

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {
    private String LOG_TAG;
    private ArrayList<RecipeSteps> recipeSteps = null;
    private final StepAdapterOnClickHandler handler;
    private Context activity_context;

    public ArrayList<RecipeSteps> getList() {
        return recipeSteps;
    }

    public StepAdapter(Context context, StepAdapterOnClickHandler handler) {
        this.handler = handler;
        activity_context = context;
    }

    public interface StepAdapterOnClickHandler {
        void onClick(RecipeSteps thisRecipeSteps);
    }

    public void setRecipeSteps(ArrayList<RecipeSteps> steps) {
        recipeSteps = steps;
    }

    @Override
    public StepAdapter.StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int idForListItem = R.layout.steps_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(idForListItem, parent, shouldAttachToParentImmediately);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepAdapterViewHolder holder, int position) {
        if (recipeSteps == null) {
            Log.e(LOG_TAG, "Recipe steps are null!");
            return;
        }

//        int currentStepid = recipeSteps.get(position).getId();
        String currentStepShortDesc = recipeSteps.get(position).getShortDesc();
//        String currentStepDesc = recipeSteps.get(position).getDescription();
//        String currentStepVideo = recipeSteps.get(position).getVideoURL();
//        String currentStepThumb = recipeSteps.get(position).getThumbURL();

        holder.stepShortDesc.setText(currentStepShortDesc);

    }

    @Override
    public int getItemCount() {
        if (recipeSteps == null)
            return 0;

        return recipeSteps.size();
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_short)
        TextView stepShortDesc;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPostion = getAdapterPosition();
            if (recipeSteps != null) {
                RecipeSteps thisRecipeSteps = recipeSteps.get(adapterPostion);
                handler.onClick(thisRecipeSteps);
            }
        }
    }
}
