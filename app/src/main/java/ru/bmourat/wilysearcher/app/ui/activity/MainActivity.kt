package ru.bmourat.wilysearcher.app.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.activity_main.*
import ru.bmourat.wilysearcher.R
import ru.bmourat.wilysearcher.app.di.activity.ActivityComponent
import ru.bmourat.wilysearcher.app.mvp.presenter.TweetListPresenter
import ru.bmourat.wilysearcher.app.mvp.view.TweetListView
import ru.bmourat.wilysearcher.app.ui.adapter.TweetsAdapter
import ru.bmourat.wilysearcher.app.util.Logger
import ru.bmourat.wilysearcher.app.util.logTag
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : BaseActivity(), TweetListView {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var presenterProvider: Provider<TweetListPresenter>

    @InjectPresenter
    lateinit var presenter: TweetListPresenter

    private lateinit var tweetsAdapter: TweetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
    }

    private fun initializeViews() {
        rvTweets.layoutManager = LinearLayoutManager(this)
        tweetsAdapter = TweetsAdapter()
        rvTweets.adapter = tweetsAdapter
    }

    override fun addTweets(tweets: List<Tweet>) {
        tweetsAdapter.addTweets(tweets)
    }

    override fun replaceTweets(tweets: List<Tweet>) {
        tweetsAdapter.replaceTweets(tweets)
    }

    // region Infrastructure

    override fun componentTag(): String = logTag

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onFirstPresenterAttach() {
        presenter.setInitialHashTag(etCurrentTag.text.toString())
    }

    @ProvidePresenter
    fun providePresenter(): TweetListPresenter {
        return presenterProvider.get()
    }

    // endregion
}
