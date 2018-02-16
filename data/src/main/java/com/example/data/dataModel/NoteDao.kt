package com.example.data.dataModel

import android.arch.persistence.room.*

/**
 * Created by ayush on 2/14/18.
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes")
    fun getAllData() : List<NoteModel>

    @Insert
    fun insert(vararg dashData: NoteModel)

    @Update
    fun update(vararg dashData: NoteModel)

    @Delete
    fun delete(vararg dashData: NoteModel)

    @Query("DELETE FROM Notes")
    fun nuke() : Int

}