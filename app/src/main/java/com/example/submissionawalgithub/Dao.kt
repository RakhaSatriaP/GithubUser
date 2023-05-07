package com.example.submissionawalgithub

import androidx.room.Dao
import androidx.room.*
import androidx.lifecycle.LiveData

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Response.Item)

    @Query("SELECT * FROM User")
    fun loadAll(): LiveData<MutableList<Response.Item>>

    @Delete
    fun delete(User: Response.Item)

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): Response.Item
}