package com.example.hsh.homesweethome.furniture;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsh.homesweethome.Models.Furniture;
import com.example.hsh.homesweethome.R;
import com.example.hsh.homesweethome.details.FurnitureDetails;
import com.example.hsh.homesweethome.furniture.presenter.IRecyclerViewAdapterPresenter;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapterFurniture extends RecyclerView.Adapter<RecyclerViewAdapterFurniture.FurnitureViewHolder>{


    private IRecyclerViewAdapterPresenter presenter;

    public RecyclerViewAdapterFurniture(IRecyclerViewAdapterPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public FurnitureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FurnitureViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.furniture_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FurnitureViewHolder holder, int position) {
        presenter.onBindFurnitureViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getFurnitureCount();
    }

    public static class FurnitureViewHolder extends RecyclerView.ViewHolder implements RecyclerViewHolderFurniture{

        TextView furniture_title, furniture_brand, furniture_price;
        ImageView furniture_image, furniture_brand_logo;
        CardView furniture_card;

        public FurnitureViewHolder(View itemView) {
            super(itemView);
            furniture_title = itemView.findViewById(R.id.furnitureTitleCard);
            furniture_brand = itemView.findViewById(R.id.furnitureBrandCard);
            furniture_price = itemView.findViewById(R.id.furniturePriceCard);
            furniture_brand_logo = itemView.findViewById(R.id.furnitureBrandLogoCard);
            furniture_image = itemView.findViewById(R.id.furnitureImgCard);
            furniture_card = itemView.findViewById(R.id.furnitureCard);
        }

        @Override
        public void setFurnitureTitle(String title) {
            furniture_title.setText(title);
        }

        @Override
        public void setFurnitureBrand(String brand) {
            furniture_brand.setText(brand);
        }

        @Override
        public void setFurniturePrice(String price) {
            furniture_price.setText(price);
        }

        @Override
        public void setOnClickListenerFurnitureCard(Context activityContext, Furniture furniture) {
            furniture_card.setOnClickListener(v -> {

                Intent intent = new Intent(activityContext, FurnitureDetails.class);
                intent.putExtra("Furniture", furniture);
                activityContext.startActivity(intent);
            });

        }

        @Override
        public void setFurnitureImage(String furnitureImageUrl) {
            Picasso.get()
                    .load(furnitureImageUrl)
                    .fit()
                    .into(furniture_image);

        }

        @Override
        public void setFurnitureBrandImage(String furnitureBrandImageUrl) {
            Picasso.get()
                    .load(furnitureBrandImageUrl)
                    .fit()
                    .into(furniture_brand_logo);

        }
    }
}
