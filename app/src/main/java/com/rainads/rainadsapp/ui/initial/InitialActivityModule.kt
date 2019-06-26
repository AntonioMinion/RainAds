package com.rainads.rainadsapp.ui.initial

import com.rainads.rainadsapp.ui.initial.interactor.InitialInteractor
import com.rainads.rainadsapp.ui.initial.interactor.InitialMVPInteractor
import com.rainads.rainadsapp.ui.initial.presenter.InitialMVPPresenter
import com.rainads.rainadsapp.ui.initial.presenter.InitialPresenter
import com.rainads.rainadsapp.ui.initial.view.InitialMVPView
import dagger.Module
import dagger.Provides

@Module
class InitialActivityModule {

    @Provides
    internal fun provideInitialInteractor(interactor: InitialInteractor): InitialMVPInteractor = interactor

    @Provides
    internal fun provideInitialPresenter(presenter: InitialPresenter<InitialMVPView, InitialMVPInteractor>)
            : InitialMVPPresenter<InitialMVPView, InitialMVPInteractor> = presenter

}