package com.rainads.rainadsapp.ui.main.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.AdModel
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.initial.view.InitialActivity
import com.rainads.rainadsapp.ui.levels.view.LevelsActivity
import com.rainads.rainadsapp.ui.main.interactor.MainMVPInteractor
import com.rainads.rainadsapp.ui.main.presenter.MainMVPPresenter
import com.rainads.rainadsapp.ui.myadlist.view.MyAdListActivity
import com.rainads.rainadsapp.ui.watchad.view.WatchAdActivity
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.ToastType
import kotlinx.android.synthetic.main.bottom_sheet_ad.view.*
import kotlinx.android.synthetic.main.bottom_sheet_profile.view.*
import kotlinx.android.synthetic.main.bottom_sheet_profile.view.ivClose
import kotlinx.android.synthetic.main.dashboard_main_card.*
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.android.synthetic.main.main_content.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMVPView {


    @Inject
    internal lateinit var presenter: MainMVPPresenter<MainMVPView, MainMVPInteractor>

    private var clipboardManager: ClipboardManager? = null

    private var referralCode: String? = null
    private var btcAddress: String? = null

    private lateinit var lastOpenedAd: AdModel
    private var mUser = User()

    lateinit var mainHandler: Handler
    private val pagerList = ArrayList<String>()
    var currentPage = 0

    override fun onFragmentAttached() {
        TODO("not implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("not implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onAttach(this)

        mainHandler = Handler(Looper.getMainLooper())

        pagerList.add("1")
        pagerList.add("2")
        pagerList.add("3")

        viewPager.adapter = ViewPagerAdapter(this, pagerList)
        viewPager.setPadding(150, 0, 150, 0)
        viewPager.clipToPadding = false
        viewPager.pageMargin = 40

        spinner_balances.background.setColorFilter(
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                PorterDuff.Mode.SRC_ATOP
        )

        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        setOnClickListeners()
        setupBalancesSpinner()

        ivRefresh.setOnClickListener { presenter.getUser() }
    }

    override fun userDownloaded(user: User) {
        mUser = user
        setBalance()
    }

    private fun setBalance() {
        tv_balance.text = if (mUser.balance.isNullOrEmpty()) "0" else mUser.balance
    }

    private fun setAdBalance() {
        tv_balance.text = "0"
    }

    private fun setupBalancesSpinner() {
        val balancesAdapter =
                ArrayAdapter.createFromResource(
                        this
                        , R.array.balance_items
                        , R.layout.item_invisible_text
                )

        balancesAdapter.setDropDownViewResource(R.layout.item_spinner_custom)

        spinner_balances?.adapter = balancesAdapter

        spinner_balances?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //not implemented
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tvDashboardChosenBalance.text = balancesAdapter.getItem(position)?.toString()
                if (position == 0)
                    setBalance()
                else
                    setAdBalance()
            }

        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(taskToScrollPager)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(taskToScrollPager)
    }

    private fun scrollViewpager() {
        if (currentPage != viewPager.currentItem)
            currentPage = viewPager.currentItem

        if (pagerList.size - 1 == currentPage)
            currentPage = 0
        else
            currentPage++

        viewPager.setCurrentItem(currentPage, true)
    }

    private val taskToScrollPager = object : Runnable {
        override fun run() {
            scrollViewpager()
            mainHandler.postDelayed(this, 3000)
        }
    }

    override fun referralCodeFound(code: String?) {
        referralCode = code
    }

    override fun btcAddressFound(btcAddress: String?) {
        this.btcAddress = btcAddress
    }

    private fun setOnClickListeners() {
        iv_open_menu.setOnClickListener {
            showProfileBottomSheet()
        }

        btn_watch_ad.setOnClickListener {
            presenter.getAd()
        }

        btn_advertise.setOnClickListener {
            val i = Intent(this, MyAdListActivity::class.java)
            startActivity(i)
        }

        frameNetwork.setOnClickListener {
            val i = Intent(this, LevelsActivity::class.java)
            startActivity(i)
        }
    }

    override fun adFound(theAd: AdModel) {
        lastOpenedAd = theAd
        if (!theAd.message.isNullOrEmpty()) {
            AppUtils.showMyToast(layoutInflater, this, theAd.message, ToastType.INFO)
        } else {
            showAdBottomSheet(theAd)
        }
    }

    @SuppressLint("InflateParams")
    private fun showProfileBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_profile, null)
        val dialog = BottomSheetDialog(this, R.style.SheetDialog)
        dialogView.tv_referral_code.text = referralCode
        dialogView.tv_btc_address.text = btcAddress
        setBottomMenuClickListeners(dialogView, dialog)
        dialog.setContentView(dialogView)
        dialog.show()
    }

    @SuppressLint("InflateParams")
    private fun showAdBottomSheet(theAd: AdModel) {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_ad, null)
        val dialog = BottomSheetDialog(this, R.style.SheetDialog)

        dialogView.tv_ad_url.text = theAd.url

        dialogView.tv_ad_description.text =
                if (theAd.description.isNullOrEmpty()) "/" else theAd.description

        dialogView.tv_ad_duration.text =
                if (theAd.duration.isNullOrEmpty()) "/" else theAd.duration

        dialogView.tv_ad_price.text = if (theAd.price.isNullOrEmpty()) "/" else (theAd.price.toInt()/2).toString()

        dialogView.ll_ad_price.setOnClickListener {
            dialog.dismiss()
            val i = Intent(this, WatchAdActivity::class.java)
            i.putExtra(MyConstants.EXTRA_AD_URL, theAd.url)
            i.putExtra(MyConstants.EXTRA_AD_ID, theAd.id)
            i.putExtra(MyConstants.EXTRA_AD_DURATION, theAd.duration?.toLong())
            i.putExtra(MyConstants.EXTRA_AD_PRICE, theAd.price.toInt()/2)
            startActivityForResult(i, MyConstants.REQUEST_RESULT_WATCH_AD)
        }

        dialogView.ivClose.setOnClickListener { dialog.dismiss() }

        dialog.setContentView(dialogView)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            presenter.getUser()
        }
    }

    private fun setBottomMenuClickListeners(view: View, dialog: BottomSheetDialog) {
        //Close
        view.ivClose.setOnClickListener { dialog.dismiss() }

        //Copy code
        view.ll_referral_code.setOnClickListener {
            val myClip = ClipData.newPlainText(
                    getString(com.rainads.rainadsapp.R.string.referral_code),
                    view.tv_referral_code.text
            )
            clipboardManager?.primaryClip = myClip
            Toast.makeText(this, getString(com.rainads.rainadsapp.R.string.code_copied), Toast.LENGTH_SHORT).show()
        }

        //Copy btc address
        view.ll_btc_container.setOnClickListener {
            val myClip =
                    ClipData.newPlainText(getString(com.rainads.rainadsapp.R.string.btc_address), view.tv_btc_address.text)
            clipboardManager?.primaryClip = myClip
            Toast.makeText(this, getString(com.rainads.rainadsapp.R.string.btc_copied), Toast.LENGTH_SHORT).show()
        }

        view.ll_logout.setOnClickListener {
            showDialog()
        }

    }

    private fun logout() {
        presenter.logoutUser()
        val i = Intent(this, InitialActivity::class.java)
        startActivity(i)
        finish()
    }

    // Method to show an alert dialog with yes, no and cancel button
    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_logout)

        dialog.btnLogOut.setOnClickListener {
            dialog.dismiss()
            logout()
        }

        dialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
