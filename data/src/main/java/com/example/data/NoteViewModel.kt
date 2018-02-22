package com.example.data

import com.example.domain.usecase.AddNoteCase
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.GetAllNotesCase
import com.example.domain.usecase.GetNoteDetailCase

/**
 * Created by ayush on 2/14/18.
 */
class NoteViewModel(addNoteCase: AddNoteCase, getAllCase: GetAllNotesCase, deleteCase : DeleteNoteUseCase, noteDetailCase: GetNoteDetailCase) {

    private val addNewNote = addNoteCase
    private val getNotesCase = getAllCase
    private val deleteNotesUseCase = deleteCase
    private val getNoteDetailCase = noteDetailCase

    fun createNoteStream() = addNewNote.getnotesSubject().flatMap { addNewNote.execute(it).toObservable() }

    fun createNoteDeleteStream() = deleteNotesUseCase.getNoteDeleteSubject().flatMap { deleteNotesUseCase.execute(1).toObservable() }

    fun createAllNotesStream() = getNotesCase.getAllNotesSubject().flatMap { getNotesCase.execute(1).toObservable() }

    fun updateRecycler() {
        getNotesCase.postToStream()
    }

    fun saveNote(noteText: String){
        addNewNote.postToStream(noteText)
    }

    fun deleteNotes() {
        deleteNotesUseCase.postToStream("testing")
    }

    fun getNoteDetails(id : Long) = getNoteDetailCase.execute(id)


}