package com.example.vidbregar.bluepodcast.ui.main.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.data.SearchResult;
import com.example.vidbregar.bluepodcast.ui.main.search.listener.SearchResultClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<SearchResult> searchResults;
    private SearchResultClickListener searchResultClickListener;

    public SearchResultsAdapter(SearchResultClickListener searchResultClickListener) {
        this.searchResultClickListener = searchResultClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        SearchResult searchResult = searchResults.get(position);
        viewHolder.favoriteTitleTextView.setText(searchResult.getEpisodeTitle());
        viewHolder.favoritePublisherTextView.setText(searchResult.getPublisher());
    }

    @Override
    public int getItemCount() {
        if (searchResults == null) return 0;
        else return searchResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.podcast_favorite_title)
        TextView favoriteTitleTextView;
        @BindView(R.id.podcast_favorite_publisher)
        TextView favoritePublisherTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            searchResultClickListener.onSearchResultClick(searchResults.get(getAdapterPosition()));
        }
    }

    public void swapSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
        notifyDataSetChanged();
    }
}
