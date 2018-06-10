package ru.bmourat.wilysearcher.data.tweet

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class LocalTweetsRepository: TweetsRepository {

    override fun loadTweets(hashTag: String): Single<List<Tweet>> {
        return Single.just(listOf())
    }

}
