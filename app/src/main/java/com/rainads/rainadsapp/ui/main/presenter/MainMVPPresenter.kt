package com.rainads.rainadsapp.ui.main.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.main.interactor.MainMVPInteractor
import com.rainads.rainadsapp.ui.main.view.MainMVPView

interface MainMVPPresenter<V : MainMVPView, I : MainMVPInteractor> : MVPPresenter<V, I> {
    fun logoutUser()
    fun getUser()
    fun getAd()
}