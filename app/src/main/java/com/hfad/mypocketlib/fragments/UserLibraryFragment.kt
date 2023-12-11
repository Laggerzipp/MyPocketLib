package com.hfad.mypocketlib.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.mypocketlib.databinding.FragmentUserLibraryBinding

class UserLibraryFragment : Fragment() {
    lateinit var binding: FragmentUserLibraryBinding
    private var fragmentCallback: FragmentCallback? = null

    fun setFragmentCallback(callback: FragmentCallback){
        fragmentCallback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserLibraryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnLogOut.setOnClickListener{
            fragmentCallback?.onFragmentAction("startSignFragment",false)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserLibraryFragment()
    }
}