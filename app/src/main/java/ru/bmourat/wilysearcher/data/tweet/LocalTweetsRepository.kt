package ru.bmourat.wilysearcher.data.tweet

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.bmourat.wilysearcher.app.common.logger.Logger
import ru.bmourat.wilysearcher.app.common.logger.logTag
import ru.bmourat.wilysearcher.data.database.GsonTweetMapper
import ru.bmourat.wilysearcher.data.database.TweetMapper
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class LocalTweetsRepository(private val tweetDao: TweetDao,
                            private val tweetMapper: TweetMapper,
                            private val logger: Logger,
                            private val backgroundScheduler: Scheduler): TweetsRepository {

    override fun loadTweets(hashTag: String, maxId: Long?, sinceId: Long?): Single<List<Tweet>> {
        logger.verbose(logTag, "Loading tweets from database.")
        return tweetDao
                .loadTweets(hashTag)
                .subscribeOn(backgroundScheduler)
                .map {  it.map { tweetMapper.toTweet(it) } }
    }

    override fun saveTweets(hashTag: String, tweets: List<Tweet>) {
        Single.create<Boolean>{ emitter ->
            tweetDao.insertAll(tweets.map { tweetMapper.toTweetEntity(it, hashTag) })
            emitter.onSuccess(true)
        }
        .subscribeOn(backgroundScheduler)
        .subscribe()
    }
}
