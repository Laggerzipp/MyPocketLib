package com.hfad.mypocketlib.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name="login")
    var login: String,
    @ColumnInfo(name="email")
    var email: String,
    @ColumnInfo(name="password")
    var password: String,
    @ColumnInfo(name="readingBooks")
    var readingBooks: List<String>?,
    @ColumnInfo(name="readBooks")
    var readBooks: List<String>?,
    @ColumnInfo(name="toReadBooks")
    var toReadBooks: List<String>?
)