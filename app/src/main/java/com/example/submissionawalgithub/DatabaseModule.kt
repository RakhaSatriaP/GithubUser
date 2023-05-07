package com.example.submissionawalgithub

import android.content.Context
import androidx.room.*

class DatabaseModule(private val context: Context) {
    private val db = Room.databaseBuilder(context, Database::class.java, "usergithub.db")
        .allowMainThreadQueries().build()
    val dao = db.Dao()
}