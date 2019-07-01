package com.rainads.rainadsapp.ui.main.presenter

import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.main.interactor.MainMVPInteractor
import com.rainads.rainadsapp.ui.main.view.MainMVPView
import com.rainads.rainadsapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MainPresenter<V : MainMVPView, I : MainMVPInteractor> @Inject internal constructor(
        interactor: I,
        schedulerProvider: SchedulerProvider,
        disposable: CompositeDisposable
) : BasePresenter<V, I>(
        interactor = interactor,
        schedulerProvider = schedulerProvider,
        compositeDisposable = disposable
), MainMVPPresenter<V, I> {


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

    override fun getAd() {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(
                    it.downloadAd()
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ adResponse ->
                                getView()?.hideProgress()
                                getView()?.adFound(adResponse)
                            }, { err ->
                                println(err)
                                getView()?.hideProgress()
                            })
            )
        }
    }

    override fun onAttach(view: V?) {
        super.onAttach(view)
        getReferralCode()
        getBtcAddress()
        getUser()
    }

    private fun getBtcAddress() {
        val address: String? = interactor?.getBtcAddress()
        getView()?.btcAddressFound(address)
    }

    private fun getReferralCode() {
        val code: String? = interactor?.getCode()
        getView()?.referralCodeFound(code)
    }

    override fun logoutUser() {
        interactor?.performUserLogout()
    }

}