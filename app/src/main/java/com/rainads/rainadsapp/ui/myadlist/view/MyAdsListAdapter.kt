package com.rainads.rainadsapp.ui.myadlist.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.AdModel
import com.rainads.rainadsapp.util.AdActivityStatus
import com.rainads.rainadsapp.util.AdStatus
import com.rainads.rainadsapp.util.AppUtils
import kotlinx.android.synthetic.main.item_my_ad.view.*

class MyAdsListAdapter(private val myAdsListItems: MutableList<AdModel>) :
        RecyclerView.Adapter<MyAdsListAdapter.AdViewHolder>() {

    interface AdStatusChangeListener {
        fun onStatusChanged(campaignId: String, newStatus: String)
    }

    private lateinit var adStatusChangeListener: AdStatusChangeListener

    internal fun setAdStatusChangeListener(listener: AdStatusChangeListener) {
        this.adStatusChangeListener = listener
    }


    override fun getItemCount() = myAdsListItems.size

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) = holder.run {
        clear()
        onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AdViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_my_ad, parent, false)
    )

    internal fun addAdsToList(adList: List<AdModel>) {
        this.myAdsListItems.addAll(adList)
        notifyDataSetChanged()
    }

    internal fun clearAdList() {
        this.myAdsListItems.clear()
        notifyDataSetChanged()
    }


    internal fun stopAd() {
        //not implemented
    }

    inner class AdViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun clear() {
            itemView.tv_my_ad_url.text = ""
            itemView.tv_my_ad_description.text = ""
            itemView.tv_my_ad_duration.text = ""
            itemView.tv_my_ad_price.text = ""
            itemView.tv_my_ad_status.text = ""
            itemView.tv_my_ad_countries.text = ""
        }

        fun onBind(position: Int) {
            val (id, url, description, price, duration, status, activityStatus, countries) = myAdsListItems[position]

            inflateData(url, description, duration, price, status, activityStatus, countries)

            if (status.equals(AdStatus.APPROVED.toString(), ignoreCase = true)) {
                itemView.tv_my_ad_activity_status.visibility = View.VISIBLE
                itemView.btn_my_ad_start_pause.visibility = View.VISIBLE
                setItemClickListener(position)
            } else {
                itemView.tv_my_ad_activity_status.visibility = View.GONE
                itemView.btn_my_ad_start_pause.visibility = View.GONE
            }
        }

        private fun setPlayPauseButtonImage(activityStatus: String?) {
            activityStatus?.let {

                itemView.tv_my_ad_activity_status.text = activityStatus

                if (it.equals(AdActivityStatus.PAUSED.toString(), ignoreCase = true)) {
                    itemView.btn_my_ad_start_pause.setImageResource(R.drawable.ic_play)
                } else {
                    itemView.btn_my_ad_start_pause.setImageResource(R.drawable.ic_pause)
                }
            }
        }

        private fun setItemClickListener(position: Int) {
            itemView.btn_my_ad_start_pause.setOnClickListener {
                val currentAd = myAdsListItems[position]
                val newAd: AdModel

                val newStatus: String =
                        if (currentAd.activityStatus.equals(AdActivityStatus.PAUSED.toString(), ignoreCase = true)) {
                            AdActivityStatus.PLAYING.toString()
                        } else {
                            AdActivityStatus.PAUSED.toString()
                        }

                newAd = AdModel(
                        currentAd.id,
                        currentAd.url,
                        currentAd.description,
                        currentAd.price,
                        currentAd.duration,
                        currentAd.status,
                        newStatus,
                        currentAd.countries,
                ""
                )
                myAdsListItems[position] = newAd
                notifyItemChanged(position)

                adStatusChangeListener.onStatusChanged(currentAd.id, newStatus)
            }

            itemView.tv_my_ad_activity_status.setOnClickListener { itemView.btn_my_ad_start_pause.callOnClick() }
        }

        private fun inflateData(
                url: String?,
                description: String?,
                duration: String?,
                price: String?,
                status: String?,
                activityStatus: String?,
                countries: List<String>
        ) {
            url?.let { itemView.tv_my_ad_url.text = it }
            description?.let { itemView.tv_my_ad_description.text = it }
            duration?.let { itemView.tv_my_ad_duration.text = it }
            price?.let { itemView.tv_my_ad_price.text = it }

            if (countries.isEmpty())
                itemView.tv_my_ad_countries.visibility = View.GONE
            else {
                itemView.tv_my_ad_countries.visibility = View.VISIBLE
                itemView.tv_my_ad_countries.text = AppUtils.listToString(countries)
            }

            setPlayPauseButtonImage(activityStatus)
            status?.let {
                itemView.tv_my_ad_status.text = it
                when {
                    it.equals(
                            AdStatus.APPROVED.toString(),
                            ignoreCase = true
                    ) -> {
                        itemView.btn_my_ad_start_pause.isEnabled = true
                        itemView.btn_my_ad_start_pause.alpha = 1f

                        itemView.tv_my_ad_status.setTextColor(Color.parseColor("#7cb342"))
                        itemView.tv_my_ad_status.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.circle_green,
                                0,
                                0,
                                0
                        )
                    }
                    it.equals(
                            AdStatus.DENIED.toString(),
                            ignoreCase = true
                    ) -> {
                        itemView.btn_my_ad_start_pause.isEnabled = false
                        itemView.btn_my_ad_start_pause.alpha = 0.5f

                        itemView.tv_my_ad_status.setTextColor(Color.parseColor("#e53935"))
                        itemView.tv_my_ad_status.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.circle_red,
                                0,
                                0,
                                0
                        )
                    }
                    else -> {
                        itemView.btn_my_ad_start_pause.isEnabled = false
                        itemView.btn_my_ad_start_pause.alpha = 0.5f

                        itemView.tv_my_ad_status.setTextColor(Color.parseColor("#fbd943"))
                        itemView.tv_my_ad_status.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.circle_yellow,
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
