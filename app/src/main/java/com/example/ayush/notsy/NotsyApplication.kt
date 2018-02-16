package com.example.ayush.notsy

import android.app.Application
import android.content.Context
import com.example.ayush.notsy.dagger.component.AppComponent
import com.example.ayush.notsy.dagger.component.DaggerAppComponent
import com.example.ayush.notsy.dagger.module.AppModule

/**
 * Created by ayush on 2/14/18.
 */
class NotsyApplication : Application() {

    private lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

    fun getAppComponent() = appComponent

    companion object {
        fun get(context : Context) = context.applicationContext as NotsyApplication
    }


}