package com.rainads.rainadsapp.ui.watchad.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.watchad.interactor.IWatchAdInteractor
import com.rainads.rainadsapp.ui.watchad.view.WatchAdView

interface IWatchAdPresenter<V : WatchAdView, I : IWatchAdInteractor> : MVPPresenter<V, I> {
    fun startTimer(adId: String, adDuration: Long)
    fun pauseTimer()
    fun watchAdExtra(adId: String)
}