package ru.bmourat.wilysearcher.data.tweet

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class TweetEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "hash_tag")
    val hashTag: String,
    @ColumnInfo(name = "tweet_content")
    val tweetContent: String
)
