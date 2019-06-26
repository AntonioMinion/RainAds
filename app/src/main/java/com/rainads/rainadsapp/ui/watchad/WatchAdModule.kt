package com.rainads.rainadsapp.ui.watchad

import com.rainads.rainadsapp.ui.watchad.interactor.IWatchAdInteractor
import com.rainads.rainadsapp.ui.watchad.interactor.WatchAdInteractor
import com.rainads.rainadsapp.ui.watchad.presenter.IWatchAdPresenter
import com.rainads.rainadsapp.ui.watchad.presenter.WatchAdPresenter
import com.rainads.rainadsapp.ui.watchad.view.WatchAdView
import dagger.Module
import dagger.Provides

@Module
class WatchAdModule {

    @Provides
    internal fun provideWatchAdInteractor(interactor: WatchAdInteractor): IWatchAdInteractor = interactor

    @Provides
    internal fun provideWatchAdPresenter(presenter: WatchAdPresenter<WatchAdView, IWatchAdInteractor>)
            : IWatchAdPresenter<WatchAdView, IWatchAdInteractor> = presenter

}