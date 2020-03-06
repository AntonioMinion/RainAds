package com.rainads.rainadsapp.ui.transferpoints

import com.rainads.rainadsapp.ui.transferpoints.interactor.ITransferPointsInteractor
import com.rainads.rainadsapp.ui.transferpoints.interactor.TransferPointsInteractor
import com.rainads.rainadsapp.ui.transferpoints.presenter.ITransferPointsPresenter
import com.rainads.rainadsapp.ui.transferpoints.presenter.TransferPointsPresenter
import com.rainads.rainadsapp.ui.transferpoints.view.TransferPointsView
import dagger.Module
import dagger.Provides

@Module
class TransferPointsModule {
    @Provides
    internal fun provideTransferPointsInteractor(interactor: TransferPointsInteractor): ITransferPointsInteractor = interactor

    @Provides
    internal fun provideTransferPointsPresenter(presenter: TransferPointsPresenter<TransferPointsView, ITransferPointsInteractor>)
            : ITransferPointsPresenter<TransferPointsView, ITransferPointsInteractor> = presenter
}