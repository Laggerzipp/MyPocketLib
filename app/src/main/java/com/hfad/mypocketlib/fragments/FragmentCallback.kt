package com.hfad.mypocketlib.fragments

import com.hfad.mypocketlib.database.User

interface FragmentCallback {
    fun onFragmentAction(action: String,isSignIn: Boolean, user: User?)
}