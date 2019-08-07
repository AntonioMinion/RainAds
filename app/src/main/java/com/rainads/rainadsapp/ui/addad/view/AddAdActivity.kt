package com.rainads.rainadsapp.ui.addad.view

import android.os.Bundle
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.data.network.models.SatoshiResponse
import com.rainads.rainadsapp.ui.addad.interactor.IAddAdInteractor
import com.rainads.rainadsapp.ui.addad.presenter.AddAdPresenter
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.countryselect.view.CountrySelectDialog
import com.rainads.rainadsapp.util.*
import com.rainads.rainadsapp.util.adapter.CustomArrayAdapter
import kotlinx.android.synthetic.main.activity_add_ad.*
import kotlinx.android.synthetic.main.custom_back_button.*
import java.util.*
import javax.inject.Inject


class AddAdActivity : BaseActivity(), AddAdView, CountrySelectDialog.CountriesSavedListener {

    @Inject
    internal lateinit var presenter: AddAdPresenter<AddAdView, IAddAdInteractor>

    private val selectedCountries = ArrayList<Country>()

    override fun onFragmentAttached() {
        //non implemented
    }

    override fun onFragmentDetached(tag: String) {
        //non implemented
    }

    override fun showMessage(type: ToastType, message: String) {
        AppUtils.showMyToast(layoutInflater, this, message, type)
        if (type == ToastType.SUCCESS)
            finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ad)

        presenter.onAttach(this)

        setOnClickListeners()
    }

    override fun loadOptions(options: List<SatoshiResponse>) {
        setupPriceDurationSpinner(options)
    }

    private fun setOnClickListeners() {
        btnBack.setOnClickListener { onBackPressed() }

        btn_save_ad.setOnClickListener {
            if (AppUtils.isConnected(this))
                saveAd()
            else
                AppUtils.showMySnackBar(it, Handler.getErrorMessage(MyConstants.NETWORK_FAILURE), SnackBarType.NETWORK)
        }

        tv_ad_countries.setOnClickListener {
            CountrySelectDialog.newInstance().let {
                it?.show(supportFragmentManager)
                it?.setPreselectedCountries(selectedCountries)
                it?.setListener(this)
            }
        }
    }

    override fun onCountriesSaved(countries: List<Country>) {
        selectedCountries.clear()
        selectedCountries.addAll(countries)
        tv_ad_countries.text = AppUtils.listToString(selectedCountries.map { it.name })

        if (tv_ad_countries.text.toString().isEmpty())
            tv_ad_countries.text = getString(R.string.countries_all)
    }

    private fun saveAd() {
        val adUrl = et_ad_url.text.toString()
        val adDuration = (spinner_ad_price_duration.selectedItem as SatoshiResponse).duration.toString()
        val adPrice = (spinner_ad_price_duration.selectedItem as SatoshiResponse).satoshi.toString()
        val adDescription = et_ad_description.text.toString()

        presenter.saveAd(adUrl, adPrice, adDuration, adDescription, selectedCountries.map { it.name })
    }

    private fun setupPriceDurationSpinner(options: List<SatoshiResponse>) {
        val priceDurationAdapter =
                CustomArrayAdapter(this, R.layout.item_display_spinner_light, R.id.tv_spinner_text, options)

        priceDurationAdapter.dropdownLayoutResource = R.layout.item_spinner_custom
        priceDurationAdapter.dropdownTextResource = R.id.tv_spinner_dropdown_text

        spinner_ad_price_duration?.adapter = priceDurationAdapter
    }

}


