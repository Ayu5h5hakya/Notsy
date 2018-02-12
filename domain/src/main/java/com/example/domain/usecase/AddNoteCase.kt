package com.example.domain.usecase

import com.example.domain.repository.NoteRepository

/**
 * Created by ayush on 2/12/18.
 */
class AddNoteCase(repository: NoteRepository) : UseCase<Unit, Int> {

    private val noteRepository = repository

    override fun execute(parameter: Unit, callback: UseCase.CallBack<Int>) {
        callback.onSuccess(noteRepository.createNewNote())
    }

}