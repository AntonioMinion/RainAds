package com.rainads.rainadsapp.ui.countryselect.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.ui.base.view.BaseDialogView
import com.rainads.rainadsapp.ui.countryselect.interactor.CountrySelectMVPInterator
import com.rainads.rainadsapp.ui.countryselect.presenter.CountrySelectMVPPresenter
import com.rainads.rainadsapp.util.AppUtils
import kotlinx.android.synthetic.main.fragment_dialog_countries.*
import java.util.*
import javax.inject.Inject


class CountrySelectDialog : BaseDialogView(), CountrySelectDialogMVPView, CountrySelectAdapter.SelectedCountryListener {

    override fun showErrorMessage(error: Throwable) {
        //not implemented
    }

    private val TAG = "CountrySelectDialog"

    @Inject
    internal lateinit var adapter: CountrySelectAdapter

    @Inject
    internal lateinit var presenter: CountrySelectMVPPresenter<CountrySelectDialogMVPView, CountrySelectMVPInterator>

    @Inject
    internal lateinit var layoutManager: LinearLayoutManager

    private val selectedCountries = ArrayList<Country>()

    private lateinit var savedCountriesListener: CountriesSavedListener

    interface CountriesSavedListener {
        fun onCountriesSaved(countries: List<Country>)
    }

    companion object {
        fun newInstance(): CountrySelectDialog? {
            return CountrySelectDialog()
        }
    }

    override fun loadCountries(countries: List<Country>) {
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewCountries.layoutManager = layoutManager
        recyclerViewCountries.itemAnimator = null
        recyclerViewCountries.adapter = adapter
        adapter.setSelectedCountryCallbackListener(this)
        adapter.addToList(countries)
        adapter.checkCountries(selectedCountries)
        updateText()
    }

    override fun onCountryChanged(country: Country) {
        if (country.isSelected)
            selectedCountries.add(country)
        else {
            selectedCountries.filter { it.name == country.name }.forEach {
                selectedCountries.remove(it)
            }
        }

        updateText()
    }

    private fun updateText() {
        tv_selected_countries.text = AppUtils.listToString(selectedCountries.map { it.name })

        if (tv_selected_countries.text.isEmpty())
            tv_selected_countries.visibility = View.GONE
        else
            tv_selected_countries.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_dialog_countries, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)

        setListeners()
    }

    private fun setListeners() {
        ivClose.setOnClickListener { dismissDialog() }

        btnClearCountries.setOnClickListener {
            selectedCountries.clear()
            tv_selected_countries.text = ""
            tv_selected_countries.visibility = View.GONE
            adapter.clearChecked()
        }

        btnSelectAllCountries.setOnClickListener {
            tv_selected_countries.visibility = View.VISIBLE
            tv_selected_countries.text = getString(R.string.all)
            adapter.selectAll()
        }
        btnSaveCountries.setOnClickListener {
            savedCountriesListener.onCountriesSaved(selectedCountries)
            dismissDialog()
        }

        etSearchCountry.doOnTextChanged { text, start, count, after -> adapter.search(etSearchCountry.text.toString()) }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun dismissDialog() = super.dismissDialog(TAG)

    internal fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    internal fun setListener(listener: CountriesSavedListener) {
        this.savedCountriesListener = listener
    }

    internal fun setPreselectedCountries(preselectedList: List<Country>) {
        this.selectedCountries.addAll(preselectedList)
    }
}