package ru.bmourat.wilysearcher.app.di

import ru.bmourat.wilysearcher.app.di.activity.ActivityModule
import ru.bmourat.wilysearcher.app.common.WilySearcherApplication
import ru.bmourat.wilysearcher.app.di.activity.ActivityComponent
import ru.bmourat.wilysearcher.app.di.app.AppComponent
import ru.bmourat.wilysearcher.app.di.app.AppModule
import ru.bmourat.wilysearcher.app.di.app.DaggerAppComponent
import ru.bmourat.wilysearcher.app.ui.activity.BaseActivity

class ComponentFactory private constructor() {

    companion object {
        fun createApplicationComponent(app: WilySearcherApplication): AppComponent =
            DaggerAppComponent.builder().appModule(AppModule(app)).build()

        fun createActivityComponent(activity: BaseActivity, app: WilySearcherApplication): ActivityComponent =
            app.appComponent.plus(ActivityModule(activity))
    }
}
