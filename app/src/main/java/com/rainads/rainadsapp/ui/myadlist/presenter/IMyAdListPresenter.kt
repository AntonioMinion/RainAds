package com.rainads.rainadsapp.ui.myadlist.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.myadlist.interactor.IMyAdListInteractor
import com.rainads.rainadsapp.ui.myadlist.view.MyAdListView

interface IMyAdListPresenter<V : MyAdListView, I : IMyAdListInteractor> : MVPPresenter<V, I> {
    fun onViewPrepared()
    fun adStatusChanged(id: String, newStatus: String)
}