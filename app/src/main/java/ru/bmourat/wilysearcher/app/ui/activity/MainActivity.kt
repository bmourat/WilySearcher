package ru.bmourat.wilysearcher.app.ui.activity

import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Search
import ru.bmourat.wilysearcher.R
import ru.bmourat.wilysearcher.app.mvp.presenter.TweetListPresenter
import ru.bmourat.wilysearcher.app.mvp.view.TweetListView
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : MvpAppCompatActivity(), TweetListView {

    @InjectPresenter
    lateinit var tweetListPresenter: TweetListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
