package com.rainads.rainadsapp.ui.scanner.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.scanner.interactor.IScannerInteractor
import com.rainads.rainadsapp.ui.scanner.view.IScannerView

interface IScannerPresenter<V : IScannerView, I : IScannerInteractor> :
        MVPPresenter<V, I>