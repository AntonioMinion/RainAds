package com.rainads.rainadsapp.ui.initial.view

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.MobileAds
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.data.network.models.ResendEmailRequest
import com.rainads.rainadsapp.data.network.models.ResetPasswordRequest
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.initial.interactor.InitialMVPInteractor
import com.rainads.rainadsapp.ui.initial.presenter.InitialMVPPresenter
import com.rainads.rainadsapp.ui.main.view.MainActivity
import com.rainads.rainadsapp.ui.scanner.view.ScannerDialog
import com.rainads.rainadsapp.util.*
import com.rainads.rainadsapp.util.AppUtils.hideKeyboard
import kotlinx.android.synthetic.main.activity_initial.*
import kotlinx.android.synthetic.main.dialog_custom_referral_code.*
import kotlinx.android.synthetic.main.dialog_forgot_password.*
import kotlinx.android.synthetic.main.dialog_resend_email.*
import javax.inject.Inject


class InitialActivity : BaseActivity(), InitialMVPView, ScannerDialog.ScanFinishedListener,
        BaseActivity.PermissionRequestListener {

    @Inject
    internal lateinit var presenter: InitialMVPPresenter<InitialMVPView, InitialMVPInteractor>

    private var isSignUp: Boolean = true

    private lateinit var dialog: Dialog

    private fun initAdProviders() {
        MobileAds.initialize(
                this,
                "ca-app-pub-6953773192251170~7601411476"
        )
/*
        StartAppSDK.init(this, "207141024", true)
        StartAppSDK.setUserConsent (this,
                "pas",
                System.currentTimeMillis(),
                false)*/

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        initAdProviders()

        setPermissionRequestListener(this)

        window.enterTransition = null

        presenter.onAttach(this)

        spinner_countries.background.setColorFilter(
                ContextCompat.getColor(this, R.color.white),
                PorterDuff.Mode.SRC_ATOP
        )

        setOnClickListeners()
    }

    override fun onFragmentAttached() {
        //non implemented
    }

    override fun onFragmentDetached(tag: String) {
        //non implemented
    }

    override fun loadCountries(countries: List<Country>) {
        val countryNames: List<String> = countries.map { it.name }
        val adapter = ArrayAdapter(this, R.layout.item_display_spinner_light, countryNames)
        adapter.setDropDownViewResource(R.layout.item_spinner_custom)
        spinner_countries?.adapter = adapter
    }

    override fun showErrorMessage(errorCode: Int) {
        AppUtils.showMySnackBar(ll_container, Handler.getErrorMessage(errorCode), SnackBarType.ERROR)
    }

    override fun showMessage(type: ToastType, message: String, token: String) {
        if (type == ToastType.ERROR) {
            openEmailConfirmationDialog(token)
        } else {
            AppUtils.showMyToast(layoutInflater, this, message, type)
            if (type == ToastType.INFO) {
                clearFields()
                menu_login.callOnClick()
            }
        }
    }

    private fun openEmailConfirmationDialog(token: String) {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_resend_email)

        dialog.btnOk.setOnClickListener {
            dialog.dismiss()
        }

        dialog.btnResendEmail.setOnClickListener {
            dialog.dismiss()
            presenter.onResendEmail(ResendEmailRequest(token))
        }

        dialog.show()
    }

    private fun openForgotPasswordDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_forgot_password)

        dialog.btnSend.setOnClickListener {
            if (dialog.etEmail.text.toString().isEmpty())
                return@setOnClickListener
            presenter.onResetPassword(ResetPasswordRequest(dialog.etEmail.text.toString()))
            dialog.dismiss()
        }

        dialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun clearFields() {
        et_email.setText("")
        et_password.setText("")
        et_confirm_password.setText("")
    }

    override fun openMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun askForReferralCode() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_custom_referral_code)

        /*      dialog.btnSkip.setOnClickListener {
                  dialog.dismiss()
                  registerUser("")
              }
      */
        dialog.btnSave.setOnClickListener {
            dialog.dismiss()
            registerUser(dialog.etReferral.text.toString())
        }

        dialog.btnScanCode.setOnClickListener {
            openCameraToScan()
        }

        dialog.show()
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
        if (dialog.isShowing) {
            dialog.etReferral.setText(refCode)
        }
    }

    override fun onScanError(errorMsg: String) {
        AppUtils.showMyToast(layoutInflater, this, errorMsg, ToastType.ERROR)
    }

    private fun registerUser(referral: String) {
        presenter.onRegisterClicked(
                et_email.text.toString(),
                et_password.text.toString(),
                et_confirm_password.text.toString(),
                spinner_countries.selectedItem.toString(),
                referral
        )
    }

    override fun showProgress() {
        Log.d(this.localClassName, "Start Progress")
        menu_login.isEnabled = false
        menu_register.isEnabled = false
        progressBar.visibility = View.VISIBLE
        btnLoginOrSignUp.text = ""
        btnLoginOrSignUp.isEnabled = false
    }

    override fun hideProgress() {
        menu_login.isEnabled = true
        menu_register.isEnabled = true
        progressBar.visibility = View.GONE
        btnLoginOrSignUp.text = if (isSignUp) getString(R.string.sign_up) else getString(R.string.log_in)
        btnLoginOrSignUp.isEnabled = true
    }

    private fun setOnClickListeners() {

        tvForgotPassword.setOnClickListener {
            openForgotPasswordDialog()
        }

        menu_register.setOnClickListener {
            if (!isSignUp)
                menuClicked(menu_register, menu_login)
        }

        menu_login.setOnClickListener {
            if (isSignUp)
                menuClicked(menu_login, menu_register)
        }

        btnLoginOrSignUp.setOnClickListener {
            hideKeyboard()

            if (AppUtils.isConnected(this)) {
                if (isSignUp) {
                    if (cbTerms.isChecked && cbPrivacy.isChecked)
                        askForReferralCode()
                    else
                        AppUtils.showMySnackBar(
                                it,
                                Handler.getErrorMessage(MyConstants.ERROR_ACCEPT_TERMS_AND_PRIVACY),
                                SnackBarType.INFO
                        )
                } else {
                    presenter.onLoginClicked(
                            et_email.text.toString(),
                            et_password.text.toString()
                    )
                }
            } else {
                AppUtils.showMySnackBar(it, Handler.getErrorMessage(MyConstants.NETWORK_FAILURE), SnackBarType.NETWORK)
            }
        }

        ivOpenPrivacy.setOnClickListener {
            openTextDialog(false)
        }

        ivOpenTerms.setOnClickListener {
            openTextDialog(true)
        }
    }

    private fun openTextDialog(isTerms: Boolean) {

        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle(if (isTerms) getString(R.string.terms_of_use) else getString(R.string.privacy_policy))
            setMessage(if (isTerms) getText(R.string.terms_of_use_text) else getText(R.string.privacy_policy_text))
            setPositiveButton(
                    getString(R.string.close),
                    DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            show()
        }
    }

    private val positiveButtonClick = { dialog: DialogInterface, _: Int ->
        dialog.dismiss()
    }

    private fun menuClicked(selectedView: Button, deselectedView: Button) {

        hideKeyboard()

        isSignUp = !isSignUp

        btnLoginOrSignUp.text = if (isSignUp) getString(R.string.signup) else getString(R.string.login)

        et_confirm_password.visibility = if (et_confirm_password.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        spinner_countries.visibility = if (spinner_countries.visibility == View.VISIBLE) View.GONE else View.VISIBLE

        selectedView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        selectedView.setBackgroundResource(if (selectedView.id == menu_register.id) R.drawable.initial_menu_top_left_selected else R.drawable.initial_menu_top_right_selected)

        deselectedView.setTextColor(ContextCompat.getColor(this, R.color.white))
        deselectedView.setBackgroundResource(if (deselectedView.id == menu_register.id) R.drawable.initial_menu_top_left else R.drawable.initial_menu_top_right)

        llPrivacyPolicy.visibility = if (isSignUp) View.VISIBLE else View.GONE
        llTermsOfUse.visibility = if (isSignUp) View.VISIBLE else View.GONE
    }

}
