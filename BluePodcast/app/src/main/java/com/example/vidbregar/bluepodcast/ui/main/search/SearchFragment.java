package com.example.vidbregar.bluepodcast.ui.main.search;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.ui.main.search.listener.OnKeyDownListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    private Context context;

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
    @BindView(R.id.search_loading_container)
    ConstraintLayout searchLoadingContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        context = getContext();
        ButterKnife.bind(this, root);

        hideUpNavigation();
        setUpSearch();
        setUpDismissSearchButton();
        setUpClearQueryButton();
        return root;
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
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void performSearch(String query) {

    }

    private void setUpDismissSearchButton() {
        dismissSearchButton.setOnClickListener(view -> {
            searchViewContainer.requestFocus();
            hideKeyboardFrom(context, view);
        });
    }

    private void setUpClearQueryButton() {
        clearQueryButton.setOnClickListener(view -> {
            searchView.getText().clear();
        });
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void hideUpNavigation() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        setHasOptionsMenu(false);
    }
}
