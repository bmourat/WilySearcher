package ru.bmourat.wilysearcher.app.di.activity

import dagger.Module
import dagger.Provides
import ru.bmourat.wilysearcher.app.mvp.presenter.TweetListPresenter
import ru.bmourat.wilysearcher.app.ui.activity.BaseActivity
import ru.bmourat.wilysearcher.domain.interactor.LoadInitialTweetsUseCase
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository
import javax.inject.Named

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ActivityScope
    fun provideLoadRecentTweetsUseCase(
            @Named("Online") onlineTweetsRepository: TweetsRepository,
            @Named("Local") localTweetsRepository: TweetsRepository): LoadInitialTweetsUseCase {
        return LoadInitialTweetsUseCase(onlineTweetsRepository, localTweetsRepository)
    }

    @Provides
    @ActivityScope
    fun provideTweetListPresenter(loadInitialTweetsUseCase: LoadInitialTweetsUseCase): TweetListPresenter =
            TweetListPresenter(loadInitialTweetsUseCase)

}
