package com.hfad.mypocketlib.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.mypocketlib.databinding.FragmentSignUpRequestBinding
class SignUpRequestFragment : Fragment() {
    private lateinit var binding: FragmentSignUpRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignUpRequestBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpRequestFragment()
    }
}