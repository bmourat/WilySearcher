package ru.bmourat.wilysearcher.app.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.bmourat.wilysearcher.R
import ru.bmourat.wilysearcher.app.common.WilySearcherApplication
import ru.bmourat.wilysearcher.data.api.TwitterApi
import ru.bmourat.wilysearcher.data.api.TwitterSdkApi
import ru.bmourat.wilysearcher.data.tweet.LocalTweetsRepository
import ru.bmourat.wilysearcher.data.tweet.OnlineTweetsRepository
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository
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
    fun provideLocalTweetsRepository(): TweetsRepository = LocalTweetsRepository()

    @Provides
    @Singleton
    @Named("Online")
    fun provideOnlineTweetsRepository(twitterApi: TwitterApi): TweetsRepository {
        val pageSize = app.resources.getInteger(R.integer.default_page_size)
        return OnlineTweetsRepository(twitterApi, pageSize)
    }

    @Provides
    @Singleton
    fun provideTwitterApi(): TwitterApi = TwitterSdkApi()

    /*
    @Provides
    @Singleton
    fun provideLogger(): Logger = CapsuleLogger()
    */
}
