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

        private const val daggerService = "daggerService"
        private const val rootComponentTag = "rootComponentTag"

        fun applicationComponent(app: WilySearcherApplication): AppComponent {
            val appComponent = DaggerAppComponent.builder().appModule(AppModule(app)).build()
            val componentStorage = appComponent.componentStorage()
            componentStorage
                    .createRootNode(rootComponentTag)
                    .bindService(daggerService, appComponent)
            return appComponent
        }

        fun activityComponent(tag: String, activity: BaseActivity, app: WilySearcherApplication): ActivityComponent {
            val componentStorage = app.appComponent.componentStorage()
            return if (componentStorage.hasNodeWithKey(tag)) {
                componentStorage.getNode(tag).getService(daggerService)
            } else {
                val activityComponent = app.appComponent.plus(ActivityModule(activity))
                val node = componentStorage.createChildNode(componentStorage.getNode(rootComponentTag), tag)
                node.bindService(daggerService, activityComponent)
                activityComponent
            }
        }

        fun removeComponent(tag: String, app: WilySearcherApplication) {
            val componentStorage = app.appComponent.componentStorage()
            componentStorage.removeNodeAndChildren(componentStorage.getNode(tag))
        }

    }
}
