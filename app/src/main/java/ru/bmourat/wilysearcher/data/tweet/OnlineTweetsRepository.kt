package ru.bmourat.wilysearcher.data.tweet

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single
import ru.bmourat.wilysearcher.data.api.TwitterApi
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class OnlineTweetsRepository(
        private val twitterApi: TwitterApi,
        private val pageSize: Int): TweetsRepository {

    override fun loadTweets(hashTag: String, maxId: Long?, sinceId: Long?): Single<List<Tweet>> {
        return twitterApi.loadTweets(hashTag, pageSize)
    }

}
