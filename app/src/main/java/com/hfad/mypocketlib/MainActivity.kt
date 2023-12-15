package com.hfad.mypocketlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.database.User
import com.hfad.mypocketlib.databinding.ActivityMainBinding
import com.hfad.mypocketlib.fragments.FragmentCallback
import com.hfad.mypocketlib.fragments.LibraryFragment
import com.hfad.mypocketlib.fragments.SignFragment
import com.hfad.mypocketlib.fragments.SignUpRequestFragment
import com.hfad.mypocketlib.fragments.UserLibraryFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), FragmentCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DbHelper
    private lateinit var fragment: Fragment
    private var isSignIn : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper.getDb(applicationContext)

        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().insertUser(User(null,"admin16","admin","admin"))
            db.getDao().deleteAdmin16()
        }

        binding.btmNav.selectedItemId = R.id.library
        openFragment(LibraryFragment.newInstance(),R.id.frLayout)

        fragment = SignFragment.newInstance()
        (fragment as SignFragment).setFragmentCallback(this)
        openFragment(fragment,R.id.frTool)

        binding.btmNav.setOnItemSelectedListener {
            when(isSignIn){
                false -> {
                    when(it.itemId){
                        R.id.user_library -> openFragment(SignUpRequestFragment.newInstance(),R.id.frLayout)
                        R.id.library ->{
                            fragment = SignFragment.newInstance()
                            (fragment as SignFragment).setFragmentCallback(this)
                            openFragment(fragment,R.id.frTool)
                            binding.frLayout.visibility = View.VISIBLE
                            openFragment(LibraryFragment.newInstance(),R.id.frLayout)
                        }
                        else -> true
                    }
                }
                true -> {
                    fragment = UserLibraryFragment.newInstance()
                    (fragment as UserLibraryFragment).setFragmentCallback(this)
                    when(it.itemId){
                        R.id.user_library -> openFragment(fragment,R.id.frLayout)
                        R.id.library -> {
                            fragment = SignFragment.newInstance()
                            (fragment as SignFragment).setFragmentCallback(this)
                            openFragment(fragment,R.id.frTool)
                            binding.frLayout.visibility = View.VISIBLE
                            openFragment(LibraryFragment.newInstance(),R.id.frLayout)
                        }
                        else -> true
                    }
                }
            }
        }
    }

    private fun openFragment(fragment: Fragment, fragmentId: Int): Boolean{
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentId, fragment)
            .commit()
        return true
    }

    override fun onFragmentAction(action: String, isSignIn: Boolean) {
        this.isSignIn = isSignIn
        if (action == "startUserLibraryFragment") {
            fragment = UserLibraryFragment.newInstance()
            (fragment as UserLibraryFragment).setFragmentCallback(this)
            openFragment(fragment,R.id.frLayout)
        }
        if (action == "clearLayoutFragment") {
            binding.btmNav.selectedItemId = R.id.user_library
            binding.frLayout.visibility = View.GONE
        }
        if (action == "clearToolFragment") {
            binding.frTool.visibility = View.GONE
            binding.frLayout.visibility = View.VISIBLE
        }
        if (action == "startSignFragment") {
            binding.btmNav.selectedItemId = R.id.user_library
            fragment = SignFragment.newInstance()
            (fragment as SignFragment).setFragmentCallback(this)
            openFragment(fragment,R.id.frLayout)
        }
        if (action == "startSignUpRequestFragment") {
            binding.btmNav.selectedItemId = R.id.user_library
            binding.frTool.visibility = View.VISIBLE
            openFragment(SignUpRequestFragment.newInstance(),R.id.frLayout)
        }
    }
}