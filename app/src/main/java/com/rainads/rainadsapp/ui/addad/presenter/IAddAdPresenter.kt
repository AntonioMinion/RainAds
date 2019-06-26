package com.rainads.rainadsapp.ui.addad.presenter

import com.rainads.rainadsapp.ui.addad.interactor.IAddAdInteractor
import com.rainads.rainadsapp.ui.addad.view.AddAdView
import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter

interface IAddAdPresenter<V : AddAdView, I : IAddAdInteractor> : MVPPresenter<V, I> {
    fun saveAd(adUrl: String, adPrice: String, adDuration: String, adDescription: String, adCountries: List<String>)
}