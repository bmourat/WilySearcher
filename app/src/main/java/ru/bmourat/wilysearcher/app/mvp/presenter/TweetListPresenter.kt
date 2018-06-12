package ru.bmourat.wilysearcher.app.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.rxkotlin.subscribeBy
import ru.bmourat.wilysearcher.app.mvp.view.TweetListView
import ru.bmourat.wilysearcher.app.util.Logger
import ru.bmourat.wilysearcher.app.util.logTag
import ru.bmourat.wilysearcher.domain.interactor.LoadInitialTweetsUseCase
import ru.bmourat.wilysearcher.domain.interactor.LoadNextPageUseCase
import ru.bmourat.wilysearcher.domain.interactor.SwipeToRefreshUseCase

@InjectViewState
class TweetListPresenter(
        private val loadInitialTweetsUseCase: LoadInitialTweetsUseCase,
        private val loadNextPageUseCase: LoadNextPageUseCase,
        private val swipeToRefreshUseCase: SwipeToRefreshUseCase,
        private val logger: Logger)
    : MvpPresenter<TweetListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onFirstPresenterAttach()
    }

    fun setInitialHashTag(hashTag: String) {
        loadInitialTweetsUseCase
                .execute(hashTag, true)
                .subscribeBy(
                    onNext = { viewState.replaceTweets(it) },
                    onError = { logger.error(logTag,  "Error retrieving initial tweets", it) }
                )
    }
}
