package com.rainads.rainadsapp.ui.transferpoints.view

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.scanner.view.ScannerDialog
import com.rainads.rainadsapp.ui.transferpoints.interactor.ITransferPointsInteractor
import com.rainads.rainadsapp.ui.transferpoints.presenter.TransferPointsPresenter
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.AppUtils.hideKeyboard
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.ToastType
import kotlinx.android.synthetic.main.activity_transfer_points.*
import kotlinx.android.synthetic.main.custom_back_button.*
import javax.inject.Inject

class TransferPointsActivity : BaseActivity(), TransferPointsView,
    ScannerDialog.ScanFinishedListener,
    BaseActivity.PermissionRequestListener {

    var needToConfirm = true
    var availablePoints: Double? = 0.0

    @Inject
    internal lateinit var presenter: TransferPointsPresenter<TransferPointsView, ITransferPointsInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_points)

        presenter.onAttach(this)

        btnTransferFunds.alpha = 0.5f
        btnTransferFunds.isEnabled = false

        btnTransferFunds.setOnClickListener {
            if (needToConfirm) {
                needToConfirm = false
                tvConfirmTransfer.visibility = View.VISIBLE
                btnTransferFunds.setImageResource(R.drawable.ic_check)
            } else {
                if (!etReceiverId.text.isNullOrBlank() && !etTransferAmount.text.isNullOrBlank())
                    presenter.sendFunds(
                        etReceiverId.text.toString(),
                        etTransferAmount.text.toString()
                    )
            }

        }

        etTransferAmount.doOnTextChanged { _, _, _, _ ->
            when {
                etTransferAmount.text.isNullOrBlank() -> disableSendButton()
                etTransferAmount.text.toString().toDouble() > availablePoints!! -> disableSendButton()
                etReceiverId.text.isNullOrBlank() -> disableSendButton()
                else -> enableSendButton()
            }
        }

        etReceiverId.doOnTextChanged { _, _, _, _ ->
            when {
                etReceiverId.text.isNullOrBlank() -> disableSendButton()
                etTransferAmount.text.isNullOrBlank() -> disableSendButton()
                etTransferAmount.text.toString().toDouble() > availablePoints!! -> disableSendButton()
                else -> enableSendButton()
            }
        }

        ivScanQr.setOnClickListener {
            openCameraToScan()
        }

        btnBack.setOnClickListener {
            finish()
        }

        rlTransferPointsMainContainer.setOnClickListener {
            hideKeyboard()
        }

        setPermissionRequestListener(this)

        handleExtras()

    }

    private fun enableSendButton() {
        btnTransferFunds.alpha = 1f
        btnTransferFunds.isEnabled = true
    }

    private fun disableSendButton() {
        btnTransferFunds.alpha = 0.5f
        btnTransferFunds.isEnabled = false
    }

    private fun handleExtras() {
        val bundle = intent.extras
        if (bundle != null) {
            availablePoints = bundle.getDouble(MyConstants.EXTRA_POINTS, -1.0)
            if (availablePoints == -1.0)
                presenter.getUser()
            else {
                tvAmountAvailable.text = availablePoints.toString()
                disableInputIfNoFunds()
            }
        }
    }

    private fun disableInputIfNoFunds() {
        if (availablePoints == 0.0) {
            etTransferAmount.isEnabled = false
            etTransferAmount.alpha = 0.4f
            etReceiverId.isEnabled = false
            etReceiverId.alpha = 0.4f
        }
    }

    private fun openCameraToScan() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            ScannerDialog.newInstance().let {
                it?.show(supportFragmentManager)
                it?.setListener(this)
            }
        } else {
            requestPermission(Manifest.permission.CAMERA, MyConstants.CAMERA_REQUEST_CODE)
        }
    }

    override fun onPermissionGranted() {
        openCameraToScan()
    }


    override fun onScanSuccessful(refCode: String) {
        etReceiverId.setText(refCode)
    }

    override fun onScanError(errorMsg: String) {
        AppUtils.showMyToast(layoutInflater, this, errorMsg, ToastType.ERROR)
    }

    override fun onFragmentAttached() {
        //not implemented
    }

    override fun onFragmentDetached(tag: String) {
        //not implemented
    }

    override fun sendFundsCallback(toastType: ToastType, message: String) {
        AppUtils.showMyToast(layoutInflater, this, message, toastType)
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun userDownloaded(user: User) {
        // set user params
        availablePoints = user.balance?.toDouble()
        tvAmountAvailable.text = availablePoints?.toString()
        disableInputIfNoFunds()
    }
}
