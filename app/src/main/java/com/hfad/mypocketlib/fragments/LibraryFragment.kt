package com.hfad.mypocketlib.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.mypocketlib.LibraryAdapter
import com.hfad.mypocketlib.MainActivity
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var db: DbHelper
    private var fragmentCallback: FragmentCallback? = null

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
        val adapter = LibraryAdapter()
        adapter.addAllBooks( DbHelper.createBookTable().shuffled())
        Log.d("Books","Book table successfully created")
        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLibrary.adapter = adapter
    }

        companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }
}