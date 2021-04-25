package com.mwguy.vezdecod_20_mobile.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.mwguy.vezdecod_20_mobile.model.Category;
import com.mwguy.vezdecod_20_mobile.utils.StringsUtils;

import java.util.HashSet;
import java.util.Set;

public class CategoryRepository {
    private final String PREF_NAME = "___categories";
    private final SharedPreferences preferences;
    private final Gson gson = new Gson();

    public CategoryRepository(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, 0);
    }

    public Set<String> getAllIds() {
        return this.preferences.getStringSet(PREF_NAME, new HashSet<>());
    }

    public Category getCategoryById(String id) {
        String rawString = this.preferences.getString(id, null);
        if (rawString == null) {
            return null;
        }

        return gson.fromJson(rawString, Category.class);
    }

    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(StringsUtils.randomString(10));
        }

        SharedPreferences.Editor sharedPreferencesEditor = preferences.edit();
        sharedPreferencesEditor.putString(category.getId(), gson.toJson(category));

        Set<String> ids = getAllIds();
        ids.add(category.getId());
        sharedPreferencesEditor.putStringSet(PREF_NAME, ids);
        sharedPreferencesEditor.apply();

        return category;
    }
}
