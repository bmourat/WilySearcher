package ru.bmourat.wilysearcher.app.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.twitter.sdk.android.core.models.Tweet

interface TweetListView: MvpView{

    @StateStrategyType(SkipStrategy::class)
    fun onFirstPresenterAttach()

    fun onRefreshFinished()

    fun addTweets(tweets:List<Tweet>)

    fun replaceTweets(tweets:List<Tweet>)

    fun insertTweets(tweets: List<Tweet>)
}
