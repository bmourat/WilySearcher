package ru.bmourat.wilysearcher.app.di.app

import com.zhuinden.servicetree.ServiceTree
import dagger.Component
import ru.bmourat.wilysearcher.app.di.activity.ActivityModule
import ru.bmourat.wilysearcher.app.di.activity.ActivityComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun plus(activityModule: ActivityModule): ActivityComponent
    fun componentStorage(): ServiceTree
}
