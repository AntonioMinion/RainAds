package com.rainads.rainadsapp.ui.base.presenter

import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import com.rainads.rainadsapp.ui.base.view.MVPView

interface MVPPresenter<V : MVPView, I : MVPInteractor> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?

}