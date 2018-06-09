package ru.bmourat.wilysearcher.domain.interactor.type

import io.reactivex.Completable

interface CompletableUseCase {
    fun execute(): Completable
}

interface CompletableUseCaseWithParameter<in P> {
    fun execute(parameter: P): Completable
}

