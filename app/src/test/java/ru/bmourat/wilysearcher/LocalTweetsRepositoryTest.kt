package ru.bmourat.wilysearcher

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import ru.bmourat.wilysearcher.app.common.logger.Logger
import ru.bmourat.wilysearcher.data.database.TweetMapper
import ru.bmourat.wilysearcher.data.tweet.LocalTweetsRepository
import ru.bmourat.wilysearcher.data.tweet.TweetDao
import ru.bmourat.wilysearcher.data.tweet.TweetEntity
import ru.bmourat.wilysearcher.mapper.TweetMapperMock

class LocalTweetsRepositoryTest {

    private lateinit var sut: LocalTweetsRepository
    private lateinit var tweetDao: TweetDao
    private lateinit var tweetMapper: TweetMapper
    private lateinit var logger: Logger

    @Before
    fun setUp() {
        tweetDao = Mockito.mock(TweetDao::class.java)
        logger = Mockito.mock(Logger::class.java)
        tweetMapper = TweetMapperMock("mockHash", "mockContent")
        sut = LocalTweetsRepository(tweetDao, tweetMapper, logger, Schedulers.trampoline())
    }


    @Test
    fun `Local tweets repository returns tweets from dao`() {

        // If
        val hashTag = "#hashtag"
        val tweetContent = "tweet_content"
        val tweetList = listOf(
                TweetEntity(1, hashTag, tweetContent),
                TweetEntity(2, hashTag, tweetContent),
                TweetEntity(3, hashTag, tweetContent))
        val subscriber = TestObserver<List<TweetEntity>>()
        `when`(tweetDao.loadTweets(hashTag)).thenReturn(Single.just(tweetList))


        // When
        val observer = sut.loadTweets(hashTag).test()

        // Then
        observer.assertValueCount(1)
        observer.assertComplete()
        observer.assertValue {
            for(i in 0 until tweetList.size) {
                if (it[i].id != tweetList[i].id) {
                    return@assertValue false
                }
            }
            return@assertValue true
        }
    }

    @Test
    fun `Insert is called on dao object`() {

        // When
        sut.saveTweets("mockTag", listOf())

        // Then
        verify(tweetDao, times(1)).insertAll(anyList())
    }
}
