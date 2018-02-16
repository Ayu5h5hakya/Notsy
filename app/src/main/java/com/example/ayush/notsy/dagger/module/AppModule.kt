package com.example.ayush.notsy.dagger.module

import android.app.Application
import android.arch.persistence.room.Room
import com.example.data.NotsyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ayush on 2/14/18.
 */
@Singleton
@Module
class AppModule(app: Application) {

    private val context = app

    @Provides
    @Singleton
    fun provideApplicationContext() = context

    @Provides
    @Singleton
    fun provideNotsyDatabase() : NotsyDatabase = Room.databaseBuilder(context, NotsyDatabase::class.java, "Notsy.db").build()

}