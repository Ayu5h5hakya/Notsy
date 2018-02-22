package com.example.domain.usecase

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import io.reactivex.Single

/**
 * Created by ayush on 2/12/18.
 */
class GetNoteDetailCase(repository: NoteRepository) : UseCase<Long, Note> {

    private val noteRepository = repository

    override fun execute(parameter: Long) = noteRepository.getNoteById(parameter)
}