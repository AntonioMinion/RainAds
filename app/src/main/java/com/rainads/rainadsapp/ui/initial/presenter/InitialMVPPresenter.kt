package com.rainads.rainadsapp.ui.initial.presenter

import com.rainads.rainadsapp.data.network.models.ResendEmailRequest
import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.initial.interactor.InitialMVPInteractor
import com.rainads.rainadsapp.ui.initial.view.InitialMVPView

interface InitialMVPPresenter<V : InitialMVPView, I : InitialMVPInteractor> : MVPPresenter<V, I> {
    fun onLoginClicked(email: String, password: String)
    fun onRegisterClicked(email: String, password: String, confirmPassword: String, country: String, referral: String)
    fun onResendEmail(request: ResendEmailRequest)
}