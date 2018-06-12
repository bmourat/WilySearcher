package ru.bmourat.wilysearcher.data.database

import com.google.gson.GsonBuilder
import com.twitter.sdk.android.core.models.*
import ru.bmourat.wilysearcher.data.tweet.TweetEntity

class TweetMapper{

    private val jsonConverter = GsonBuilder()
            .registerTypeAdapterFactory(SafeListAdapter())
            .registerTypeAdapterFactory(SafeMapAdapter())
            .registerTypeAdapter(BindingValues::class.java, BindingValuesAdapter())
            .create()

    fun toTweetEntity(tweet: Tweet, hashTag: String): TweetEntity =
            TweetEntity(tweet.id, hashTag, fromTweet(tweet))

    fun toTweet(tweetEntity: TweetEntity): Tweet =
            toTweet(tweetEntity.tweetContent)

    private fun fromTweet(tweet: Tweet): String =
            jsonConverter.toJson(tweet)

    private fun toTweet(tweetString: String): Tweet =
            jsonConverter.fromJson(tweetString, Tweet::class.java)


}
