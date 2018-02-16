package com.example.data

import com.example.data.dataModel.NoteDao
import com.example.data.dataModel.NoteModel
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by ayush on 2/12/18.
 */
class NoteRepositoryImpl(dao: NoteDao) : NoteRepository {

    private val noteDao = dao

    override fun createNewNote(noteText: String): Single<Unit> = Single.fromCallable { noteDao.insert(NoteModel(System.currentTimeMillis(), noteText)) }.subscribeOn(Schedulers.newThread())


    override fun getNotes(): Single<MutableList<Note>> {
        return Single.fromCallable {
            val notes = mutableListOf<Note>()
            noteDao.getAllData().mapTo(notes) { Note(it.id, it.noteText) }
            notes
        }.subscribeOn(Schedulers.newThread())
    }

    override fun deleteNote(noteId: Int): Boolean {
        return false
    }
}