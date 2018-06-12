package ru.bmourat.wilysearcher.domain.interactor.type

import io.reactivex.Single

interface SingleUseCase<T> {
    fun execute(): Single<T>
}

interface SingleUseCaseWithParameter<in P, R> {
    fun execute(parameter: P): Single<R>
}

interface SingleUseCaseWithParameters<in P, in P1, R> {
    fun execute(parameter: P, parameter1: P1): Single<R>
}
