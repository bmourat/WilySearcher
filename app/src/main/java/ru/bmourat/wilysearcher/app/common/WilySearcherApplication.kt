package ru.bmourat.wilysearcher.app.common

import android.app.Application
import com.twitter.sdk.android.core.Twitter
import ru.bmourat.wilysearcher.app.di.ComponentFactory
import ru.bmourat.wilysearcher.app.di.app.AppComponent

class WilySearcherApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = ComponentFactory.createApplicationComponent(this)
        Twitter.initialize(this)
    }
}
