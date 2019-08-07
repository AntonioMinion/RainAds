package com.rainads.rainadsapp.ui.myadlist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.addad.view.AddAdActivity
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.deposit.view.DepositActivity
import com.rainads.rainadsapp.ui.myadlist.interactor.IMyAdListInteractor
import com.rainads.rainadsapp.ui.myadlist.presenter.MyAdListPresenter
import com.rainads.rainadsapp.ui.watchad.view.WatchAdActivity
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.ToastType
import kotlinx.android.synthetic.main.activity_my_ad_list.*
import kotlinx.android.synthetic.main.custom_back_button.*
import javax.inject.Inject

class MyAdListActivity : BaseActivity(), MyAdListView, MyAdsListAdapter.AdStatusChangeListener {

    @Inject
    internal lateinit var myAdListAdapter: MyAdsListAdapter
    @Inject
    internal lateinit var layoutManager: LinearLayoutManager
    @Inject
    internal lateinit var presenter: MyAdListPresenter<MyAdListView, IMyAdListInteractor>


    override fun onFragmentAttached() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentDetached(tag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ad_list)
        presenter.onAttach(this)
        setupList()
        setOnClickListeners()

        swipeContainer.setOnRefreshListener {
            myAdListAdapter.clearAdList()
            presenter.onViewPrepared()
        }
    }

    private fun setupList() {
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewMyAds.layoutManager = layoutManager
        recyclerViewMyAds.itemAnimator = null
        recyclerViewMyAds.adapter = myAdListAdapter
        myAdListAdapter.setAdStatusChangeListener(this)
        presenter.onViewPrepared()
    }

    private fun setOnClickListeners() {
        btn_add_new_ad.setOnClickListener {
            val i = Intent(this, AddAdActivity::class.java)
            startActivity(i)
        }

        btnNewAd.setOnClickListener { btn_add_new_ad.callOnClick() }

        btnBack.setOnClickListener { onBackPressed() }

        tvDepositBtn.setOnClickListener {
            val i = Intent(this, DepositActivity::class.java)
            i.putExtra(MyConstants.EXTRA_IS_DEPOSIT, true)
            startActivity(i)
        }
    }

    override fun showMessage(type: ToastType, message: String?) {
        AppUtils.showMyToast(layoutInflater, this, message, type)
        if (type == ToastType.ERROR)
            myAdListAdapter.stopAd()
    }

    override fun onStatusChanged(campaignId: String, newStatus: String) {
        presenter.adStatusChanged(campaignId, newStatus)
    }

    override fun userDownloaded(user: User) {
        swipeContainer.isRefreshing = false

        val adList = user.myCampaigns!!.reversed()

        adList.let {
            myAdListAdapter.addAdsToList(it)
            if (it.isEmpty()) {
                btnNewAd.visibility = View.VISIBLE
                tvNoAdsYet.visibility = View.VISIBLE
                btn_add_new_ad.visibility = View.GONE
            } else {
                btnNewAd.visibility = View.GONE
                tvNoAdsYet.visibility = View.GONE
                btn_add_new_ad.visibility = View.VISIBLE
            }
        }

        tvMyAdsBalance.text = if (user.balance.isNullOrEmpty()) "0" else user.balance
    }

}
