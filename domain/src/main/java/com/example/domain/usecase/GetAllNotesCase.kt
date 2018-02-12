package com.example.domain.usecase

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository

/**
 * Created by ayush on 2/12/18.
 */
class GetAllNotesCase(repository : NoteRepository) : UseCase<Unit, List<Note>> {

    private val noteRepository = repository

    override fun execute(parameter: Unit, callback: UseCase.CallBack<List<Note>>) {
        callback.onSuccess(noteRepository.getNotes())
    }
}