package ru.bmourat.wilysearcher.domain.interactor.type

import io.reactivex.Observable

interface UseCase<T> {
    fun execute(): Observable<T>
}

interface UseCaseWithParameter<in P, R> {
    fun execute(parameter: P): Observable<R>
}

interface UseCaseWithParameters<in P, in P1, R> {
    fun execute(parameter: P, parameter1: P1): Observable<R>
}
