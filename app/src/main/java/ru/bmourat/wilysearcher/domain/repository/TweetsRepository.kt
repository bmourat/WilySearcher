package ru.bmourat.wilysearcher.domain.repository

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single

interface TweetsRepository {

    fun loadTweets(hashTag: String, maxId: Long? = null, sinceId: Long? = null): Single<List<Tweet>>

    fun saveTweets(hashTag: String, tweets: List<Tweet>)

}
