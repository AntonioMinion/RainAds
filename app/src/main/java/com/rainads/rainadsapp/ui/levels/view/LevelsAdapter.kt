package com.rainads.rainadsapp.ui.levels.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rainads.rainadsapp.R
import kotlinx.android.synthetic.main.item_referral_level.view.*

class LevelsAdapter(private val myAdsListItems: MutableList<String>) :
    RecyclerView.Adapter<LevelsAdapter.LevelHolder>() {


    override fun getItemCount() = myAdsListItems.size

    override fun onBindViewHolder(holder: LevelHolder, position: Int) = holder.run {
        clear()
        onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LevelHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_referral_level, parent, false)
    )


    internal fun addToList(leList: List<String>) {
        this.myAdsListItems.addAll(leList)
        notifyDataSetChanged()
    }

    inner class LevelHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun clear() {
            itemView.tv_nr_referrals.text = ""
        }

        fun onBind(position: Int) {
            val howMany = myAdsListItems[position]
            val currentLevel = (position + 1).toString()
            val percentage = when (position) {
                0 -> "10%"
                1 -> "5%"
                2 -> "3%"
                else -> "1%"
            }

            inflateData(currentLevel, howMany, percentage)
            setItemClickListener(currentLevel, howMany, percentage)

        }

        private fun setItemClickListener(currentLevel: String?, howMany: String?, percentage: String?) {
            //not yet implemented
        }

        private fun inflateData(
            currentLevel: String?,
            howMany: String?,
            percentage: String?
        ) {
            currentLevel?.let { itemView.tv_referral_level.text = it }
            howMany?.let { itemView.tv_nr_referrals.text = it }
            percentage?.let { itemView.tv_referral_level_percentage.text = it }
        }
    }
}
