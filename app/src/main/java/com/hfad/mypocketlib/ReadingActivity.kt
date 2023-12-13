package com.hfad.mypocketlib

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.hfad.mypocketlib.databinding.ActivityReadingBinding

class ReadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadingBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("bookUrl").toString()


        binding.ibBack.setOnClickListener {
            finish()
        }

       binding.apply {
           wvBook.settings.javaScriptEnabled = true
           wvBook.webViewClient = WebViewClient()
           wvBook.loadUrl(url)
       }
    }
}