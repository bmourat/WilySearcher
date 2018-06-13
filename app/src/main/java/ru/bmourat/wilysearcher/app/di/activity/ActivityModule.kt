package ru.bmourat.wilysearcher.app.di.activity

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.bmourat.wilysearcher.R
import ru.bmourat.wilysearcher.app.mvp.presenter.TweetListPresenter
import ru.bmourat.wilysearcher.app.ui.activity.BaseActivity
import ru.bmourat.wilysearcher.app.common.logger.Logger
import ru.bmourat.wilysearcher.domain.interactor.LoadInitialTweetsUseCase
import ru.bmourat.wilysearcher.domain.interactor.LoadNextPageUseCase
import ru.bmourat.wilysearcher.domain.interactor.LoadMostRecentTweetsUseCase
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository
import ru.bmourat.wilysearcher.domain.util.PagingOptions
import javax.inject.Named

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ActivityScope
    fun provideLoadRecentTweetsUseCase(
            @Named("Online") onlineTweetsRepository: TweetsRepository,
            @Named("Local") localTweetsRepository: TweetsRepository): LoadInitialTweetsUseCase =
            LoadInitialTweetsUseCase(onlineTweetsRepository, localTweetsRepository, AndroidSchedulers.mainThread())

    @Provides
    @ActivityScope
    fun provideLoadNextPageUseCase(
            @Named("Online") onlineTweetsRepository: TweetsRepository): LoadNextPageUseCase =
            LoadNextPageUseCase(onlineTweetsRepository, AndroidSchedulers.mainThread())

    @Provides
    @ActivityScope
    fun provideSwipeToRefreshUseCase(
            @Named("Online") onlineTweetsRepository: TweetsRepository): LoadMostRecentTweetsUseCase =
            LoadMostRecentTweetsUseCase(onlineTweetsRepository, AndroidSchedulers.mainThread())

    @Provides
    @ActivityScope
    fun provideTweetListPresenter(loadInitialTweetsUseCase: LoadInitialTweetsUseCase,
                                  loadNextPageUseCase: LoadNextPageUseCase,
                                  loadMostRecentTweetsUseCase: LoadMostRecentTweetsUseCase,
                                  logger: Logger): TweetListPresenter {

        val pageSize = activity.resources.getInteger(R.integer.default_page_size)
        return TweetListPresenter(loadInitialTweetsUseCase, loadNextPageUseCase, loadMostRecentTweetsUseCase,
                PagingOptions(pageSize), logger)

    }
}
