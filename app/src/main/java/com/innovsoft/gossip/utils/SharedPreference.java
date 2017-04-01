package com.innovsoft.gossip.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.innovsoft.gossip.bean.User;

/**
 * Created by SriMaddy on 4/1/2017.
 */

public class SharedPreference {

    private static final String PREFERENCE_NAME = "gossipPreference";
    private static final String KEY_USER = "user";

    public void saveUser(Context context, User user) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String userString = gson.toJson(user, User.class);
        editor.putString(KEY_USER, userString);
        editor.apply();
    }

    public User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String userString = preferences.getString(KEY_USER, null);
        Gson gson = new Gson();
        return gson.fromJson(userString, User.class);
    }
}
