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
import com.hfad.mypocketlib.MainActivity
import com.hfad.mypocketlib.database.Book
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment(),LibraryAdapter.Listener {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var db: DbHelper
    private val adapter = LibraryAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(inflater)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Getting the instance of the database from the activity
        val activity = requireActivity() as MainActivity
        db = activity.getDatabaseInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.addAllBooks( DbHelper.createBookTable().shuffled())
        Log.d("Books","Book table successfully created")
        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLibrary.adapter = adapter
    }

        companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }

    override fun onClick(book: Book) {
        val intent = Intent(activity, BookActivity::class.java)
        startActivity(intent)
    }
}