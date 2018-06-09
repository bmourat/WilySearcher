package ru.bmourat.wilysearcher.data.tweet

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class OnlineTweetsRepository: TweetsRepository {

    override fun loadTweets(hashTag: String): Observable<List<Tweet>> {
        return Observable.just(listOf())
    }

}
