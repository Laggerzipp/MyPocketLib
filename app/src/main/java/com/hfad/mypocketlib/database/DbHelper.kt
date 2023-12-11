package com.hfad.mypocketlib.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hfad.mypocketlib.R

@Database(entities = [User::class,Book::class], version = 3)
abstract class DbHelper:RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {
        private var INSTANCE: DbHelper? = null

        fun getDb(context: Context): DbHelper {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DbHelper::class.java,
                        "MyPocketLibDb"
                    ).fallbackToDestructiveMigration().build()
                    Log.d("DbHelper", "Database created successfully")
                } else {
                    Log.d("DbHelper", "Using existing database instance")
                }
                return INSTANCE!!
        }

         fun createBookTable(): List<Book> {
            return listOf(
                Book(
                    null, R.drawable.book_drugoj_mir_popadanec ,"TestTitle1" ,"TestAuthor1",
                    "Test book description", "★★★☆☆", "Ru", "testUrl"
                ),
                Book(
                    null, R.drawable.book_drugoj_mir_popadanec,"TestTitle2", "TestAuthor2",
                    "Test book description2", "★★★★☆", "Eng", "testUrl2"
                ))
        }
    }

}