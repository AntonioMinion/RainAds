package com.rainads.rainadsapp.ui.countryselect.view

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rainads.rainadsapp.data.network.models.Country
import kotlinx.android.synthetic.main.item_spinner_custom.view.*


class CountrySelectAdapter(private val list: MutableList<Country>) :
    RecyclerView.Adapter<CountrySelectAdapter.ViewHolder>() {

    var filteredList = ArrayList<Country>()

    interface SelectedCountryListener {
        fun onCountryChanged(country: Country)
    }

    private lateinit var selectedCountryListener: SelectedCountryListener

    internal fun setSelectedCountryCallbackListener(listener: SelectedCountryListener) {
        this.selectedCountryListener = listener
    }

    internal fun search(filterText: String) {
        filteredList.clear()

        if (filterText.isEmpty())
            filteredList.addAll(this.list)
        else
            this.list.filter { it.name.contains(filterText, ignoreCase = true) }.forEach {
                filteredList.add(it)
            }

        notifyDataSetChanged()
    }

    internal fun addToList(countryList: List<Country>) {
        this.list.addAll(countryList)
        filteredList.addAll(countryList)
        notifyDataSetChanged()
    }

    internal fun clearChecked() {
        this.filteredList.forEach { it.isSelected = false }
        notifyDataSetChanged()
    }

    internal fun selectAll() {
        this.filteredList.forEach { it.isSelected = true }
        notifyDataSetChanged()
    }

    internal fun checkCountries(preselectedList: List<Country>) {
        preselectedList.forEach { preselectedCountry ->
            this.filteredList.filter { preselectedCountry.name.equals(it.name, ignoreCase = true) }
                .forEach {
                    it.isSelected = true
                }
        }
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.run {
        clear()
        onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(com.rainads.rainadsapp.R.layout.item_spinner_custom, parent, false)
    )

    override fun getItemCount(): Int = filteredList.size

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun clear() {
            itemView.tv_country_name.text = ""
            itemView.tv_country_name
                .setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    com.rainads.rainadsapp.R.drawable.ic_checkbox_unselected,
                    0
                )
        }

        fun onBind(position: Int) {
            val countryName = filteredList[position].name
            val isSelected = filteredList[position].isSelected

            inflateData(countryName, isSelected)
            setItemClickListener(position, filteredList[position])
        }

        private fun setItemClickListener(position: Int, country: Country?) {
            itemView.setOnClickListener {
                country?.let {
                    country.isSelected = !country.isSelected
                    selectedCountryListener.onCountryChanged(country)
                    notifyItemChanged(position)
                }
            }
        }

        private fun inflateData(
            countryName: String?,
            isSelected: Boolean?
        ) {
            countryName?.let { itemView.tv_country_name.text = it }
            isSelected?.let {
                if (it) {
                    itemView.tv_country_name.setTypeface(null, Typeface.BOLD)
                    itemView.tv_country_name
                        .setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            com.rainads.rainadsapp.R.drawable.ic_checkbox_selected,
                            0
                        )
                } else {
                    itemView.tv_country_name.setTypeface(null, Typeface.NORMAL)
                    itemView.tv_country_name
                        .setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            com.rainads.rainadsapp.R.drawable.ic_checkbox_unselected,
                            0
                        )
                }
            }
        }
    }
}