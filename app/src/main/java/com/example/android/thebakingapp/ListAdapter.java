package com.example.android.thebakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Arjun Vidyarthi on 10-Mar-18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListAdapterViewHolder> {
    private String LOG_TAG;
    private ArrayList<RecipeName> recipeNames = null;
    private final ListAdapterOnClickHandler handler;
    private Context activity_context;

    public interface ListAdapterOnClickHandler {
        void onClick(RecipeName thisRecipe);
    }

    public void setRecipeNames(ArrayList<RecipeName> names) {
        recipeNames = names;
    }

    public ListAdapter(Context context, ListAdapterOnClickHandler handler) {
        this.handler = handler;
        activity_context = context;
    }

    @Override
    public ListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int idForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(idForListItem, parent, shouldAttachToParentImmediately);
        return new ListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapterViewHolder holder, int position) {

        if (recipeNames == null) {
            Log.e(LOG_TAG, "Recipe names are null!");
            return;
        }
        String currentRecipeName = recipeNames.get(position).getName();
        String currentRecipeImage = recipeNames.get(position).getImageURL();

        if(currentRecipeImage!=null){
            Picasso.with(activity_context).load(currentRecipeImage).into(holder.recipeImage);
        }

        holder.recipeName.setText(currentRecipeName);
    }

    @Override
    public int getItemCount() {
        if (recipeNames == null) {
            return 1;
        }

        return recipeNames.size();
    }

    public class ListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_name)
        TextView recipeName;

        @BindView(R.id.recipeImage)
        ImageView recipeImage;

        public ListAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (recipeNames != null) {
                RecipeName thisRecipeName = recipeNames.get(adapterPosition);
                handler.onClick(thisRecipeName);
            }
        }
    }
}
