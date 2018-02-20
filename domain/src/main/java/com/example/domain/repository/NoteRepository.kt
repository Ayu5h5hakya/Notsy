package com.example.domain.repository

import com.example.domain.model.Note
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by ayush on 2/12/18.
 */
interface NoteRepository {

    fun createNewNote(noteText : String) : Single<Note>
    fun getNotes() : Single<MutableList<Note>>
    fun deleteNote(noteId: Int) : Single<Unit>

}