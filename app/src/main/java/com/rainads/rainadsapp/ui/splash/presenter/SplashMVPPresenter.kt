package com.rainads.rainadsapp.ui.splash.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.splash.interactor.SplashMVPInteractor
import com.rainads.rainadsapp.ui.splash.view.SplashMVPView

interface SplashMVPPresenter<V : SplashMVPView, I : SplashMVPInteractor> : MVPPresenter<V, I>