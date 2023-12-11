package com.hfad.mypocketlib.fragments

interface FragmentCallback {
    fun onFragmentAction(action: String,isSignIn: Boolean)
}