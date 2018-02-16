package com.example.domain.usecase

import com.example.domain.model.Note
import io.reactivex.Single

/**
 * Created by ayush on 2/12/18.
 */
class GetNoteDetailCase : UseCase<Int, Note> {

    override fun execute(parameter: Int) = Single.just(Note())
}