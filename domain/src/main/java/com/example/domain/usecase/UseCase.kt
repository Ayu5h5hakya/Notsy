package com.example.domain.usecase

import io.reactivex.Single

/**
 * Created by ayush on 2/12/18.
 */
interface UseCase<I,O> {

    fun execute(parameter : I) : Single<O>

}