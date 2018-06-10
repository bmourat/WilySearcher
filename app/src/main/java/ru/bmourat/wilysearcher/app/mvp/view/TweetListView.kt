package ru.bmourat.wilysearcher.app.mvp.view

import com.arellomobile.mvp.MvpView
import com.twitter.sdk.android.core.models.Tweet

interface TweetListView: MvpView{
    fun onFirstPresenterAttach()
    fun addTweets(tweets:List<Tweet>)
    fun replaceTweets(tweets:List<Tweet>)
}
