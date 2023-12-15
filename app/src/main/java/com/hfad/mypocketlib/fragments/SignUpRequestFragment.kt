package com.hfad.mypocketlib.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.mypocketlib.databinding.FragmentSignUpRequestBinding
class SignUpRequestFragment : Fragment() {
    private lateinit var binding: FragmentSignUpRequestBinding
    private var fragmentCallback: FragmentCallback? = null
    private var condition: Boolean = false

    fun setFragmentCallback(callback: FragmentCallback) {
        fragmentCallback = callback
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpRequestBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpRequestFragment()
    }
}