package com.example.domain.usecase

import com.example.domain.repository.NoteRepository
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by ayush on 2/12/18.
 */
class AddNoteCase(repository: NoteRepository) : UseCase<String, Unit> {

    private val noteRepository = repository
    private val notesSubject : BehaviorSubject<String> = BehaviorSubject.create()

    override fun execute(parameter: String) = noteRepository.createNewNote(parameter)

    fun postToStream(noteText : String){
        notesSubject.onNext(noteText)
    }

    fun getnotesSubject() = notesSubject

}