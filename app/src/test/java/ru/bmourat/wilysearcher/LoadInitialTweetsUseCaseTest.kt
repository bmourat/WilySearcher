package ru.bmourat.wilysearcher

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import ru.bmourat.wilysearcher.domain.interactor.LoadInitialTweetsUseCase
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class LoadInitialTweetsUseCaseTest {

    private lateinit var sut: LoadInitialTweetsUseCase
    private lateinit var onlineRepository: TweetsRepository
    private lateinit var localRepository: TweetsRepository

    @Before
    fun setUp() {
        onlineRepository = Mockito.mock(TweetsRepository::class.java)
        localRepository = Mockito.mock(TweetsRepository::class.java)
        sut = LoadInitialTweetsUseCase(onlineRepository, localRepository, Schedulers.trampoline())
    }


    @Test
    fun `Local and online repositories are requested for tweets`() {

        // If
        val hashTag = "mockTag"
        `when`(localRepository.loadTweets(hashTag)).thenReturn(Single.just(listOf()))
        `when`(onlineRepository.loadTweets(hashTag)).thenReturn(Single.just(listOf()))

        // When
        sut.execute(hashTag, true).test()

        // Then
        verify(localRepository, times(1)).loadTweets(hashTag)
        verify(onlineRepository, times(1)).loadTweets(hashTag)
        verify(localRepository, times(1)).saveTweets(anyString(), anyList())
    }
}
