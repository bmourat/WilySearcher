package ru.bmourat.wilysearcher.domain.interactor.type

import io.reactivex.Single

interface SingleUseCase<T> {
    fun execute(): Single<T>
}

interface SingleUseCaseWithParameter<in P, R> {
    fun execute(parameter: P): Single<R>
}
