package com.rainads.rainadsapp.ui.main.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.rainads.rainadsapp.R


class ViewPagerAdapter(private val mContext: Context, private val mListData: List<String>) : PagerAdapter() {

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return mListData.size
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.item_pager_ad, container, false) as ViewGroup

/*        val textView = view.findViewById<TextView>(R.id.textView)

        val button = view.findViewById<View>(R.id.button)
        button.setText(mListData[position])
        button.setOnClickListener(object : View.OnClickListener() {
            fun onClick(view: View) {
                textView.text = mListData[position]
            }
        })*/

        container.addView(view)
        return view
    }
}