package com.rainads.rainadsapp

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.rainads.rainadsapp.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MyApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    internal lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector() = activityDispatchingAndroidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return mFragmentInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

}