package com.rainads.rainadsapp.ui.initial.presenter

import com.androidnetworking.error.ANError
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.initial.interactor.InitialMVPInteractor
import com.rainads.rainadsapp.ui.initial.view.InitialMVPView
import com.rainads.rainadsapp.util.MyConstants.CONFIRM_PASSWORD_ERROR
import com.rainads.rainadsapp.util.MyConstants.EMPTY_EMAIL_ERROR
import com.rainads.rainadsapp.util.MyConstants.EMPTY_PASSWORD_ERROR
import com.rainads.rainadsapp.util.MyConstants.INVALID_CREDENTIALS
import com.rainads.rainadsapp.util.SchedulerProvider
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
                .subscribe { countries ->
                    getView()?.let { it ->
                        it.hideProgress()
                        it.loadCountries(countries)
                    }
                }
        }
    }

    override fun onLoginClicked(email: String, password: String) {
        when {
            email.isEmpty() -> getView()?.showValidationMessage(EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showValidationMessage(EMPTY_PASSWORD_ERROR)
            else -> {
                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(
                        it.loginApiCall(email, password)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ loginResponse ->
                                updateUserInSharedPref(
                                    user = loginResponse,
                                    loggedIn = true
                                )
                                getView()?.hideProgress()
                                getView()?.openMainActivity()
                            }, { err ->
                                println(err)
                                getView()?.hideProgress()
                                getView()?.showValidationMessage(INVALID_CREDENTIALS)
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
            email.isEmpty() -> getView()?.showValidationMessage(EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showValidationMessage(EMPTY_PASSWORD_ERROR)
            !password.contentEquals(confirmPassword) -> getView()?.showValidationMessage(CONFIRM_PASSWORD_ERROR)
            else -> {
                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(
                        it.registerApiCall(email, password, country, referral)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ registerResponse ->
                                updateUserInSharedPref(
                                    user = registerResponse,
                                    loggedIn = true
                                )
                                getView()?.hideProgress()
                                getView()?.openMainActivity()
                            }, { err ->
                                println(err)
                                getView()?.hideProgress()
                                getView()?.showValidationMessage(if (err is ANError) err.errorCode else 0)
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