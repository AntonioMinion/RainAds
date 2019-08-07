package com.rainads.rainadsapp.ui.deposit.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.deposit.interactor.IDepositInteractor
import com.rainads.rainadsapp.ui.deposit.view.DepositView

interface IDepositPresenter<V : DepositView, I : IDepositInteractor> : MVPPresenter<V, I> {
    fun depositRequest(amount: String)
    fun withdrawRequest(btcAddress: String, amount: String)
    fun getUser()
}