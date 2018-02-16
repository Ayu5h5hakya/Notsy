package com.example.ayush.notsy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ayush on 2/14/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivityComponent()
        initUiComponents()
    }

    abstract fun setupActivityComponent()

    abstract fun initUiComponents()

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

}