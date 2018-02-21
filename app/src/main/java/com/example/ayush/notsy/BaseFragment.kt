package com.example.ayush.notsy

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by ayush on 2/21/18.
 */
abstract class BaseFragment : Fragment(){

    protected lateinit var activtiy: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentDaggerComponent()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activtiy = context as BaseActivity
    }

    abstract fun setupFragmentDaggerComponent()


}