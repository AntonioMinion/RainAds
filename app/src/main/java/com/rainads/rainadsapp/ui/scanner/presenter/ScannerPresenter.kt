package com.rainads.rainadsapp.ui.scanner.presenter

import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.scanner.interactor.IScannerInteractor
import com.rainads.rainadsapp.ui.scanner.view.IScannerView
import com.rainads.rainadsapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ScannerPresenter<V : IScannerView, I : IScannerInteractor> @Inject internal constructor(
        interator: I,
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable
) : BasePresenter<V, I>(
        interactor = interator,
        schedulerProvider = schedulerProvider,
        compositeDisposable = compositeDisposable
), IScannerPresenter<V, I>
