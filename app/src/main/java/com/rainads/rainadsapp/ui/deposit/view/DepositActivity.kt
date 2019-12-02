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

    private var actualBalance = 0f
    private var rainAdsBalance = 0f
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

        getExtras()
    }

    private fun getExtras() {
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
            }
        }
    }

    override fun userDownloaded(user: User) {
        rainAdsBalance = if (user.balance.isNullOrEmpty()) 0f else user.balance!!.toFloat()

        tvBalanceBtcAddress.text = if (user.btcAddress.isNullOrEmpty()) "/" else user.btcAddress

        tvDepositRainAdsBalancePoints.text = if (user.balance.isNullOrEmpty()) "0" else user.balance

        tvDepositPointsBalance.text = "0"

        if (!user.btcBalance.isNullOrEmpty()) {
            actualBalance = user.btcBalance?.toFloat()?.times(100000000)!!
        }

        if (user.btcBalance.isNullOrEmpty() || actualBalance == 0f) {
            disableDepositButton()
        } else {
            tvDepositPointsBalance.text = actualBalance.toString()
        }

        /*
             tvDepositBtcBalance.text = if (user.btcBalance.isNullOrEmpty()) "0" else user.btcBalance
             tvDepositRainAdsBalance.text =
                     if (user.balance.isNullOrEmpty()) "0"
                     else user.balance?.toFloat()!!.div(100000000).toString()
     */

    }

    private fun disableDepositButton() {
        btnSendDepositRequest.isEnabled = false
        btnSendDepositRequest.alpha = 0.3f
    }

    private fun enableDepositButton() {
        btnSendDepositRequest.isEnabled = true
        btnSendDepositRequest.alpha = 1f
    }

    private fun setListeners() {
        btnBack.setOnClickListener { onBackPressed() }

        btnSendDepositRequest.setOnClickListener {
            if (isDepositing) {
                if (!etDepositAmount.text.isNullOrEmpty()
                        || etDepositAmount.text.toString().toFloat() <= actualBalance)
                    presenter.depositRequest(etDepositAmount.text.toString())
            } else {
                if (!etDepositAmount.text.isNullOrEmpty()
                        || etDepositAmount.text.toString().toFloat() <= rainAdsBalance)
                    presenter.withdrawRequest(etBtcAddress.text.toString(), etDepositAmount.text.toString())
            }
        }

        etDepositAmount.doOnTextChanged { _, _, _, _ ->
            if (isDepositing) {
                if (etDepositAmount.text.isNullOrEmpty()
                        || etDepositAmount.text.toString().toFloat() > actualBalance)
                    disableDepositButton()
                else
                    enableDepositButton()
            } else {
                toggleFieldBasedOnInput()
            }

        }

        etBtcAddress.doOnTextChanged { _, _, _, _ ->
            toggleFieldBasedOnInput()
        }

    }

    private fun toggleFieldBasedOnInput(){
        if (isAmountCorrect() && isAddressCorrect())
            enableDepositButton()
        else
            disableDepositButton()
    }

    private fun isAmountCorrect(): Boolean{
        return !(etDepositAmount.text.isNullOrEmpty()
                || etDepositAmount.text.toString().toFloat() > rainAdsBalance
                || etDepositAmount.text.toString().toFloat()  < 10000)
    }

    private fun isAddressCorrect(): Boolean{
        return !etBtcAddress.text.toString().isEmpty()
    }

}


