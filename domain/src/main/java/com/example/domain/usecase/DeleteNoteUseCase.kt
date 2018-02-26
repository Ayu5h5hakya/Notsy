package com.example.domain.usecase

import com.example.domain.repository.NoteRepository
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by ayush on 2/19/18.
 */
class DeleteNoteUseCase(repository: NoteRepository) {

    private val noteRepository = repository
    private val deleteNotesSubject: BehaviorSubject<Long> = BehaviorSubject.create()

    fun execute(parameter: Long) = noteRepository.deleteNote(parameter)

    fun postToStream(noteId : Long){
        deleteNotesSubject.onNext(noteId)
    }

    fun getNoteDeleteSubject() = deleteNotesSubject
}