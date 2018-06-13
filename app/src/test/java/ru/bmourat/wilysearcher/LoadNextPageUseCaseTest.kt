package ru.bmourat.wilysearcher

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import ru.bmourat.wilysearcher.domain.interactor.LoadNextPageUseCase
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class LoadNextPageUseCaseTest {

    private lateinit var sut: LoadNextPageUseCase
    private lateinit var onlineRepository: TweetsRepository

    @Before
    fun setUp() {
        onlineRepository = Mockito.mock(TweetsRepository::class.java)
        sut = LoadNextPageUseCase(onlineRepository, Schedulers.trampoline())
    }


    @Test
    fun `Searches for tweets in online repository`() {

        // If
        val hashTag = "mockTag"
        val testId = 1L
        val testList = listOf(tweetWithId(1), tweetWithId(2), tweetWithId(3))
        `when`(onlineRepository.loadTweets(hashTag, testId)).thenReturn(Single.just(testList))

        // When
        val observer = sut.execute(hashTag, tweetWithId(testId)).test()

        // Then
        verify(onlineRepository, times(1)).loadTweets(hashTag, maxId = testId)
        observer.assertResult(testList)
    }
}
