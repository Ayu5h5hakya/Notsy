package com.example.domain.usecase

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by ayush on 2/12/18.
 */
class GetAllNotesCase(repository : NoteRepository) : UseCase<Int, MutableList<Note>> {

    private val noteRepository = repository
    private val allnotesSubject : BehaviorSubject<Int> = BehaviorSubject.create()

    override fun execute(parameter: Int) = noteRepository.getNotes()

    fun getAllNotesSubject() = allnotesSubject

    fun postToStream(){
        allnotesSubject.onNext(1)
    }
}