package ru.bmourat.wilysearcher.app.di.activity

import dagger.Subcomponent
import ru.bmourat.wilysearcher.app.ui.activity.MainActivity

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}
