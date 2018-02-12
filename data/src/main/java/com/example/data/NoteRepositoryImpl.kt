package com.example.data

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository

/**
 * Created by ayush on 2/12/18.
 */
class NoteRepositoryImpl : NoteRepository {

    override fun createNewNote(): Int {
        return 1
    }

    override fun getNotes(): List<Note> {
        return emptyList()
    }

    override fun deleteNote(noteId: Int): Boolean {
        return false
    }
}