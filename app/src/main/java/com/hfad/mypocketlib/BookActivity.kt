package com.hfad.mypocketlib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.mypocketlib.database.Book
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.databinding.ActivityBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class BookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookBinding
    private lateinit var db: DbHelper
    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper.getDb(this)

        val bookTitle = intent.getStringExtra("book").toString()

        val job = CoroutineScope(Dispatchers.IO).async {
            db.getDao().getBookByTitle(bookTitle)
        }
        runBlocking {
            book = job.await()
        }

        binding.apply {
            tvTitle.text = book?.title
            tvAuthor.text = book?.author
            tvGrade.text = book?.grade
            tvLang.text = book?.language
            tvDescription.text = book?.description
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
}