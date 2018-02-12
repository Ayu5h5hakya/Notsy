package com.example.domain.repository

import com.example.domain.model.Note

/**
 * Created by ayush on 2/12/18.
 */
interface NoteRepository {

    fun createNewNote() : Int
    fun getNotes() : List<Note>
    fun deleteNote(noteId: Int) : Boolean

}