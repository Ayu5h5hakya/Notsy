package com.example.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.data.dataModel.NoteDao
import com.example.data.dataModel.NoteModel

/**
 * Created by ayush on 2/14/18.
 */
@Database(entities = arrayOf(NoteModel::class),version = 1)
abstract class NotsyDatabase : RoomDatabase(){
    abstract fun getNoteDao() : NoteDao
}