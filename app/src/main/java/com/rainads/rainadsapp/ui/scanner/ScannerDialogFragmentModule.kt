package com.rainads.rainadsapp.ui.scanner

import com.rainads.rainadsapp.ui.scanner.interactor.IScannerInteractor
import com.rainads.rainadsapp.ui.scanner.interactor.ScannerInteractor
import com.rainads.rainadsapp.ui.scanner.presenter.IScannerPresenter
import com.rainads.rainadsapp.ui.scanner.presenter.ScannerPresenter
import com.rainads.rainadsapp.ui.scanner.view.IScannerView
import dagger.Module
import dagger.Provides

@Module
class ScannerDialogFragmentModule {

    @Provides
    internal fun provideScannerInteractor(interactor: ScannerInteractor): IScannerInteractor =
            interactor

    @Provides
    internal fun provideScannerPresenter(presenter: ScannerPresenter<IScannerView, IScannerInteractor>)
            : IScannerPresenter<IScannerView, IScannerInteractor> = presenter
}