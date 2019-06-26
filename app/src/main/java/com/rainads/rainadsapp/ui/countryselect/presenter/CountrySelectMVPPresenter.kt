package com.rainads.rainadsapp.ui.countryselect.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.countryselect.interactor.CountrySelectMVPInterator
import com.rainads.rainadsapp.ui.countryselect.view.CountrySelectDialogMVPView

interface CountrySelectMVPPresenter<V : CountrySelectDialogMVPView, I : CountrySelectMVPInterator> :
    MVPPresenter<V, I> {

    fun onLaterOptionClicked(): Unit?
    fun onSubmitOptionClicked(): Unit?
}