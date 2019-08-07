package com.rainads.rainadsapp.ui.addad.presenter

import com.rainads.rainadsapp.ui.addad.interactor.IAddAdInteractor
import com.rainads.rainadsapp.ui.addad.view.AddAdView
import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.util.Handler
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.SchedulerProvider
import com.rainads.rainadsapp.util.ToastType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class AddAdPresenter<V : AddAdView, I : IAddAdInteractor>
@Inject internal constructor(
        interactor: I,
        schedulerProvider: SchedulerProvider,
        disposable: CompositeDisposable
) : BasePresenter<V, I>(
        interactor = interactor,
        schedulerProvider = schedulerProvider,
        compositeDisposable = disposable
), IAddAdPresenter<V, I> {

    override fun onAttach(view: V?) {
        super.onAttach(view)
        getOptionsList()
    }

    private fun getOptionsList() {
        interactor?.let {
            it.getOptionsList()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe({ list ->
                        getView()?.let { it ->
                            it.hideProgress()
                            it.loadOptions(list)
                        }
                    }, { err ->
                        println(err)
                        getView()?.hideProgress()
                        getView()?.showErrorMessage(err)
                    })
        }
    }

    override fun saveAd(
            adUrl: String,
            adPrice: String,
            adDuration: String,
            adDescription: String,
            adCountries: List<String>
    ) {
        when {
            adUrl.isEmpty() -> getView()?.showMessage(
                    ToastType.ERROR,
                    Handler.getErrorMessage(MyConstants.EMPTY_AD_URL)
            )
            adPrice.isEmpty() -> getView()?.showMessage(
                    ToastType.ERROR,
                    Handler.getErrorMessage(MyConstants.EMPTY_AD_PRICE)
            )
            adDescription.isEmpty() -> getView()?.showMessage(
                    ToastType.ERROR,
                    Handler.getErrorMessage(MyConstants.EMPTY_AD_DESCRIPTION)
            )
            else -> {
                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(
                            it.createAdCall(adUrl, adPrice, adDuration, adDescription, adCountries)
                                    .compose(schedulerProvider.ioToMainObservableScheduler())
                                    .subscribe({ saveAdResponse ->
                                        getView()?.hideProgress()
                                        getView()?.showMessage(ToastType.SUCCESS, saveAdResponse)
                                    }, { err ->
                                        println(err)
                                        getView()?.hideProgress()
                                        getView()?.showMessage(ToastType.ERROR, Handler.getErrorMessage(0))
                                    })
                    )
                }

            }
        }
    }

}