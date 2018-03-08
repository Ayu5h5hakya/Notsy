package com.example.data.dataModel

import android.arch.persistence.room.*
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by ayush on 2/14/18.
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes")
    fun getAllData() : Single<List<NoteModel>>

    @Query("SELECT * FROM Notes WHERE id = :noteid")
    fun getNoteFromId(noteid : Long) : Single<NoteModel>

    @Query("DELETE FROM Notes WHERE id = :noteid")
    fun deleteNoteWithId(noteid: Long)

    @Query("DELETE FROM Notes")
    fun nuke()

    @Insert
    fun insert(vararg dashData: NoteModel)

    @Update
    fun update(vararg dashData: NoteModel)

    @Delete
    fun delete(vararg dashData: NoteModel)

}