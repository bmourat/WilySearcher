package ru.bmourat.wilysearcher.app.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.bmourat.wilysearcher.app.mvp.view.TweetListView
import ru.bmourat.wilysearcher.domain.interactor.LoadRecentTweetsUseCase

@InjectViewState
class TweetListPresenter(
        private val loadRecentTweetsUseCase: LoadRecentTweetsUseCase)
    : MvpPresenter<TweetListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onFirstPresenterAttach()
    }

    fun setInitialHashTag(hashTag: String) {
        loadRecentTweetsUseCase.execute(hashTag, true)
    }
}
