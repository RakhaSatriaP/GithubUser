package com.example.submissionawalgithub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class FavViewModel(private val databaseModule: DatabaseModule) : ViewModel() {

    fun getUserFav() = databaseModule.dao.loadAll()

    class Factory(private val db: DatabaseModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavViewModel(db) as T
    }


}