package com.rainads.rainadsapp.ui.main

import com.rainads.rainadsapp.ui.main.interactor.MainInteractor
import com.rainads.rainadsapp.ui.main.interactor.MainMVPInteractor
import com.rainads.rainadsapp.ui.main.presenter.MainMVPPresenter
import com.rainads.rainadsapp.ui.main.presenter.MainPresenter
import com.rainads.rainadsapp.ui.main.view.MainMVPView
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMainInteractor(interactor: MainInteractor): MainMVPInteractor = interactor

    @Provides
    internal fun provideMainPresenter(presenter: MainPresenter<MainMVPView, MainMVPInteractor>)
            : MainMVPPresenter<MainMVPView, MainMVPInteractor> = presenter

/*    @Provides
    internal fun provideClipboardManager(clipboardManager: ClipboardManager)
            : ClipboardManager = clipboardManager*/

}