package ru.bmourat.wilysearcher.mapper

import com.twitter.sdk.android.core.models.Tweet
import ru.bmourat.wilysearcher.data.database.TweetMapper
import ru.bmourat.wilysearcher.data.tweet.TweetEntity
import ru.bmourat.wilysearcher.tweetWithId

class TweetMapperMock(private val hashTag: String, private val tweetContent: String): TweetMapper {

    override fun toTweetEntity(tweet: Tweet, hashTag: String): TweetEntity {
        return TweetEntity(tweet.id, hashTag, tweetContent)
    }

    override fun toTweet(tweetEntity: TweetEntity): Tweet {
        return tweetWithId(tweetEntity.id)
    }

}
