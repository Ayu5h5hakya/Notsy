package com.example.data

import com.example.data.dataModel.NoteDao
import com.example.data.dataModel.NoteModel
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by ayush on 2/12/18.
 */
class NoteRepositoryImpl(dao: NoteDao) : NoteRepository {

    private val noteDao = dao

    override fun createNewNote(note: Note): Single<Note> =
            Single.fromCallable {
                val (dateString, timeString) = Calendar.getInstance().getDateTime()
                val noteModel = NoteModel()
                noteModel.textContent = note.textContent
                noteModel.imageContent = note.imageContent
                noteModel.dateStamp = dateString
                noteModel.timeStamp = timeString
                noteDao.insert(noteModel)
                Note(textContent = note.textContent, imageContent = note.imageContent, dateStamp = dateString, timeStamp = timeString)
            }.subscribeOn(Schedulers.newThread())


    override fun getNotes(): Single<MutableList<Note>> {
        return Single.fromCallable {
            val notes = mutableListOf<Note>()
            noteDao.getAllData().mapTo(notes) { Note(id = it.id, textContent = it.textContent, imageContent = it.imageContent, dateStamp = it.dateStamp, timeStamp = it.timeStamp) }
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
                        Note(textContent = it.textContent, imageContent = it.imageContent, dateStamp = it.dateStamp, timeStamp = it.timeStamp)
                    }
                    .subscribeOn(Schedulers.newThread())

    override fun updateNote(note: Note): Single<Note> {
        return Single.fromCallable {
            val (dateString, timeString) = Calendar.getInstance().getDateTime()
            val noteModel = NoteModel()
            noteModel.id = note.id
            noteModel.textContent = note.textContent
            noteModel.imageContent = note.imageContent
            noteModel.dateStamp = dateString
            noteModel.timeStamp = timeString
            noteDao.update(noteModel)
            note
        }.subscribeOn(Schedulers.newThread())
    }

}