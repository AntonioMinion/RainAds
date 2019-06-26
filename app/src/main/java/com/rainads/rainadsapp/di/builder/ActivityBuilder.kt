package com.rainads.rainadsapp.di.builder

import com.rainads.rainadsapp.ui.addad.AddAdModule
import com.rainads.rainadsapp.ui.addad.view.AddAdActivity
import com.rainads.rainadsapp.ui.countryselect.CountrySelectDialogFragmentProvider
import com.rainads.rainadsapp.ui.initial.InitialActivityModule
import com.rainads.rainadsapp.ui.initial.view.InitialActivity
import com.rainads.rainadsapp.ui.levels.LevelsModule
import com.rainads.rainadsapp.ui.levels.view.LevelsActivity
import com.rainads.rainadsapp.ui.main.MainActivityModule
import com.rainads.rainadsapp.ui.main.view.MainActivity
import com.rainads.rainadsapp.ui.myadlist.MyAdListModule
import com.rainads.rainadsapp.ui.myadlist.view.MyAdListActivity
import com.rainads.rainadsapp.ui.splash.SplashActivityModule
import com.rainads.rainadsapp.ui.splash.view.SplashActivity
import com.rainads.rainadsapp.ui.watchad.WatchAdModule
import com.rainads.rainadsapp.ui.watchad.view.WatchAdActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(SplashActivityModule::class)])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [(InitialActivityModule::class)])
    abstract fun bindInitialActivity(): InitialActivity

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun buildMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(MyAdListModule::class)])
    abstract fun buildMyAdListActivity(): MyAdListActivity

    @ContributesAndroidInjector(modules = [(AddAdModule::class), (CountrySelectDialogFragmentProvider::class)])
    abstract fun buildAddAdActivity(): AddAdActivity

    @ContributesAndroidInjector(modules = [(WatchAdModule::class)])
    abstract fun buildWatchAdActivity(): WatchAdActivity

    @ContributesAndroidInjector(modules = [(LevelsModule::class)])
    abstract fun buildLevelsActivity(): LevelsActivity

}