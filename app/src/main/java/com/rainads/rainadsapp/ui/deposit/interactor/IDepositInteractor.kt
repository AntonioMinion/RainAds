package com.rainads.rainadsapp.ui.deposit.interactor

import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable


interface IDepositInteractor : MVPInteractor {
    fun downloadUser(): Observable<User>
    fun sendDepositRequest(amount: String): Observable<String>
    fun sendWithdrawRequest(btcAddress: String, amount: String): Observable<String>

}