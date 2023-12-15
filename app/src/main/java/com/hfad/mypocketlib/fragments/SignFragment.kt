package com.hfad.mypocketlib.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db = DbHelper.getDb(requireContext())

        binding.tvSignUp.setOnClickListener{
            fragmentCallback?.onFragmentAction("clearLayoutFragment",false, null)
            binding.apply {
                tvSignUp.background = ContextCompat.getDrawable(requireContext(),R.drawable.shape_sign_selected)
                tvSignUp.setTextColor(ContextCompat.getColor(requireContext(),R.color.btmNavBackground))
                tvSignIn.background = ContextCompat.getDrawable(requireContext(),R.drawable.shape_sign)
                tvSignIn.setTextColor(ContextCompat.getColor(requireContext(),R.color.layoutBackground))

                tvInfo.text = resources.getText(R.string.sign_up)
                tvInfo.visibility = View.VISIBLE
                edLogin.visibility = View.VISIBLE
                edEmail.visibility = View.VISIBLE
                edPas.visibility = View.VISIBLE
                tvPasForgot.visibility = View.GONE
                clButtons.visibility = View.VISIBLE
                btnSignUp.visibility = View.VISIBLE
                btnSignIn.visibility = View.GONE
            }
        }

        binding.tvSignIn.setOnClickListener{
            fragmentCallback?.onFragmentAction("clearLayoutFragment",false, null)
            binding.apply {
                tvSignIn.background = ContextCompat.getDrawable(requireContext(),R.drawable.shape_sign_selected)
                tvSignIn.setTextColor(ContextCompat.getColor(requireContext(),R.color.btmNavBackground))
                tvSignUp.background = ContextCompat.getDrawable(requireContext(),R.drawable.shape_sign)
                tvSignUp.setTextColor(ContextCompat.getColor(requireContext(),R.color.layoutBackground))

                tvInfo.text = resources.getText(R.string.sign_in)
                tvInfo.visibility = View.VISIBLE
                edLogin.visibility = View.VISIBLE
                edEmail.visibility = View.GONE
                edPas.visibility = View.VISIBLE
                tvPasForgot.visibility = View.VISIBLE
                clButtons.visibility = View.VISIBLE
                btnSignUp.visibility = View.GONE
                btnSignIn.visibility = View.VISIBLE
            }
        }

        binding.btnSignUp.setOnClickListener{
            val existingUser: User?

            val login = binding.edLogin.text.toString()
            val email = binding.edEmail.text.toString()
            val pas = binding.edPas.text.toString()

            if(login.length <= 1){
                binding.edLogin.error = resources.getText(R.string.login_is_too_short)
            }
            else if(email.length < 10){
                binding.edEmail.error = resources.getText(R.string.email_is_too_short)
            }
            else if(pas.length < 4){
                binding.edPas.error = resources.getText(R.string.password_is_too_short)
            }
            else{
                val user = User(null, login, email, pas, "", "" , "")
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
                    fragmentCallback?.onFragmentAction("clearToolFragment",true, null)
                    fragmentCallback?.onFragmentAction("startUserLibraryFragment",true, user.login)
                }
                else {
                    binding.edLogin.error = resources.getText(R.string.login_already_exist)
                }
            }
        }

        binding.btnSignIn.setOnClickListener{
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
                    fragmentCallback?.onFragmentAction("clearToolFragment",true, null)
                    fragmentCallback?.onFragmentAction("startUserLibraryFragment",true, user!!.login)
                }
            }
            else{
                binding.edLogin.error = resources.getText(R.string.wrg_login)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignFragment()
    }
}