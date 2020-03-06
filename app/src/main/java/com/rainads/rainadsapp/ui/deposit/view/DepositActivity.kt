package com.rainads.rainadsapp.ui.deposit.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.deposit.interactor.IDepositInteractor
import com.rainads.rainadsapp.ui.deposit.presenter.DepositPresenter
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.ToastType
import kotlinx.android.synthetic.main.activity_deposit.*
import kotlinx.android.synthetic.main.custom_back_button.*
import javax.inject.Inject


class DepositActivity : BaseActivity(), DepositView {


    @Inject
    internal lateinit var presenter: DepositPresenter<DepositView, IDepositInteractor>

    //private var actualBalance = 0f
    private var rainPointsBalance: Double = 0.0
    private var btcBalance: Double = 0.0
    private var isDepositing = true

    override fun onFragmentAttached() {
        //non implemented
    }

    override fun onFragmentDetached(tag: String) {
        //non implemented
    }

    override fun showMessage(type: ToastType, message: String) {
        AppUtils.showMyToast(layoutInflater, this, message, type)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit)

        presenter.onAttach(this)
        presenter.getUser()

        setListeners()

        handleExtras()
    }

    private fun handleExtras() {
        val bundle = intent.extras
        if (bundle != null) {
            isDepositing = bundle.getBoolean(MyConstants.EXTRA_IS_DEPOSIT, true)
            if (!isDepositing) {
                tvTransactionsTitle.text = getString(R.string.withdraw)

                llBtcBalance.visibility = View.GONE
                ivTransferDirection.visibility = View.GONE
                tvBtcAddressLabel.visibility = View.GONE
                tvBalanceBtcAddress.visibility = View.GONE
                tilBtcAddress.visibility = View.VISIBLE
                tvWithdrawMinimumAmount.visibility = View.VISIBLE
                tvWithdrawNote.visibility = View.VISIBLE

                btnSendDepositRequest.text = getString(R.string.send_withdraw_request)

                tvOpenWithdrawalHistory.visibility = View.VISIBLE
            }
        }
    }

    override fun userDownloaded(user: User) {
        rainPointsBalance = if (user.balance.isNullOrEmpty()) 0.0 else user.balance!!.toDouble()
        btcBalance = if (user.btcBalance.isNullOrEmpty()) 0.0 else user.btcBalance!!.toDouble()

        tvBalanceBtcAddress.text = if (user.btcAddress.isNullOrEmpty()) "/" else user.btcAddress

        tvDepositRainAdsBalancePoints.text = String.format("%.2f", rainPointsBalance)

        tvDepositPointsBalance.text = btcBalance.times(100000000).toString()

        if (((isDepositing && btcBalance == 0.0) || user.btcAddress.isNullOrEmpty())
            || (!isDepositing && rainPointsBalance < 10000)
        ) {
            tilBtcAddress.alpha = 0.5f
            etBtcAddress.isEnabled = false
            tilDepositAmount.alpha = 0.5f
            etDepositAmount.isEnabled = false
            disableSendButton()
        }
    }

    private fun disableSendButton() {
        btnSendDepositRequest.isEnabled = false
        btnSendDepositRequest.alpha = 0.3f
    }

    private fun enableSendButton() {
        btnSendDepositRequest.isEnabled = true
        btnSendDepositRequest.alpha = 1f
    }

    private fun setListeners() {
        btnBack.setOnClickListener { onBackPressed() }

        btnSendDepositRequest.setOnClickListener {
            if (isDepositing) {
                if (!etDepositAmount.text.isNullOrEmpty()
                    || etDepositAmount.text.toString().toFloat() <= rainPointsBalance
                )
                    presenter.depositRequest(etDepositAmount.text.toString())
            } else {
                if (!etDepositAmount.text.isNullOrEmpty() && etDepositAmount.text.toString().toFloat() <= rainPointsBalance)
                    presenter.withdrawRequest(
                        etBtcAddress.text.toString(),
                        etDepositAmount.text.toString()
                    )
            }
        }

        etDepositAmount.doOnTextChanged { _, _, _, _ ->
            if (isDepositing) {
                if (etDepositAmount.text.isNullOrEmpty()
                    || etDepositAmount.text.toString().toFloat() > rainPointsBalance
                )
                    disableSendButton()
                else
                    enableSendButton()
            } else {
                toggleFieldBasedOnInput()
            }

        }

        etBtcAddress.doOnTextChanged { _, _, _, _ ->
            toggleFieldBasedOnInput()
        }

        tvOpenWithdrawalHistory.setOnClickListener {
            //not implemented
            AppUtils.showMyToast(layoutInflater, this, "Coming soon!", ToastType.INFO)
        }

    }

    private fun toggleFieldBasedOnInput() {
        if (isAmountCorrect() && isAddressCorrect())
            enableSendButton()
        else
            disableSendButton()
    }

    private fun isAmountCorrect(): Boolean {
        return !(etDepositAmount.text.isNullOrEmpty()
                || etDepositAmount.text.toString().toFloat() > rainPointsBalance
                || etDepositAmount.text.toString().toFloat() < 10000)
    }

    private fun isAddressCorrect(): Boolean {
        return etBtcAddress.text.toString().isNotEmpty()
    }

}


