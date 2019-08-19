package com.gunaya.demo.demomeow

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.robolectric.TestLifecycleApplication
import java.lang.reflect.Method

class MockApplication : Application(), TestLifecycleApplication {

    override fun onCreate() {
        super.onCreate()
        // Adding Koin modules to our application
        startKoin {
            androidContext(this@MockApplication)
            modules(mockAppModules)
        }
    }

    override fun beforeTest(method: Method) {}
    override fun prepareTest(o: Any) {}
    override fun afterTest(method: Method) {}
}
