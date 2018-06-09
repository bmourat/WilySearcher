package ru.bmourat.wilysearcher.app.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.bmourat.wilysearcher.app.common.WilySearcherApplication
import javax.inject.Singleton

@Module
class AppModule(val app: WilySearcherApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    /*
    @Provides
    @Singleton
    fun provideLogger(): Logger = CapsuleLogger()
    */
}
