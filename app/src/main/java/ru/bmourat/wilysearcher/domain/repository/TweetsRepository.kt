package ru.bmourat.wilysearcher.domain.repository

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable

interface TweetsRepository {
    fun loadTweets(hashTag: String): Observable<List<Tweet>>
}
