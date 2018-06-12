package ru.bmourat.wilysearcher.data.tweet

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface TweetDao {

    @Query("SELECT * FROM TweetEntity WHERE hash_tag = :hashTag")
    fun loadTweets(hashTag: String): Single<List<TweetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tweets: List<TweetEntity>)
}
