package com.example.ap2_ex4;

import java.util.Locale;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocaleHelper {
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String SELECTED_LANGUAGE_KEY = "selected_language";

    public static void setLocale(Context context, String languageCode) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        saveSelectedLanguage(context, languageCode);
    }

    public static String getSelectedLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE_KEY, DEFAULT_LANGUAGE);
    }

    private static void saveSelectedLanguage(Context context, String languageCode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(SELECTED_LANGUAGE_KEY, languageCode).apply();
    }
}
