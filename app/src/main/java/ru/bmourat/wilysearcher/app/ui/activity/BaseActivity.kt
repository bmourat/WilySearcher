package ru.bmourat.wilysearcher.app.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.bmourat.wilysearcher.app.common.WilySearcherApplication
import ru.bmourat.wilysearcher.app.di.ComponentFactory
import ru.bmourat.wilysearcher.app.di.activity.ActivityComponent

@SuppressLint("Registered")
abstract class BaseActivity: MvpAppCompatActivity() {

    private lateinit var activityComponent: ActivityComponent

    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = ComponentFactory.activityComponent(
                componentTag(),
                this,
                application as WilySearcherApplication)
        inject(activityComponent)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        if (isFinishing) {
            ComponentFactory.removeComponent(
                    componentTag(),
                    application as WilySearcherApplication)
        }
    }

    fun disposeOnDestroy(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected abstract fun inject(activityComponent: ActivityComponent)
    protected abstract fun componentTag(): String
}
