package com.example.domain.usecase

/**
 * Created by ayush on 2/12/18.
 */
interface UseCase<I,O> {

    interface CallBack<O>{
        fun onSuccess(result : O)
        fun onError(throwable: Throwable)
    }

    fun execute(parameter : I, callback : CallBack<O>)

}