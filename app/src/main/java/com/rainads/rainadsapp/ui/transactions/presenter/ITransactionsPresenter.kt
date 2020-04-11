package com.rainads.rainadsapp.ui.transactions.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.myadlist.interactor.IMyAdListInteractor
import com.rainads.rainadsapp.ui.myadlist.view.MyAdListView
import com.rainads.rainadsapp.ui.transactions.interactor.ITransactionsInteractor
import com.rainads.rainadsapp.ui.transactions.view.TransactionsView
import com.rainads.rainadsapp.util.TransactionType

interface ITransactionsPresenter<V : TransactionsView, I : ITransactionsInteractor> : MVPPresenter<V, I> {
    fun downloadTransactions(transactionType: TransactionType)
}