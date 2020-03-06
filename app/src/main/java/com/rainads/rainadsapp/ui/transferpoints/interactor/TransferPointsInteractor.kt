package com.rainads.rainadsapp.ui.transferpoints.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.TransferPointsRequest
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class TransferPointsInteractor @Inject internal constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) :
    BaseInteractor(preferenceHelper, apiCalls),
    ITransferPointsInteractor {

    override fun makeTransferPointsCall(receiverId: String, amount: String) = apiCalls.transferPoints(
        TransferPointsRequest(amount, receiverId)
    )

}