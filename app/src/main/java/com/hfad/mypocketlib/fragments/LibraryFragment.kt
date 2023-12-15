package com.hfad.mypocketlib.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.mypocketlib.BookActivity
import com.hfad.mypocketlib.LibraryAdapter
import com.hfad.mypocketlib.database.Book
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.databinding.FragmentLibraryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibraryFragment : Fragment(),LibraryAdapter.Listener {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var db: DbHelper
    private val adapter = LibraryAdapter(this)
    private var userLogin: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userLogin = arguments?.getString("userLogin")
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db = DbHelper.getDb(requireContext())

        val books = DbHelper.createBookTable().shuffled()
        Log.d("Books","Book table successfully created")

        CoroutineScope(Dispatchers.IO).launch {
            for(b in books){
                val existingBook = db.getDao().getBookByTitle(b.title)
                if (existingBook == null) {
                    db.getDao().insertBook(b)
                }
                adapter.addBook(b)
            }
        }

        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLibrary.adapter = adapter
    }

        companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }

    override fun onClick(book: Book) {
        val intent = Intent(activity, BookActivity::class.java)
        intent.putExtra("book",book.title)
        intent.putExtra("userLogin",userLogin)
        startActivity(intent)
    }
}