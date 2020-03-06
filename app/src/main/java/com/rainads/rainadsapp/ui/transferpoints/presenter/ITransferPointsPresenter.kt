package com.rainads.rainadsapp.ui.transferpoints.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.transferpoints.interactor.ITransferPointsInteractor
import com.rainads.rainadsapp.ui.transferpoints.view.TransferPointsView

interface ITransferPointsPresenter<V : TransferPointsView, I : ITransferPointsInteractor> : MVPPresenter<V, I> {
    fun sendFunds(receiverId: String, amount: String)
    fun getUser()
}