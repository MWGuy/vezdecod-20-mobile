package com.mwguy.vezdecod_20_mobile.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.mwguy.vezdecod_20_mobile.model.Record;
import com.mwguy.vezdecod_20_mobile.utils.StringsUtils;

import java.util.HashSet;
import java.util.Set;

public class RecordRepository {
    private final String PREF_NAME = "___records";
    private final SharedPreferences preferences;
    private final Gson gson = new Gson();

    public RecordRepository(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, 0);
    }

    public Set<String> getAllIds() {
        return this.preferences.getStringSet(PREF_NAME, new HashSet<>());
    }

    public Record getRecordById(String id) {
        String rawString = this.preferences.getString(id, null);
        if (rawString == null) {
            return null;
        }

        return gson.fromJson(rawString, Record.class);
    }

    public Record save(Record record) {
        if (record.getId() == null) {
            record.setId(StringsUtils.randomString(10));
        }

        SharedPreferences.Editor sharedPreferencesEditor = preferences.edit();
        sharedPreferencesEditor.putString(record.getId(), gson.toJson(record));

        Set<String> ids = getAllIds();
        ids.add(record.getId());
        sharedPreferencesEditor.putStringSet(PREF_NAME, ids);
        sharedPreferencesEditor.apply();

        return record;
    }

    public void remove(Record record) {
        SharedPreferences.Editor sharedPreferencesEditor = preferences.edit();
        sharedPreferencesEditor.putString(record.getId(), gson.toJson(record));

        Set<String> ids = getAllIds();
        ids.remove(record.getId());
        sharedPreferencesEditor.putStringSet(PREF_NAME, ids);
        sharedPreferencesEditor.remove(record.getId());
        sharedPreferencesEditor.apply();
    }
}
