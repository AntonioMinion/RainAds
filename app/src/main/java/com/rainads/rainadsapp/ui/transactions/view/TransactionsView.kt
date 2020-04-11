package com.rainads.rainadsapp.ui.transactions.view

import com.rainads.rainadsapp.data.network.models.TransactionData
import com.rainads.rainadsapp.data.network.models.TransactionResponse
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.MVPView
import com.rainads.rainadsapp.util.ToastType

interface TransactionsView : MVPView {
    fun transactionsDownloaded(transactions: List<TransactionResponse>)
}