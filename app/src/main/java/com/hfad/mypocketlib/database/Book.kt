package com.hfad.mypocketlib.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    @ColumnInfo(name = "imageId")
    val imageId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "grade")
    val grade: String,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "urlPath")
    val urlPath: String
)