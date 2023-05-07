package com.example.submissionawalgithub

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit


private val Context.prefDataStore by preferencesDataStore("settings")

class SettingPreferences constructor(context: Context) {

    private val Key = booleanPreferencesKey("theme_setting")
    private val settingsDataStore = context.prefDataStore

    fun getThemeSetting():Flow<Boolean> =
    settingsDataStore.data.map { preferences ->
        preferences[Key] ?: false
    }

    suspend fun saveTheme(isDarkMode: Boolean){
        settingsDataStore.edit {preferences ->
            preferences[Key] = isDarkMode
        }
    }

}