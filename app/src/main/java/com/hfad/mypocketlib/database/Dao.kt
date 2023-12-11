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
    @Query("SELECT * FROM books WHERE author = :inputAuthor")
    suspend fun getBooksByAuthor(inputAuthor: String): List<Book>
    @Query("DELETE FROM books")
    suspend fun clearBooksTable()
}