package ru.bmourat.wilysearcher.app.di.activity

import dagger.Module
import dagger.Provides
import ru.bmourat.wilysearcher.app.mvp.presenter.TweetListPresenter
import ru.bmourat.wilysearcher.app.ui.activity.BaseActivity

@Module
class ActivityModule(private val activity: BaseActivity) {

    /*
    @Provides
    @ActivityScope
    fun provideTweetListPresenter(): TweetListPresenter =
            TweetListPresenter()

*/
}
