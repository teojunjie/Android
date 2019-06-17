package com.example.hsh.homesweethome;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsh.homesweethome.Models.CategoryFurniture;
import com.example.hsh.homesweethome.Models.Furniture;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class RecyclerViewAdapterMain
        extends RecyclerView.Adapter<RecyclerViewAdapterMain.MyViewHolder> implements Filterable{

    private Context mContext ;
    private ArrayList<CategoryFurniture> mData ;
    private ArrayList<CategoryFurniture> mDataFiltered;



    public RecyclerViewAdapterMain(Context mContext, ArrayList<CategoryFurniture> mData, ArrayList<CategoryFurniture> mDataFiltered) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mDataFiltered;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.furniture_type_and_cards, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecyclerViewAdapterHorizontalCards horizontalCards = new RecyclerViewAdapterHorizontalCards(mData.get(position).getFurnitures(), mContext);
        holder.horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.horizontalRecyclerView.setAdapter(horizontalCards);

        holder.furniture_category.setText(mData.get(position).getFurnitureCategory());
        holder.furniture_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) mContext;
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                Fragment furnitureCategoryFragment = new FurnitureCategoryFragment();
                Bundle fragArgs = new Bundle();
                CategoryFurniture categoryFurniture = mData.get(position);
                fragArgs.putSerializable("category_furnitures", categoryFurniture);
                fragArgs.putStringArrayList("locations", new ArrayList<>(Arrays.asList("Living Room", "Bedroom", "Kitchen", "Outdoor")));
                furnitureCategoryFragment.setArguments(fragArgs);

                ft.replace(R.id.fragment_container, furnitureCategoryFragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDataFiltered = mData;
                } else {
                    ArrayList<CategoryFurniture> filteredList = new ArrayList<>();
                    for (CategoryFurniture category : mData) {
                        for (Furniture row : category.getFurnitures()) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getFurnitureBrand().toLowerCase().contains(charString.toLowerCase()) || row.getFurnitureName().contains(charSequence) || row.getFurnitureType().contains(charSequence)) {
                                filteredList.add(category);
                            }

                        }
                    }

                    mDataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataFiltered = (ArrayList<CategoryFurniture>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView horizontalRecyclerView;
        TextView furniture_category;
        CardView furniture_category_card;


        public MyViewHolder(View itemView) {
            super(itemView);
            horizontalRecyclerView = itemView.findViewById(R.id.furnitureCards);
            furniture_category = itemView.findViewById(R.id.furnitureTypeCardText);
            furniture_category_card = itemView.findViewById(R.id.furnitureTypeCard);

        }
    }

}
