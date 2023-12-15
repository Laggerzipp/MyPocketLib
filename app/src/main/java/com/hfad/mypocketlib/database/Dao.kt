package com.hfad.mypocketlib.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insertUser(user: User)
    @Query("SELECT * FROM users WHERE login = :inputLogin")
    suspend fun getUserByLogin(inputLogin: String): User?
    @Query("UPDATE users SET readingBooks = :readingBooks WHERE login = :userLogin")
    suspend fun updateUserReadingBooks(userLogin: String, readingBooks: String)
    @Query("UPDATE users SET readBooks = :readBooks WHERE login = :userLogin")
    suspend fun updateUserReadBooks(userLogin: String, readBooks: String)
    @Query("UPDATE users SET toReadBooks = :toReadBooks WHERE login = :userLogin")
    suspend fun updateUserToReadBooks(userLogin: String, toReadBooks: String)

    @Query("DELETE FROM users")
    suspend fun clearUsersTable()
    @Query("DELETE FROM users WHERE login = 'admin16'")
    suspend fun deleteAdmin16()

    @Insert
    suspend fun insertBook(book: Book)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)
    @Query("SELECT * FROM books WHERE title = :inputTitle")
    suspend fun getBookByTitle(inputTitle: String): Book?
    @Query("SELECT * FROM books WHERE title IN (:readingBooks)")
    suspend fun getBooksByTitles(readingBooks: List<String>): List<Book>
    @Query("DELETE FROM books")
    suspend fun clearBooksTable()
}