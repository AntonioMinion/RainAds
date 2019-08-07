package com.rainads.rainadsapp.ui.deposit

import com.rainads.rainadsapp.ui.deposit.interactor.DepositInteractor
import com.rainads.rainadsapp.ui.deposit.interactor.IDepositInteractor
import com.rainads.rainadsapp.ui.deposit.presenter.DepositPresenter
import com.rainads.rainadsapp.ui.deposit.presenter.IDepositPresenter
import com.rainads.rainadsapp.ui.deposit.view.DepositView
import dagger.Module
import dagger.Provides

@Module
class DepositModule {
    @Provides
    internal fun provideBalanceInteractor(interactor: DepositInteractor): IDepositInteractor = interactor

    @Provides
    internal fun provideBalancePresenter(presenter: DepositPresenter<DepositView, IDepositInteractor>)
            : IDepositPresenter<DepositView, IDepositInteractor> = presenter
}