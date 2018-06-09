package ru.bmourat.wilysearcher.app.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import ru.bmourat.wilysearcher.R
import ru.bmourat.wilysearcher.app.di.activity.ActivityComponent
import ru.bmourat.wilysearcher.app.mvp.presenter.TweetListPresenter
import ru.bmourat.wilysearcher.app.mvp.view.TweetListView
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : BaseActivity(), TweetListView {

    @Inject
    lateinit var presenterProvider: Provider<TweetListPresenter>

    @InjectPresenter
    lateinit var presenter: TweetListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onFirstPresenterAttach() {
        presenter.setInitialHashTag(etCurrentTag.text.toString())
    }

    @ProvidePresenter
    fun providePresenter(): TweetListPresenter {
        return presenterProvider.get()
    }
}
