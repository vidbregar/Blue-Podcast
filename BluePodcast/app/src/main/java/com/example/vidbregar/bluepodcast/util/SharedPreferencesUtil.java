package com.example.vidbregar.bluepodcast.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {

    private static final String IS_ON_PODCAST_DETAIL_LAYOUT = "is-on-podcast-detail-layout";
    private static final String IS_APPLICATION_ALIVE = "is-application-alive";
    private static final String IS_MAIN_ACTIVITY_ALIVE = "is-main-activity-alive";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(Context applicationContext) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    public void setIsOnPodcastDetailLayout(boolean isOnPodcastDetailLayout) {
        sharedPreferences.edit()
                .putBoolean(IS_ON_PODCAST_DETAIL_LAYOUT, isOnPodcastDetailLayout)
                .apply();
    }

    public boolean isOnPodcastDetailLayout() {
        return sharedPreferences.getBoolean(IS_ON_PODCAST_DETAIL_LAYOUT, false);
    }

    public void setIsApplicationAlive(boolean isApplicationAlive) {
        sharedPreferences.edit()
                .putBoolean(IS_APPLICATION_ALIVE, isApplicationAlive)
                .apply();
    }

    public boolean isApplicationAlive() {
        return sharedPreferences.getBoolean(IS_APPLICATION_ALIVE, false);
    }

    public void setIsMainActivityAlive(boolean isMainActivityAlive) {
        sharedPreferences.edit()
                .putBoolean(IS_MAIN_ACTIVITY_ALIVE, isMainActivityAlive)
                .apply();
    }

    public boolean isMainActivityAlive() {
        return sharedPreferences.getBoolean(IS_MAIN_ACTIVITY_ALIVE, false);
    }
}
