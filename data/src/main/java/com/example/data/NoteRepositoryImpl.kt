package com.example.data

import com.example.data.dataModel.NoteDao
import com.example.data.dataModel.NoteModel
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by ayush on 2/12/18.
 */
class NoteRepositoryImpl(dao: NoteDao) : NoteRepository {

    private val noteDao = dao

    override fun createNewNote(noteText: String): Single<Note> =
            Single.fromCallable {
                val noteModel = NoteModel()
                noteModel.textContent = noteText
                noteDao.insert(noteModel)
                Note(noteText = noteText)
            }.subscribeOn(Schedulers.newThread())


    override fun getNotes(): Single<MutableList<Note>> {
        return Single.fromCallable {
            val notes = mutableListOf<Note>()
            noteDao.getAllData().mapTo(notes) { Note(id = it.id, noteText = it.textContent) }
            notes
        }.subscribeOn(Schedulers.newThread())
    }

    override fun deleteNote(noteId: Long): Single<Unit> {
        return Single.fromCallable {
            if (noteId == -1L) noteDao.nuke()
            else noteDao.deleteNoteWithId(noteId)
        }.subscribeOn(Schedulers.newThread())
    }

    override fun getNoteById(noteId: Long) =
            noteDao.getNoteFromId(noteId)
                    .map {
                        Note(noteText = it.textContent)
                    }
                    .subscribeOn(Schedulers.newThread())
}