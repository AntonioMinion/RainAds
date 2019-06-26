package com.rainads.rainadsapp.ui.splash

import com.rainads.rainadsapp.ui.splash.interactor.SplashInteractor
import com.rainads.rainadsapp.ui.splash.interactor.SplashMVPInteractor
import com.rainads.rainadsapp.ui.splash.presenter.SplashMVPPresenter
import com.rainads.rainadsapp.ui.splash.presenter.SplashPresenter
import com.rainads.rainadsapp.ui.splash.view.SplashMVPView
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {

    @Provides
    internal fun provideSplashInteractor(splashInteractor: SplashInteractor): SplashMVPInteractor = splashInteractor

    @Provides
    internal fun provideSplashPresenter(splashPresenter: SplashPresenter<SplashMVPView, SplashMVPInteractor>)
            : SplashMVPPresenter<SplashMVPView, SplashMVPInteractor> = splashPresenter
}