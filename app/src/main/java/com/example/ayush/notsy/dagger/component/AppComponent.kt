package com.example.ayush.notsy.dagger.component

import com.example.ayush.notsy.dagger.module.AppModule
import com.example.ayush.notsy.dagger.module.NoteModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by ayush on 2/14/18.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun plus(noteModule: NoteModule) : NoteComponent

}