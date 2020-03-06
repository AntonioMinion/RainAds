package com.rainads.rainadsapp.ui.transferpoints.presenter

import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.transferpoints.interactor.ITransferPointsInteractor
import com.rainads.rainadsapp.ui.transferpoints.view.TransferPointsView
import com.rainads.rainadsapp.util.SchedulerProvider
import com.rainads.rainadsapp.util.ToastType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TransferPointsPresenter<V : TransferPointsView, I : ITransferPointsInteractor>
@Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), ITransferPointsPresenter<V, I> {

    override fun getUser() {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(
                it.downloadUser()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe({ user ->
                        getView()?.hideProgress()
                        getView()?.userDownloaded(user)
                    }, { err ->
                        println(err)
                        getView()?.hideProgress()
                    })
            )
        }
    }

    override fun sendFunds(receiverId: String, amount: String) {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(
                it.makeTransferPointsCall(receiverId, amount)
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe({ msg ->
                        getView()?.hideProgress()
                        if (msg.contains("success", ignoreCase = true))
                            getView()?.sendFundsCallback(ToastType.SUCCESS, msg)
                        else
                            getView()?.sendFundsCallback(ToastType.ERROR, msg)
                    }, { err ->
                        println(err)
                        getView()?.hideProgress()
                        var errorMsg = err.message

                        if(errorMsg == null)
                            errorMsg = "An Error Has Occurred"

                        getView()?.sendFundsCallback(ToastType.ERROR, errorMsg)
                    })
            )
        }
    }

}