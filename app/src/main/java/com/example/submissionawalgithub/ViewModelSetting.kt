package com.example.submissionawalgithub

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModelSetting(private val pref: SettingPreferences): ViewModel() {

    class Factory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ViewModelSetting(pref) as T
    }


    fun getTheme() = pref.getThemeSetting().asLiveData()

    fun saveTheme(isDark:Boolean){
        viewModelScope.launch {
            pref.saveTheme(isDark)
        }
    }
}