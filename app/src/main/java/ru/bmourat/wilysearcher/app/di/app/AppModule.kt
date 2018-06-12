package ru.bmourat.wilysearcher.app.di.app

import android.arch.persistence.room.Room
import android.content.Context
import com.zhuinden.servicetree.ServiceTree
import dagger.Module
import dagger.Provides
import ru.bmourat.wilysearcher.R
import ru.bmourat.wilysearcher.app.common.WilySearcherApplication
import ru.bmourat.wilysearcher.app.common.logger.AndroidLogger
import ru.bmourat.wilysearcher.app.common.logger.Logger
import ru.bmourat.wilysearcher.data.api.TwitterApi
import ru.bmourat.wilysearcher.data.api.TwitterSdkApi
import ru.bmourat.wilysearcher.data.database.AppDatabase
import ru.bmourat.wilysearcher.data.tweet.LocalTweetsRepository
import ru.bmourat.wilysearcher.data.tweet.OnlineTweetsRepository
import ru.bmourat.wilysearcher.data.tweet.TweetDao
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository
import ru.bmourat.wilysearcher.domain.util.PagingOptions
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule(val app: WilySearcherApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    @Named("Local")
    fun provideLocalTweetsRepository(tweetDao: TweetDao, logger: Logger): TweetsRepository =
            LocalTweetsRepository(tweetDao, logger)

    @Provides
    @Singleton
    @Named("Online")
    fun provideOnlineTweetsRepository(twitterApi: TwitterApi): TweetsRepository {
        val pageSize = app.resources.getInteger(R.integer.default_page_size)
        return OnlineTweetsRepository(twitterApi, PagingOptions(pageSize))
    }

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase =
            Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.dbName).build()

    @Provides
    @Singleton
    fun provideTweetDao(db: AppDatabase): TweetDao = db.tweetDao()


    @Provides
    @Singleton
    fun provideTwitterApi(logger: Logger): TwitterApi = TwitterSdkApi(logger)

    @Provides
    @Singleton
    fun provideComponentStorage(): ServiceTree = ServiceTree()

    @Provides
    @Singleton
    fun provideLogger(): Logger = AndroidLogger()
}

