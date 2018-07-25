package com.example.vidbregar.bluepodcast.ui.main.search;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.model.data.Episode;
import com.example.vidbregar.bluepodcast.model.data.SearchResult;
import com.example.vidbregar.bluepodcast.model.network.PodcastClient;
import com.example.vidbregar.bluepodcast.ui.main.search.adapter.SearchResultsAdapter;
import com.example.vidbregar.bluepodcast.ui.main.search.listener.SearchResultClickListener;
import com.example.vidbregar.bluepodcast.ui.player.PlayerActivity;
import com.example.vidbregar.bluepodcast.viewmodel.SearchViewModel;
import com.example.vidbregar.bluepodcast.viewmodel.SearchViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements SearchResultClickListener {

    private Context context;
    private SearchViewModel searchViewModel;
    private SearchViewModelFactory searchViewModelFactory;
    private PodcastClient podcastClient;

    @BindView(R.id.dismiss_search_btn)
    ImageButton dismissSearchButton;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.search_container)
    LinearLayout searchViewContainer;
    @BindView(R.id.clear_query_btn)
    ImageButton clearQueryButton;
    @BindView(R.id.search_results_container)
    ConstraintLayout searchResultsContainer;
    @BindView(R.id.search_results_rv)
    RecyclerView searchResultsRecyclerView;
    private SearchResultsAdapter searchResultsAdapter;
    @BindView(R.id.search_loading_container)
    ConstraintLayout searchLoadingContainer;
    @BindView(R.id.no_results_container)
    ConstraintLayout noResultsFoundContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        podcastClient = new PodcastClient();
        searchViewModelFactory = new SearchViewModelFactory(podcastClient);
        searchViewModel = ViewModelProviders.of(this, searchViewModelFactory).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        context = getContext();
        ButterKnife.bind(this, root);

        hideUpNavigation();
        setUpSearchResultsLayout();
        setUpSearch();
        setUpDismissSearchButton();
        setUpClearQueryButton();
        return root;
    }

    private void hideUpNavigation() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        setHasOptionsMenu(false);
    }

    private void setUpSearchResultsLayout() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        searchResultsRecyclerView.setNestedScrollingEnabled(false);
        searchResultsRecyclerView.setLayoutManager(linearLayoutManager);
        searchResultsAdapter = new SearchResultsAdapter(this);
        searchResultsRecyclerView.setAdapter(searchResultsAdapter);
        searchViewModel.getSearchResults().observe(this, search -> {
            if (search.getResults() == null || search.getResults().size() == 0) {
                noResultsFoundContainer.setVisibility(View.VISIBLE);
                searchLoadingContainer.setVisibility(View.GONE);
                searchResultsContainer.setVisibility(View.GONE);
            } else {
                noResultsFoundContainer.setVisibility(View.GONE);
                searchLoadingContainer.setVisibility(View.GONE);
                searchResultsContainer.setVisibility(View.VISIBLE);
                searchResultsAdapter.swapSearchResults(search.getResults());
            }
        });
    }

    @Override
    public void onSearchResultClick(SearchResult searchResult) {
        Intent playerActivityIntent = new Intent(getActivity(), PlayerActivity.class);
        Channel podcast = new Channel(null,
                searchResult.getThumbnailUrl(),
                null,
                null,
                searchResult.getPublisher(),
                searchResult.getEpisodeTitle(),
                null);
        Episode episode = new Episode(searchResult.getEpisodeTitle(),
                null,
                null,
                0,
                null,
                searchResult.getAudioUrl());
        playerActivityIntent.putExtra(PlayerActivity.INTENT_EXTRA_PODCAST, podcast);
        playerActivityIntent.putExtra(PlayerActivity.INTENT_EXTRA_EPISODE, episode);
        startActivity(playerActivityIntent);
    }

    private void setUpSearch() {
        searchView.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                dismissSearchButton.setVisibility(View.VISIBLE);
            } else {
                dismissSearchButton.setVisibility(View.GONE);
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 0) {
                    clearQueryButton.setVisibility(View.VISIBLE);
                } else {
                    clearQueryButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchView.setOnKeyDownListener(() -> searchViewContainer.requestFocus());

        searchView.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(textView.getText().toString());
                hideKeyboard(searchView);
                searchViewContainer.requestFocus();
                return true;
            }
            return false;
        });
    }

    private void performSearch(String query) {
        searchLoadingContainer.setVisibility(View.VISIBLE);
        noResultsFoundContainer.setVisibility(View.GONE);
        searchResultsContainer.setVisibility(View.GONE);
        searchViewModel.search(query);
    }

    private void setUpDismissSearchButton() {
        dismissSearchButton.setOnClickListener(view -> {
            searchViewContainer.requestFocus();
            hideKeyboard(view);
        });
    }

    private void setUpClearQueryButton() {
        clearQueryButton.setOnClickListener(view -> {
            searchView.getText().clear();
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
