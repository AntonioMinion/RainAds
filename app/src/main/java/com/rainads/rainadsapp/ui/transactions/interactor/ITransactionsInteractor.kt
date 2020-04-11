package com.rainads.rainadsapp.ui.transactions.interactor

import com.rainads.rainadsapp.data.network.models.TransactionData
import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

interface ITransactionsInteractor: MVPInteractor {
    fun getTransactionsCall(): Observable<TransactionData>
}