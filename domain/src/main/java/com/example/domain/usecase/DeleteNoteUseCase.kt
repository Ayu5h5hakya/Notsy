package com.example.domain.usecase

import com.example.domain.repository.NoteRepository
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by ayush on 2/19/18.
 */
class DeleteNoteUseCase(repository: NoteRepository) {

    private val noteRepository = repository
    private val deleteNotesSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    fun execute(parameter: Int) = noteRepository.deleteNote(parameter)

    fun postToStream(noteText : String){
        deleteNotesSubject.onNext(1)
    }

    fun getNoteDeleteSubject() = deleteNotesSubject
}