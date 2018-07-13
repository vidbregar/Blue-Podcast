package com.example.vidbregar.bluepodcast.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.ui.home.PodcastClickListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenrePodcastsAdapter extends RecyclerView.Adapter<GenrePodcastsAdapter.ViewHolder> {

    private List<Channel> podcasts;
    private PodcastClickListener podcastClickListener;

    public GenrePodcastsAdapter(PodcastClickListener podcastClickListener) {
        this.podcastClickListener = podcastClickListener;
    }

    @NonNull
    @Override
    public GenrePodcastsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.podcast_row_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenrePodcastsAdapter.ViewHolder viewHolder, int position) {
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.podcast_row_thumbnail)
        RoundedImageView podcastThumbnailImageView;
        @BindView(R.id.podcast_row_title_tv)
        TextView podcastTitleTextView;
        @BindView(R.id.podcast_row_publisher_tv)
        TextView podcastPublisherTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            podcastClickListener.onPodcastClickListener(podcasts.get(getAdapterPosition()));
        }
    }

    public void swapPodcasts(List<Channel> podcasts) {
        this.podcasts = podcasts;
        notifyDataSetChanged();
    }
}