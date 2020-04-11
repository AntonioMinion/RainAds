package com.rainads.rainadsapp.ui.transactions.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.TransactionData
import com.rainads.rainadsapp.data.network.models.TransferPointsRequest
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import io.reactivex.Observable
import javax.inject.Inject

class TransactionsInteractor @Inject internal constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) :
    BaseInteractor(preferenceHelper, apiCalls),
    ITransactionsInteractor {

    override fun getTransactionsCall(): Observable<TransactionData> = apiCalls.getTransactions()

}