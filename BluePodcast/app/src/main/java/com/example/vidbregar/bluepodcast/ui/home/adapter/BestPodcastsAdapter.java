package com.example.vidbregar.bluepodcast.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BestPodcastsAdapter extends RecyclerView.Adapter<BestPodcastsAdapter.ViewHolder> {

    private List<Channel> podcasts;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.podcast_icon_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Channel podcast = podcasts.get(position);
        viewHolder.podcastTitleTextView.setText(podcast.getTitle());
        viewHolder.podcastPublisherTextView.setText(podcast.getPublisher());
        loadThumbnail(podcast, viewHolder);
    }

    private void loadThumbnail(Channel podcast, ViewHolder viewHolder) {
        Picasso.get().load(podcast.getThumbnailUrl())
                .into(viewHolder.podcastThumbnailImageView);
    }


    @Override
    public int getItemCount() {
        if (podcasts == null) return 0;
        else return podcasts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.podcast_icon_thumbnail)
        RoundedImageView podcastThumbnailImageView;
        @BindView(R.id.podcast_icon_title_tv)
        TextView podcastTitleTextView;
        @BindView(R.id.podcast_icon_publisher_tv)
        TextView podcastPublisherTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void swapPodcasts(List<Channel> podcasts) {
        this.podcasts = podcasts;
        notifyDataSetChanged();
    }
}
