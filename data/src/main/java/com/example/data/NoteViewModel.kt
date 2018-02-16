package com.example.data

import com.example.domain.usecase.AddNoteCase
import com.example.domain.usecase.GetAllNotesCase

/**
 * Created by ayush on 2/14/18.
 */
class NoteViewModel(addNoteCase: AddNoteCase, getAllCase: GetAllNotesCase) {

    private val addNewNote = addNoteCase
    private val getNotesCase = getAllCase

    fun createNoteStream() = addNewNote.getnotesSubject().flatMap { addNewNote.execute(it).toObservable() }

    fun createAllNotesStream() = getNotesCase.getAllNotesSubject().flatMap { getNotesCase.execute(1).toObservable() }

    fun updateRecycler() {
        getNotesCase.postToStream()
    }

    fun saveNote(noteText: String){
        addNewNote.postToStream(noteText)
    }


}