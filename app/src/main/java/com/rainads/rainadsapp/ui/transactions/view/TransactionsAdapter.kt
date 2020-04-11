package com.rainads.rainadsapp.ui.transactions.view

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.CreatedDate
import com.rainads.rainadsapp.data.network.models.TransactionResponse
import com.rainads.rainadsapp.util.TransactionStatus
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.SimpleDateFormat
import java.util.*


class TransactionsAdapter(private val transactionItems: MutableList<TransactionResponse>) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    val linkToOpenUrl = "https://www.blockchain.com/btc/tx/"

    override fun getItemCount() = transactionItems.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) = holder.run {
        clear()
        onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
    )

    internal fun setList(transactionList: List<TransactionResponse>) {
        this.transactionItems.clear()
        this.transactionItems.addAll(transactionList)
        notifyDataSetChanged()
    }

    internal fun clearList() {
        this.transactionItems.clear()
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun clear() {
            itemView.tvTransactionStatus.text = ""
            itemView.tvTransactionAmount.text = ""
            itemView.tvTransactionDate.text = ""
            itemView.tvTransactionTxId.text = ""
        }

        fun onBind(position: Int) {
            val (id, userId, amount, address, type, status, createdDate, txId) = transactionItems[position]

            inflateData(status, amount, createdDate, txId)
        }

        private fun inflateData(
            transactionStatus: TransactionStatus?,
            amount: String?,
            date: CreatedDate?,
            txId: String?
        ) {

            amount?.let { itemView.tvTransactionAmount.text = it }

            date?.let {
                val sdf = SimpleDateFormat("HH:mm, dd MMM yyyy", Locale.getDefault())
                itemView.tvTransactionDate.text = sdf.format(Date(it.seconds * 1000))
            }

            if (txId.isNullOrEmpty())
                itemView.llTransactionTxIdContainer.visibility = View.GONE
            else {
                itemView.tvTransactionTxId.text = txId
                itemView.tvTransactionTxId.setOnClickListener { view ->
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(linkToOpenUrl + txId))
                    view.context.startActivity(browserIntent)
                }
            }

            transactionStatus?.let {

                itemView.tvTransactionStatus.text = it.text

                when (it) {
                    TransactionStatus.APPROVED -> {
                        itemView.tvTransactionStatus.setTextColor(Color.parseColor("#7cb342"))
                        itemView.tvTransactionStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.circle_green,
                            0,
                            0,
                            0
                        )
                    }
                    TransactionStatus.DECLINED -> {
                        itemView.tvTransactionStatus.setTextColor(Color.parseColor("#e53935"))
                        itemView.tvTransactionStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.circle_red,
                            0,
                            0,
                            0
                        )
                    }
                }
            }


        }
    }
}
