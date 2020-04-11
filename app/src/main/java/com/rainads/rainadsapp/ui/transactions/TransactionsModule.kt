package com.rainads.rainadsapp.ui.transactions

import androidx.recyclerview.widget.LinearLayoutManager
import com.rainads.rainadsapp.ui.transactions.interactor.ITransactionsInteractor
import com.rainads.rainadsapp.ui.transactions.interactor.TransactionsInteractor
import com.rainads.rainadsapp.ui.transactions.presenter.ITransactionsPresenter
import com.rainads.rainadsapp.ui.transactions.presenter.TransactionsPresenter
import com.rainads.rainadsapp.ui.transactions.view.TransactionsActivity
import com.rainads.rainadsapp.ui.transactions.view.TransactionsAdapter
import com.rainads.rainadsapp.ui.transactions.view.TransactionsView
import dagger.Module
import dagger.Provides

@Module
class TransactionsModule {

    @Provides
    internal fun provideTransactionsInteractor(interactor: TransactionsInteractor): ITransactionsInteractor =
        interactor

    @Provides
    internal fun provideTransactionsPresenter(presenter: TransactionsPresenter<TransactionsView, ITransactionsInteractor>)
            : ITransactionsPresenter<TransactionsView, ITransactionsInteractor> = presenter

    @Provides
    internal fun provideTransactionsAdapter(): TransactionsAdapter =
        TransactionsAdapter(ArrayList())

    @Provides
    internal fun provideLinearLayoutManager(activity: TransactionsActivity): LinearLayoutManager =
        LinearLayoutManager(activity)


}