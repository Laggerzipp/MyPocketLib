package com.hfad.mypocketlib.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.mypocketlib.MainActivity
import com.hfad.mypocketlib.R
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.database.User
import com.hfad.mypocketlib.databinding.FragmentSignBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SignFragment : Fragment() {
    private lateinit var binding:FragmentSignBinding
    private lateinit var db: DbHelper
    private var fragmentCallback: FragmentCallback? = null
    private var condition: Boolean = false

    fun setFragmentCallback(callback: FragmentCallback) {
        fragmentCallback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Getting the instance of the database from the activity
        val activity = requireActivity() as MainActivity
        db = activity.getDatabaseInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.ibBack.setOnClickListener{
            condition = false
            binding.apply {
                tvInfo.text = resources.getText(R.string.sign_up)
                edEmail.visibility = View.VISIBLE
                btnSignUp.visibility = View.VISIBLE
                tvPasForgot.visibility = View.GONE
                ibBack.visibility = View.GONE
            }
        }

        binding.btnSignUp.setOnClickListener{
            val existingUser: User?
            val user = User(null,
            binding.edLogin.text.toString(),
            binding.edEmail.text.toString(),
            binding.edPas.text.toString())

            val job = CoroutineScope(Dispatchers.IO).async {
                db.getDao().getUserByLogin(binding.edLogin.text.toString())
            }
            runBlocking {
                existingUser = job.await()
            }
            if (existingUser == null) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.getDao().insertUser(user)
                }
                fragmentCallback?.onFragmentAction("startUserLibraryFragment",true)
            }
            else {
               binding.edLogin.error = resources.getText(R.string.login_already_exist)
            }
        }

        binding.btnSignIn.setOnClickListener{
            when(condition){
                false -> {
                    binding.apply {
                        tvInfo.text = resources.getText(R.string.sign_in)
                        ibBack.visibility = View.VISIBLE
                        edEmail.visibility = View.GONE
                        btnSignUp.visibility = View.GONE
                        tvPasForgot.visibility = View.VISIBLE
                    }
                    condition = true
                }
                true -> {
                    var user:User?
                    val job = CoroutineScope(Dispatchers.IO).async{
                        db.getDao().getUserByLogin(inputLogin = binding.edLogin.text.toString())
                    }
                    runBlocking {
                        user = job.await()
                    }
                    if(user != null){
                        if(binding.edPas.text.toString() != user?.password){
                            binding.edPas.error = resources.getText(R.string.wrg_password)
                        }
                        else{
                            fragmentCallback?.onFragmentAction("startUserLibraryFragment",true)
                        }
                    }
                    else{
                        binding.edLogin.error = resources.getText(R.string.wrg_login)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignFragment()
    }
}