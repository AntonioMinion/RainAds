package com.rainads.rainadsapp.ui.addad

import com.rainads.rainadsapp.ui.addad.interactor.AddAdInteractor
import com.rainads.rainadsapp.ui.addad.interactor.IAddAdInteractor
import com.rainads.rainadsapp.ui.addad.presenter.AddAdPresenter
import com.rainads.rainadsapp.ui.addad.presenter.IAddAdPresenter
import com.rainads.rainadsapp.ui.addad.view.AddAdView
import dagger.Module
import dagger.Provides

@Module
class AddAdModule {
    @Provides
    internal fun provideAddAdInteractor(interactor: AddAdInteractor): IAddAdInteractor = interactor

    @Provides
    internal fun provideAddAdPresenter(presenter: AddAdPresenter<AddAdView, IAddAdInteractor>)
            : IAddAdPresenter<AddAdView, IAddAdInteractor> = presenter
}