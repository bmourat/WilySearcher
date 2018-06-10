package ru.bmourat.wilysearcher.data.api

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single

interface TwitterApi {
    fun loadTweets(hashTag: String, count: Int,
                   sinceId: Long? = null, maxId: Long? = null): Single<List<Tweet>>
}
