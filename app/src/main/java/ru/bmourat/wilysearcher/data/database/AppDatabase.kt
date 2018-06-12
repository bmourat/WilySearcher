package ru.bmourat.wilysearcher.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.bmourat.wilysearcher.data.tweet.TweetDao
import ru.bmourat.wilysearcher.data.tweet.TweetEntity

@Database(entities = [TweetEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tweetDao(): TweetDao

    companion object {
        const val dbName = "wilysearcher.db"
    }
}
