package com.example.submissionawalgithub

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [Response.Item::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun Dao(): Dao
}