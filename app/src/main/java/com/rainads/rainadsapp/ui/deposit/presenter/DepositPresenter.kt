package com.rainads.rainadsapp.ui.deposit.presenter

import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.deposit.interactor.IDepositInteractor
import com.rainads.rainadsapp.ui.deposit.view.DepositView
import com.rainads.rainadsapp.util.SchedulerProvider
import com.rainads.rainadsapp.util.ToastType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class DepositPresenter<V : DepositView, I : IDepositInteractor>
@Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), IDepositPresenter<V, I> {

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


    override fun withdrawRequest(btcAddress: String, amount: String) {
        getView()?.showProgress()
        interactor?.let{
            compositeDisposable.add(
                    it.sendWithdrawRequest(btcAddress, amount)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ result ->
                                getView()?.hideProgress()
                                getView()?.showMessage(ToastType.INFO, result.toString())
                            }, { err ->
                                println(err)
                                getView()?.hideProgress()
                                getView()?.showMessage(ToastType.ERROR, err.message.toString())
                            })
            )
        }
    }

    override fun depositRequest(amount: String) {
       getView()?.showProgress()
        interactor?.let{
            compositeDisposable.add(
                    it.sendDepositRequest(amount)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ result ->
                                getView()?.hideProgress()
                                getView()?.showMessage(ToastType.INFO, result.toString())
                            }, { err ->
                                println(err)
                                getView()?.hideProgress()
                                getView()?.showMessage(ToastType.ERROR, err.message.toString())
                            })
            )
        }
    }


}