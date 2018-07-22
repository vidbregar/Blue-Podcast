package com.example.vidbregar.bluepodcast.ui.main.favorites.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoriteEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<FavoriteEntity> favorites;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        FavoriteEntity favorite = favorites.get(position);
        viewHolder.favoriteTitleTextView.setText(favorite.getEpisodeTitle());
        viewHolder.favoritePublisherTextView.setText(favorite.getPublisher());
    }

    @Override
    public int getItemCount() {
        if (favorites == null) return 0;
        else return favorites.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.podcast_favorite_title)
        TextView favoriteTitleTextView;
        @BindView(R.id.podcast_favorite_publisher)
        TextView favoritePublisherTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void swapFavorites(List<FavoriteEntity> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }
}
