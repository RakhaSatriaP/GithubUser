package com.example.submissionawalgithub

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class Response(
    val incomplete_results: Boolean,
    val items: MutableList<Item>,
    val total_count: Int
) {
    @Parcelize
    @Entity(tableName = "user")
    data class Item(
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "avatar_url")
        val avatar_url: String,
        @ColumnInfo(name = "login")
        val login: String,
    ) : Parcelable
}