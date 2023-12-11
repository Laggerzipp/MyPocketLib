package com.hfad.mypocketlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.hfad.mypocketlib.database.DbHelper
import com.hfad.mypocketlib.database.User
import com.hfad.mypocketlib.databinding.ActivityMainBinding
import com.hfad.mypocketlib.fragments.FragmentCallback
import com.hfad.mypocketlib.fragments.LibraryFragment
import com.hfad.mypocketlib.fragments.SignFragment
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

        openFragment(LibraryFragment.newInstance())
        binding.btmNav.selectedItemId = R.id.library

        binding.btmNav.setOnItemSelectedListener {
            when(isSignIn){
                false -> {
                    fragment = SignFragment.newInstance()
                    (fragment as SignFragment).setFragmentCallback(this)
                    when(it.itemId){
                        R.id.user_library -> openFragment(fragment)
                        R.id.library -> openFragment(LibraryFragment.newInstance())
                        else -> true
                    }
                }
                true -> {
                    fragment = UserLibraryFragment.newInstance()
                    (fragment as UserLibraryFragment).setFragmentCallback(this)
                    when(it.itemId){
                        R.id.user_library -> openFragment(fragment)
                        R.id.library -> openFragment(LibraryFragment.newInstance())
                        else -> true
                    }
                }
            }
        }
    }

    private fun openFragment(fragment: Fragment): Boolean{
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frLayout, fragment)
            .commit()
        return true
    }

    override fun onFragmentAction(action: String, isSign: Boolean) {
        isSignIn = isSign
        if (action == "startUserLibraryFragment") {
            fragment = UserLibraryFragment.newInstance()
            (fragment as UserLibraryFragment).setFragmentCallback(this)
            openFragment(fragment)
        }
        if (action == "startSignFragment") {
            fragment = SignFragment.newInstance()
            (fragment as SignFragment).setFragmentCallback(this)
            openFragment(fragment)
        }
    }

    fun getDatabaseInstance(): DbHelper {
        return DbHelper.getDb(this)
    }
}