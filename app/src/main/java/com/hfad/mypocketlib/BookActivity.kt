package com.hfad.mypocketlib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.hfad.mypocketlib.database.Book
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.database.User
import com.hfad.mypocketlib.databinding.ActivityBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookBinding
    private lateinit var db: DbHelper
    private var book: Book? = null
    private var user: User? = null
    private var isReading:Boolean = false
    private var isRead:Boolean = false
    private var isToRead:Boolean = false
    private var bookTitle:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        var readingBooks: List<Book>
        var readBooks: List<Book>
        var toReadBooks: List<Book>

        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper.getDb(this)

        bookTitle = intent.getStringExtra("book").toString()
        val userLogin = intent.getStringExtra("userLogin").toString()

        val jobBook = CoroutineScope(Dispatchers.IO).async {
            db.getDao().getBookByTitle(bookTitle)

        }
        val jobUser = CoroutineScope(Dispatchers.IO).async {
            db.getDao().getUserByLogin(userLogin)
        }
        runBlocking {
            book = jobBook.await()
            user = jobUser.await()
        }

        val readingBooksString:String = user?.readingBooks ?: ""
        val readingBooksList = readingBooksString.split("|").map { it.trim() }
        val readBooksString:String = user?.readBooks ?: ""
        val readBooksList = readBooksString.split("|").map { it.trim() }
        val toReadBooksString:String = user?.toReadBooks ?: ""
        val toReadBooksList = toReadBooksString.split("|").map { it.trim() }

        val jobReading = CoroutineScope(Dispatchers.IO).async {
            db.getDao().getBooksByTitles(readingBooks = readingBooksList)
        }
        val jobRead = CoroutineScope(Dispatchers.IO).async {
            db.getDao().getBooksByTitles(readingBooks = readBooksList)
        }
        val jobToRead = CoroutineScope(Dispatchers.IO).async {
            db.getDao().getBooksByTitles(readingBooks = toReadBooksList)
        }
        runBlocking {
            readingBooks = jobReading.await()
            readBooks = jobRead.await()
            toReadBooks = jobToRead.await()
        }

        binding.apply {
            book?.imageId?.let { imBook.setImageResource(it) }
            tvTitle.text = book?.title
            tvAuthor.text = book?.author
            tvGrade.text = book?.grade
            tvLang.text = book?.language
            tvDescription.text = book?.description
        }

        isReading = checkIsInUserLists(readingBooks)
        isRead = checkIsInUserLists(readBooks)
        isToRead = checkIsInUserLists(toReadBooks)

        if(isReading){
            binding.tvReading.background =
                ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign_selected)
            binding.tvReading.setTextColor(
                ContextCompat.getColor(
                    this@BookActivity,
                    R.color.btmNavBackground
                )
            )
        }
        else{
            binding.tvReading.background =
                ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign)
            binding.tvReading.setTextColor(
                ContextCompat.getColor(
                    this@BookActivity,
                    R.color.layoutBackground
                )
            )

            binding.apply {
                tvReading.setOnClickListener {
                    tvReading.background = ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign_selected)
                    tvReading.setTextColor(ContextCompat.getColor(this@BookActivity, R.color.btmNavBackground))

                    var tmp = user?.readingBooks
                    tmp = tmp.plus("$bookTitle|")

                    CoroutineScope(Dispatchers.IO).launch {
                        db.getDao().updateUserReadingBooks(readingBooks = tmp!!, userLogin = userLogin )
                    }
                }
            }
        }

        if(isRead){
            binding.tvRead.background = ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign_selected)
            binding.tvRead.setTextColor(ContextCompat.getColor(this@BookActivity, R.color.btmNavBackground))
        }
        else{
            binding.tvRead.background = ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign)
            binding.tvRead.setTextColor(ContextCompat.getColor(this@BookActivity, R.color.layoutBackground))

            binding.apply {
                tvRead.setOnClickListener {
                    tvRead.background = ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign_selected)
                    tvRead.setTextColor(ContextCompat.getColor(this@BookActivity, R.color.btmNavBackground))

                    var tmp = user?.readBooks
                    tmp = tmp.plus("$bookTitle|")

                    CoroutineScope(Dispatchers.IO).launch {
                        db.getDao().updateUserReadBooks(readBooks = tmp!!, userLogin = userLogin )
                    }
                }
            }
        }

        if(isToRead){
            binding.tvToRead.background = ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign_selected)
            binding.tvToRead.setTextColor(ContextCompat.getColor(this@BookActivity, R.color.btmNavBackground))
        }
        else{
            binding.tvToRead.background = ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign)
            binding.tvToRead.setTextColor(ContextCompat.getColor(this@BookActivity, R.color.layoutBackground))

            binding.apply {
                tvToRead.setOnClickListener {
                    tvToRead.background = ContextCompat.getDrawable(this@BookActivity, R.drawable.shape_sign_selected)
                    tvToRead.setTextColor(ContextCompat.getColor(this@BookActivity, R.color.btmNavBackground))

                    var tmp = user?.toReadBooks
                    tmp = tmp.plus("$bookTitle|")

                    CoroutineScope(Dispatchers.IO).launch {
                        db.getDao().updateUserToReadBooks(toReadBooks = tmp!!, userLogin = userLogin )
                    }
                }
            }
        }

        binding.ibBack.setOnClickListener{
            finish()
        }

        binding.btnRead.setOnClickListener{
            val intent = Intent(this,ReadingActivity::class.java)
            intent.putExtra("bookUrl", book?.urlPath)
            startActivity(intent)
        }
    }

    private fun checkIsInUserLists(bookList: List<Book>): Boolean {
        var check = false
        for(b in bookList) {
            if (bookTitle != b.title) {
                check = false
            }
            else{
                check = true
                break
            }
        }
        return check
    }

}