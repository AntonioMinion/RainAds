package com.rainads.rainadsapp.ui.initial.presenter

import com.androidnetworking.error.ANError
import com.rainads.rainadsapp.data.network.models.ResendEmailRequest
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.initial.interactor.InitialMVPInteractor
import com.rainads.rainadsapp.ui.initial.view.InitialMVPView
import com.rainads.rainadsapp.util.MyConstants.CONFIRM_PASSWORD_ERROR
import com.rainads.rainadsapp.util.MyConstants.EMPTY_EMAIL_ERROR
import com.rainads.rainadsapp.util.MyConstants.EMPTY_PASSWORD_ERROR
import com.rainads.rainadsapp.util.MyConstants.INVALID_CREDENTIALS
import com.rainads.rainadsapp.util.SchedulerProvider
import com.rainads.rainadsapp.util.ToastType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class InitialPresenter<V : InitialMVPView, I : InitialMVPInteractor> @Inject internal constructor(
        interactor: I,
        schedulerProvider: SchedulerProvider,
        disposable: CompositeDisposable
) : BasePresenter<V, I>(
        interactor = interactor,
        schedulerProvider = schedulerProvider,
        compositeDisposable = disposable
), InitialMVPPresenter<V, I> {


    override fun onAttach(view: V?) {
        super.onAttach(view)
        feedInCountries()
    }

    private fun feedInCountries() {
        interactor?.let {
            it.getCountries()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe({ countries ->
                        getView()?.let { it ->
                            it.hideProgress()
                            it.loadCountries(countries)
                        }
                    }, { err ->
                        println(err)
                        getView()?.hideProgress()
                        getView()?.showErrorMessage(err)
                    })
        }
    }

    override fun onResendEmail(request: ResendEmailRequest) {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(
                    it.resendEmail(request)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ response ->
                                getView()?.hideProgress()
                                getView()?.showMessage(ToastType.SUCCESS, response, "")
                            }, { err ->
                                println(err)
                                getView()?.hideProgress()
                                getView()?.showErrorMessage(INVALID_CREDENTIALS)
                            })
            )
        }
    }

    override fun onLoginClicked(email: String, password: String) {
        when {
            email.isEmpty() -> getView()?.showErrorMessage(EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showErrorMessage(EMPTY_PASSWORD_ERROR)
            else -> {
                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(
                            it.loginApiCall(email, password)
                                    .compose(schedulerProvider.ioToMainObservableScheduler())
                                    .subscribe({ loginResponse ->
                                        if (loginResponse.userData == null) {
                                            getView()?.hideProgress()
                                            getView()?.showMessage(ToastType.ERROR, loginResponse.message, loginResponse.idToken)
                                        } else {
                                            updateUserInSharedPref(
                                                    user = loginResponse.userData!!,
                                                    loggedIn = true
                                            )
                                            getView()?.hideProgress()
                                            getView()?.openMainActivity()
                                        }
                                    }, { err ->
                                        println(err)
                                        getView()?.hideProgress()
                                        getView()?.showErrorMessage(INVALID_CREDENTIALS)
                                    })
                    )
                }

            }
        }
    }

    override fun onRegisterClicked(
            email: String,
            password: String,
            confirmPassword: String,
            country: String,
            referral: String
    ) {
        when {
            email.isEmpty() -> getView()?.showErrorMessage(EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showErrorMessage(EMPTY_PASSWORD_ERROR)
            !password.contentEquals(confirmPassword) -> getView()?.showErrorMessage(CONFIRM_PASSWORD_ERROR)
            else -> {
                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(
                            it.registerApiCall(email, password, country, referral)
                                    .compose(schedulerProvider.ioToMainObservableScheduler())
                                    .subscribe({ registerResponse ->
                                        getView()?.hideProgress()
                                        getView()?.showMessage(ToastType.INFO, registerResponse, "")
                                    }, { err ->
                                        println(err)
                                        getView()?.hideProgress()
                                        getView()?.showErrorMessage(if (err is ANError) err.errorCode else 0)
                                    })
                    )
                }

            }
        }
    }


    private fun updateUserInSharedPref(
            user: User,
            loggedIn: Boolean
    ) =
            interactor?.updateUserInSharedPref(user, loggedIn)


}