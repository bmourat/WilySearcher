package ru.bmourat.wilysearcher.app.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.rxkotlin.subscribeBy
import ru.bmourat.wilysearcher.app.mvp.view.TweetListView
import ru.bmourat.wilysearcher.app.common.logger.Logger
import ru.bmourat.wilysearcher.app.common.logger.logTag
import ru.bmourat.wilysearcher.domain.interactor.LoadInitialTweetsUseCase
import ru.bmourat.wilysearcher.domain.interactor.LoadNextPageUseCase
import ru.bmourat.wilysearcher.domain.interactor.SwipeToRefreshUseCase
import ru.bmourat.wilysearcher.domain.util.PagingOptions

@InjectViewState
class TweetListPresenter(
        private val loadInitialTweetsUseCase: LoadInitialTweetsUseCase,
        private val loadNextPageUseCase: LoadNextPageUseCase,
        private val swipeToRefreshUseCase: SwipeToRefreshUseCase,
        private val pagingOptions: PagingOptions,
        private val logger: Logger)
    : BasePresenter<TweetListView>() {

    private var tweets: MutableList<Tweet> = mutableListOf()
    private var currentHashTag: String? = null
    private var loading = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onFirstPresenterAttach()
    }

    fun setInitialHashTag(hashTag: String) {
        currentHashTag = hashTag
        disposeOnDestroy(
            loadInitialTweetsUseCase
                .execute(hashTag, true)
                .subscribeBy(
                    onNext = { replaceTweets(it) },
                    onError = { logger.error(logTag,  "Error retrieving initial tweets", it) }
                )
        )
    }

    fun refreshTweets() {
        if (loading) return
        currentHashTag?.let {
            loading = true
            disposeOnDestroy(
                swipeToRefreshUseCase
                    .execute(it, tweets.maxBy { it.id })
                    .doAfterTerminate {
                        loading = false
                        viewState.onRefreshFinished()
                    }
                    .subscribeBy(
                        onSuccess = {
                            /**
                             *  Means we might have more tweets to load
                             *  before we reach tweets we already have,
                             *  so we are just replacing all tweets.
                             */
                            if (it.size == pagingOptions.pageSize) {
                                replaceTweets(it)
                            } else {
                                insertTweets(it)
                            }
                        },
                        onError = { logger.error(logTag,  "Error loading tweets", it) }
                    )
            )
        }
    }

    fun loadNextTweets() {
        if (loading) return
        currentHashTag?.let{
            loading = true
            disposeOnDestroy(
                loadNextPageUseCase
                    .execute(it, tweets.minBy { it.id })
                    .doAfterTerminate { loading = false }
                    .subscribeBy(
                        onSuccess = { addTweets(it) },
                        onError = { logger.error(logTag,  "Error loading next page", it) }
                    )
            )
        }
    }

    private fun addTweets(newTweets: List<Tweet>) {
        tweets.addAll(newTweets)
        viewState.addTweets(newTweets)
    }

    private fun insertTweets(newTweets: List<Tweet>) {
        tweets.addAll(0, newTweets)
        viewState.insertTweets(newTweets)
    }

    private fun replaceTweets(newTweets: List<Tweet>) {
        tweets = newTweets.toMutableList()
        viewState.replaceTweets(newTweets)
    }
}
