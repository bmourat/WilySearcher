package ru.bmourat.wilysearcher.app.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.activity_main.*
import ru.bmourat.wilysearcher.R
import ru.bmourat.wilysearcher.app.di.activity.ActivityComponent
import ru.bmourat.wilysearcher.app.mvp.presenter.TweetListPresenter
import ru.bmourat.wilysearcher.app.mvp.view.TweetListView
import ru.bmourat.wilysearcher.app.ui.adapter.TweetsAdapter
import ru.bmourat.wilysearcher.app.common.logger.Logger
import ru.bmourat.wilysearcher.app.common.logger.logTag
import ru.bmourat.wilysearcher.app.ui.util.EndlessRecyclerViewScrollListener
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
        val layoutManager = LinearLayoutManager(this)
        rvTweets.layoutManager = layoutManager
        tweetsAdapter = TweetsAdapter()
        rvTweets.adapter = tweetsAdapter
        rvTweets.addOnScrollListener(object: EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                logger.verbose(logTag, "Loading next page.")
                presenter.loadNextTweets()
            }
        })
        srlRefresher.setOnRefreshListener {
            logger.verbose(logTag,  "Refreshing tweets.")
            presenter.refreshTweets()
        }
    }

    override fun addTweets(tweets: List<Tweet>) {
        tweetsAdapter.addTweets(tweets)
    }


    override fun replaceTweets(tweets: List<Tweet>) {
        tweetsAdapter.replaceTweets(tweets)
    }

    override fun insertTweets(tweets: List<Tweet>) {
        tweetsAdapter.insertTweets(tweets)
    }

    // region Infrastructure

    override fun componentTag(): String = logTag

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onFirstPresenterAttach() {
        presenter.setInitialHashTag(etCurrentTag.text.toString())
    }

    override fun onRefreshFinished() {
        srlRefresher.isRefreshing = false
    }

    @ProvidePresenter
    fun providePresenter(): TweetListPresenter {
        return presenterProvider.get()
    }

    // endregion
}
