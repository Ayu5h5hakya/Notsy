package com.example.domain.usecase

import io.reactivex.Completable

/**
 * Created by ayush on 2/19/18.
 */
interface CompletableUseCase<I> {

    fun execute(parameter : I) : Completable

}