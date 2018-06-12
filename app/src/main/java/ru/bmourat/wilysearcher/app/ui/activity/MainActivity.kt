package ru.bmourat.wilysearcher.app.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
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
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


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
        rvTweets.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                logger.verbose(logTag, "Loading next page.")
                presenter.loadNextTweets()
            }
        })
        srlRefresher.setOnRefreshListener {
            logger.verbose(logTag, "Refreshing tweets.")
            presenter.refreshTweets()
        }
        etCurrentTag.setOnEditorActionListener { view, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_GO -> {
                    presenter.setHashTag(view.text.toString())
                    hideKeyboard(view)
                    true
                }
                else -> false
            }
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
        presenter.setHashTag(etCurrentTag.text.toString())
    }

    override fun showLoading(show: Boolean) {
        pbLoadingProgress.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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
