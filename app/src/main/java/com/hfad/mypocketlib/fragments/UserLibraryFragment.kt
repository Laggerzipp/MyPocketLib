package com.hfad.mypocketlib.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.mypocketlib.BookActivity
import com.hfad.mypocketlib.LibraryAdapter
import com.hfad.mypocketlib.R
import com.hfad.mypocketlib.database.Book
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.database.User
import com.hfad.mypocketlib.databinding.FragmentUserLibraryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class UserLibraryFragment : Fragment(),LibraryAdapter.Listener {
    private lateinit var binding: FragmentUserLibraryBinding
    private var fragmentCallback: FragmentCallback? = null
    private var userLogin: String? = null
    private lateinit var db: DbHelper
    private var user: User? = null
    private val adapter = LibraryAdapter(this)
    fun setFragmentCallback(callback: FragmentCallback){
        fragmentCallback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userLogin = arguments?.getString("userLogin")
        // Inflate the layout for this fragment
        binding = FragmentUserLibraryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db = DbHelper.getDb(requireContext())

        val job = CoroutineScope(Dispatchers.IO).async {
            userLogin?.let { db.getDao().getUserByLogin(it) }
        }
        runBlocking {
            user = job.await()
        }

        binding.apply {
            tvLogOut.setOnClickListener{
                fragmentCallback?.onFragmentAction("startSignUpRequestFragment",false, null)
            }

            tvLogin.text = user?.login

            var readingBooks: List<Book>
            var readBooks: List<Book>
            var toReadBooks: List<Book>

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

            tvReading.setOnClickListener {
                adapter.clearBooks()

                tvReading.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign_selected)
                tvReading.setTextColor(ContextCompat.getColor(requireContext(), R.color.btmNavBackground))

                tvRead.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign)
                tvRead.setTextColor(ContextCompat.getColor(requireContext(), R.color.layoutBackground))

                tvToRead.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign)
                tvToRead.setTextColor(ContextCompat.getColor(requireContext(), R.color.layoutBackground))

                adapter.addBooks(readingBooks)
                rvLists.layoutManager = LinearLayoutManager(requireContext())
                rvLists.adapter = adapter
            }

            tvRead.setOnClickListener {
                adapter.clearBooks()

                tvRead.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign_selected)
                tvRead.setTextColor(ContextCompat.getColor(requireContext(), R.color.btmNavBackground))

                tvReading.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign)
                tvReading.setTextColor(ContextCompat.getColor(requireContext(), R.color.layoutBackground))

                tvToRead.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign)
                tvToRead.setTextColor(ContextCompat.getColor(requireContext(), R.color.layoutBackground))

                adapter.addBooks(readBooks)
                rvLists.layoutManager = LinearLayoutManager(requireContext())
                rvLists.adapter = adapter
            }

            tvToRead.setOnClickListener {
                adapter.clearBooks()

                tvToRead.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign_selected)
                tvToRead.setTextColor(ContextCompat.getColor(requireContext(), R.color.btmNavBackground))

                tvReading.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign)
                tvReading.setTextColor(ContextCompat.getColor(requireContext(), R.color.layoutBackground))

                tvRead.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_sign)
                tvRead.setTextColor(ContextCompat.getColor(requireContext(), R.color.layoutBackground))

                adapter.addBooks(toReadBooks)
                rvLists.layoutManager = LinearLayoutManager(requireContext())
                rvLists.adapter = adapter
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserLibraryFragment()
    }

    override fun onClick(book: Book) {
        val intent = Intent(activity, BookActivity::class.java)
        intent.putExtra("book",book.title)
        intent.putExtra("userLogin",userLogin)
        startActivity(intent)
    }
}