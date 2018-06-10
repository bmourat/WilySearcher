package ru.bmourat.wilysearcher.domain.repository

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single

interface TweetsRepository {
    fun loadTweets(hashTag: String): Single<List<Tweet>>
}
