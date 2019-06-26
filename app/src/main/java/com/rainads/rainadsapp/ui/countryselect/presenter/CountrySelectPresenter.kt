package com.rainads.rainadsapp.ui.countryselect.presenter

import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.countryselect.interactor.CountrySelectMVPInterator
import com.rainads.rainadsapp.ui.countryselect.view.CountrySelectDialogMVPView
import com.rainads.rainadsapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CountrySelectPresenter<V : CountrySelectDialogMVPView, I : CountrySelectMVPInterator> @Inject internal constructor(
    interator: I,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interator,
    schedulerProvider = schedulerProvider,
    compositeDisposable = compositeDisposable
), CountrySelectMVPPresenter<V, I> {

    override fun onLaterOptionClicked(): Unit? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubmitOptionClicked(): Unit? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAttach(view: V?) {
        super.onAttach(view)
        feedInCountries()
    }

    private fun feedInCountries() {
        interactor?.let {
            it.getCountries()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe { countries ->
                    getView()?.let { it ->
                        it.hideProgress()
                        it.loadCountries(countries)
                    }
                }
        }
    }
}
