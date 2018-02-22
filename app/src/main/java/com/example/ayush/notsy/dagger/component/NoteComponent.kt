package com.example.ayush.notsy.dagger.component

import com.example.ayush.notsy.MainActivity
import com.example.ayush.notsy.NoteDetailFragment
import com.example.ayush.notsy.NoteListFragment
import com.example.ayush.notsy.dagger.module.NoteModule
import com.example.ayush.notsy.dagger.scope.NoteScope
import dagger.Subcomponent

/**
 * Created by ayush on 2/14/18.
 */
@NoteScope
@Subcomponent(modules = arrayOf(NoteModule::class))
interface NoteComponent {

    fun inject(mainActivity: MainActivity)

}