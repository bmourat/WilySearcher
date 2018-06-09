package ru.bmourat.wilysearcher.app.common

import android.app.Application
import com.twitter.sdk.android.core.Twitter

class WilySearcherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Twitter.initialize(this)
    }
}
