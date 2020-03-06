package com.rainads.rainadsapp.ui.transferpoints.interactor

import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

interface ITransferPointsInteractor: MVPInteractor {
    fun makeTransferPointsCall(receiverId: String, amount: String): Observable<String>
}