package com.example.domain.usecase

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by ayush on 2/12/18.
 */
class AddNoteCase(repository: NoteRepository) : UseCase<Note, Note> {

    private val noteRepository = repository
    private val notesSubject: BehaviorSubject<Note> = BehaviorSubject.create()

    override fun execute(parameter: Note) =
            if (parameter.textContent.isEmpty() || parameter.textContent.isBlank()) Single.error(Throwable("Note cannot be empty"))
            else {
                if (parameter.id == -1L) noteRepository.createNewNote(parameter)
                else noteRepository.updateNote(parameter)
            }

    fun postToStream(note: Note) {
        notesSubject.onNext(note)
    }

    fun getnotesSubject() = notesSubject

}