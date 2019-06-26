package com.rainads.rainadsapp.ui.initial.view

import android.app.Dialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.content.ContextCompat
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.initial.interactor.InitialMVPInteractor
import com.rainads.rainadsapp.ui.initial.presenter.InitialMVPPresenter
import com.rainads.rainadsapp.ui.main.view.MainActivity
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.AppUtils.hideKeyboard
import com.rainads.rainadsapp.util.Handler
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.SnackBarType
import kotlinx.android.synthetic.main.activity_initial.*
import kotlinx.android.synthetic.main.dialog_custom_referral_code.*
import javax.inject.Inject


class InitialActivity : BaseActivity(), InitialMVPView {

    @Inject
    internal lateinit var presenter: InitialMVPPresenter<InitialMVPView, InitialMVPInteractor>

    private var isSignUp: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        window.enterTransition = null

        presenter.onAttach(this)

        spinner_countries.background.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )

        setOnClickListeners()
    }

    override fun onFragmentAttached() {
        TODO("not implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("not implemented")
    }

    override fun loadCountries(countries: List<Country>) {
        val countryNames: List<String> = countries.map { it.name }
        val adapter = ArrayAdapter<String>(this, R.layout.item_display_spinner_light, countryNames)
        adapter.setDropDownViewResource(R.layout.item_spinner_custom)
        spinner_countries?.adapter = adapter
    }

    override fun showValidationMessage(errorCode: Int) {
        AppUtils.showMySnackBar(ll_container, Handler.getErrorMessage(errorCode), SnackBarType.ERROR)
    }

    override fun openMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun askForReferralCode() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_custom_referral_code)

        dialog.btnSkip.setOnClickListener {
            dialog.dismiss()
            registerUser("")
        }

        dialog.btnSave.setOnClickListener {
            dialog.dismiss()
            registerUser(dialog.etReferral.text.toString())
        }

        dialog.show()
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
                if (isSignUp)
                    askForReferralCode()
                else {
                    presenter.onLoginClicked(
                        et_email.text.toString(),
                        et_password.text.toString()
                    )
                }
            } else {
                AppUtils.showMySnackBar(it, Handler.getErrorMessage(MyConstants.NETWORK_FAILURE), SnackBarType.NETWORK)
            }
        }

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
    }

}
