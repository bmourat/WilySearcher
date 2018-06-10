package ru.bmourat.wilysearcher.app.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.rxkotlin.subscribeBy
import ru.bmourat.wilysearcher.app.mvp.view.TweetListView
import ru.bmourat.wilysearcher.domain.interactor.LoadInitialTweetsUseCase

@InjectViewState
class TweetListPresenter(
        private val loadInitialTweetsUseCase: LoadInitialTweetsUseCase)
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
                    onError = {}
                )
    }
}
