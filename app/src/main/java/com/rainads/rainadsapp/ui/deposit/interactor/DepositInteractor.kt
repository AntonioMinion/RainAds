package com.rainads.rainadsapp.ui.deposit.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.DepositRequest
import com.rainads.rainadsapp.data.network.models.WithdrawRequest
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import com.rainads.rainadsapp.util.TransactionType
import javax.inject.Inject

class DepositInteractor @Inject internal constructor(
        private val mContext: Context,
        preferenceHelper: PreferenceHelper,
        apiCalls: ApiCalls
) :
        BaseInteractor(preferenceHelper, apiCalls),
        IDepositInteractor {

    override fun downloadUser() = apiCalls.getUser(preferenceHelper.getAccessToken()!!)

    override fun sendDepositRequest(amount: String) = apiCalls.sendDepositRequest(DepositRequest(preferenceHelper.getAccessToken()!!
            , amount
            , TransactionType.DEPOSIT))

    override fun sendWithdrawRequest(btcAddress: String, amount: String) = apiCalls.sendWithdrawRequest(WithdrawRequest(preferenceHelper.getAccessToken()!!
            , amount
            , TransactionType.WITHDRAW
            , btcAddress))
}