package ru.bmourat.wilysearcher.app.di.activity

import dagger.Module
import dagger.Provides
import ru.bmourat.wilysearcher.app.mvp.presenter.TweetListPresenter
import ru.bmourat.wilysearcher.app.ui.activity.BaseActivity
import ru.bmourat.wilysearcher.app.util.Logger
import ru.bmourat.wilysearcher.domain.interactor.LoadInitialTweetsUseCase
import ru.bmourat.wilysearcher.domain.interactor.LoadNextPageUseCase
import ru.bmourat.wilysearcher.domain.interactor.SwipeToRefreshUseCase
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository
import javax.inject.Named

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ActivityScope
    fun provideLoadRecentTweetsUseCase(
            @Named("Online") onlineTweetsRepository: TweetsRepository,
            @Named("Local") localTweetsRepository: TweetsRepository): LoadInitialTweetsUseCase =
            LoadInitialTweetsUseCase(onlineTweetsRepository, localTweetsRepository)

    @Provides
    @ActivityScope
    fun provideLoadNextPageUseCase(
            @Named("Online") onlineTweetsRepository: TweetsRepository): LoadNextPageUseCase =
            LoadNextPageUseCase(onlineTweetsRepository)

    @Provides
    @ActivityScope
    fun provideSwipeToRefreshUseCase(
            @Named("Online") onlineTweetsRepository: TweetsRepository): SwipeToRefreshUseCase =
            SwipeToRefreshUseCase(onlineTweetsRepository)

    @Provides
    @ActivityScope
    fun provideTweetListPresenter(loadInitialTweetsUseCase: LoadInitialTweetsUseCase,
                                  loadNextPageUseCase: LoadNextPageUseCase,
                                  swipeToRefreshUseCase: SwipeToRefreshUseCase,
                                  logger: Logger): TweetListPresenter =
            TweetListPresenter(loadInitialTweetsUseCase, loadNextPageUseCase, swipeToRefreshUseCase, logger)

}
