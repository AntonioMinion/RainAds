package com.rainads.rainadsapp.ui.myadlist.presenter

import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.myadlist.interactor.IMyAdListInteractor
import com.rainads.rainadsapp.ui.myadlist.view.MyAdListView
import com.rainads.rainadsapp.util.Handler
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.SchedulerProvider
import com.rainads.rainadsapp.util.ToastType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MyAdListPresenter<V : MyAdListView, I : IMyAdListInteractor>
@Inject internal constructor(
        interactor: I,
        schedulerProvider: SchedulerProvider,
        disposable: CompositeDisposable
) : BasePresenter<V, I>(
        interactor = interactor,
        schedulerProvider = schedulerProvider,
        compositeDisposable = disposable
), IMyAdListPresenter<V, I> {

    override fun adStatusChanged(id: String, newStatus: String) {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(
                    it.changeAdStatus(id, newStatus)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ message ->
                                getView()?.hideProgress()
                                getView()?.showMessage(ToastType.SUCCESS, message.getString("message"))
                            }, { err ->
                                println(err)
                                getView()?.hideProgress()
                                getView()?.showMessage(ToastType.ERROR, if (err.message.isNullOrEmpty()) Handler.getErrorMessage(MyConstants.ERROR_CHANGE_AD_STATUS) else err.message)
                            })
            )
        }
    }

    override fun onViewPrepared() {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(it.getUser()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe { user ->
                        getView()?.let { it ->
                            it.hideProgress()
                            it.userDownloaded(user)
                        }
                    })
        }

    }
}