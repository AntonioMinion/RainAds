package com.rainads.rainadsapp.ui.transactions.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.TransactionResponse
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.transactions.interactor.ITransactionsInteractor
import com.rainads.rainadsapp.ui.transactions.presenter.TransactionsPresenter
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.TransactionType
import kotlinx.android.synthetic.main.activity_my_ad_list.recyclerViewMyAds
import kotlinx.android.synthetic.main.activity_transactions.*
import kotlinx.android.synthetic.main.custom_back_button.*
import javax.inject.Inject

class TransactionsActivity : BaseActivity(), TransactionsView {

    @Inject
    internal lateinit var transactionsAdapter: TransactionsAdapter
    @Inject
    internal lateinit var layoutManager: LinearLayoutManager
    @Inject
    internal lateinit var presenter: TransactionsPresenter<TransactionsView, ITransactionsInteractor>

    var transactionType = TransactionType.DEPOSIT

    override fun onFragmentAttached() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentDetached(tag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)
        presenter.onAttach(this)

        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewMyAds.layoutManager = layoutManager
        recyclerViewMyAds.itemAnimator = null
        recyclerViewMyAds.adapter = transactionsAdapter

        btnBack.setOnClickListener {
            onBackPressed()
        }

        handleExtras()

        presenter.downloadTransactions(transactionType)

        swipeContainerTransactions.setOnRefreshListener {
            presenter.downloadTransactions(transactionType)
        }

    }

    private fun handleExtras() {
        val bundle = intent.extras
        if (bundle != null) {
            val isDepositing = bundle.getBoolean(MyConstants.EXTRA_IS_DEPOSIT, false)

            if (isDepositing) {
                transactionType = TransactionType.DEPOSIT
                tvTransactionsTitle.text = getString(R.string.deposit_history)
            } else {
                transactionType = TransactionType.WITHDRAW
                tvTransactionsTitle.text = getString(R.string.withdrawal_history)
            }
        }
    }

    override fun transactionsDownloaded(transactions: List<TransactionResponse>) {
        swipeContainerTransactions.isRefreshing = false

        if (!transactions.isNullOrEmpty()) {
            tvNoTransactions.visibility = View.GONE
            transactionsAdapter.setList(transactions)
        } else {
            tvNoTransactions.visibility = View.VISIBLE
        }
    }


}
