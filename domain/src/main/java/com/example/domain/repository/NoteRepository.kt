package com.example.domain.repository

import com.example.domain.model.Note
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by ayush on 2/12/18.
 */
interface NoteRepository {

    fun createNewNote(note : Note) : Single<Note>
    fun getNotes() : Single<MutableList<Note>>
    fun deleteNote(noteId: Long) : Single<Long>
    fun getNoteById(noteId: Long) : Single<Note>
    fun updateNote(note : Note) : Single<Note>

}