package com.rainads.rainadsapp.ui.transactions.presenter

import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.transactions.interactor.ITransactionsInteractor
import com.rainads.rainadsapp.ui.transactions.view.TransactionsView
import com.rainads.rainadsapp.util.SchedulerProvider
import com.rainads.rainadsapp.util.TransactionType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class TransactionsPresenter<V : TransactionsView, I : ITransactionsInteractor>
@Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), ITransactionsPresenter<V, I> {

    override fun downloadTransactions(transactionType: TransactionType) {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(
                it.getTransactionsCall()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe({ transactionData ->
                        getView()?.let { it ->
                            it.hideProgress()
                            val transactionList = transactionData.transactionList
                            val filteredList =
                                transactionList.filter { transaction -> transaction.transactionType == transactionType.name }
                            it.transactionsDownloaded(filteredList)
                        }
                    }, { err ->
                        println(err)
                        getView()?.let { it ->
                            it.hideProgress()
                            it.transactionsDownloaded(ArrayList())
                        }
                    })
            )
        }
    }


}