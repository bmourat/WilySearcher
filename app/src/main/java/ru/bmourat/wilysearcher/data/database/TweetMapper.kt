package ru.bmourat.wilysearcher.data.database

import com.twitter.sdk.android.core.models.Tweet
import ru.bmourat.wilysearcher.data.tweet.TweetEntity

interface TweetMapper {

    fun toTweetEntity(tweet: Tweet, hashTag: String): TweetEntity

    fun toTweet(tweetEntity: TweetEntity): Tweet

}
