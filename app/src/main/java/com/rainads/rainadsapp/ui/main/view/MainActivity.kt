package com.rainads.rainadsapp.ui.main.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.network.models.AdModel
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.deposit.view.DepositActivity
import com.rainads.rainadsapp.ui.initial.view.InitialActivity
import com.rainads.rainadsapp.ui.levels.view.LevelsActivity
import com.rainads.rainadsapp.ui.main.interactor.MainMVPInteractor
import com.rainads.rainadsapp.ui.main.presenter.MainMVPPresenter
import com.rainads.rainadsapp.ui.myadlist.view.MyAdListActivity
import com.rainads.rainadsapp.ui.transferpoints.view.TransferPointsActivity
import com.rainads.rainadsapp.ui.watchad.view.WatchAdActivity
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.ToastType
import kotlinx.android.synthetic.main.bottom_sheet_ad.view.*
import kotlinx.android.synthetic.main.bottom_sheet_profile.view.*
import kotlinx.android.synthetic.main.bottom_sheet_profile.view.ivClose
import kotlinx.android.synthetic.main.bottom_sheet_withdraw_options.view.*
import kotlinx.android.synthetic.main.dashboard_main_card.*
import kotlinx.android.synthetic.main.dialog_full_qr_code.*
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.android.synthetic.main.main_content.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMVPView {


    @Inject
    internal lateinit var presenter: MainMVPPresenter<MainMVPView, MainMVPInteractor>

    private var bitmap: Bitmap? = null

    private var clipboardManager: ClipboardManager? = null

    private var referralCode: String? = null
    private var btcAddress: String? = null

    private lateinit var lastOpenedAd: AdModel
    private var mUser = User()

    private var profileBottomSheetDialog: BottomSheetDialog? = null

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

        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        setOnClickListeners()

        ivRefresh.setOnClickListener { presenter.getUser() }
    }

    override fun userDownloaded(user: User) {
        mUser = user
        setBalance()
    }

    private fun setBalance() {
        tvAmountAvailable.text =
            if (mUser.balance.isNullOrEmpty() || mUser.balance?.toFloat() == 0f) "0"
            else String.format("%.2f", mUser.balance?.toDouble())
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

        try {
            if (!referralCode.isNullOrEmpty())
                bitmap = generateQrCode(referralCode!!)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
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

        btnTransferPoints.setOnClickListener {
            val i = Intent(this, TransferPointsActivity::class.java)
            i.putExtra(MyConstants.EXTRA_POINTS, mUser.balance?.toDouble())
            startActivityForResult(i, 11)
        }

        btn_advertise.setOnClickListener {
            val i = Intent(this, MyAdListActivity::class.java)
            startActivity(i)
        }

        llNetwork.setOnClickListener {
            val i = Intent(this, LevelsActivity::class.java)
            startActivity(i)
        }

        llWithdraw.setOnClickListener {
            showWithdrawOptions()
        }

        llDeposit.setOnClickListener {
            val i = Intent(this, DepositActivity::class.java)
            i.putExtra(MyConstants.EXTRA_IS_DEPOSIT, true)
            startActivity(i)
        }
    }

    override fun adFound(theAd: AdModel) {
        lastOpenedAd = theAd
        if (!theAd.message.isNullOrEmpty()) {
            AppUtils.showMyToast(layoutInflater, this, theAd.message, ToastType.INFO)
        } else if (theAd.duration.isNullOrEmpty() || theAd.price.isNullOrEmpty() || theAd.url.isNullOrEmpty()) {
            AppUtils.showMyToast(
                layoutInflater,
                this,
                getString(R.string.ad_error),
                ToastType.ERROR
            )
        } else {
            showAdBottomSheet(theAd)
        }
    }

    override fun canWatchAd(watchable: Boolean) {
        //not implemented for now
//        btn_watch_ad.isEnabled = watchable
//        btn_watch_ad.alpha = if(watchable) 1f else 0.5f
    }

    @SuppressLint("InflateParams")
    private fun showProfileBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_profile, null)
        profileBottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        dialogView.tv_referral_code.text = referralCode
        dialogView.tv_btc_address.text = btcAddress
        if (bitmap != null)
            dialogView.ivQrCodeSmall.setImageBitmap(bitmap)
        else
            dialogView.ivQrCodeSmall.visibility = View.GONE
        setBottomMenuClickListeners(dialogView, profileBottomSheetDialog!!)
        profileBottomSheetDialog!!.setContentView(dialogView)
        profileBottomSheetDialog!!.show()
    }

    @SuppressLint("InflateParams")
    private fun showAdBottomSheet(theAd: AdModel) {
        if (theAd.duration.isNullOrEmpty() || theAd.price.isNullOrEmpty())
            return;

        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_ad, null)
        val dialog = BottomSheetDialog(this, R.style.SheetDialog)

        dialogView.tv_ad_url.text = theAd.url

        dialogView.tv_ad_description.text =
            if (theAd.description.isNullOrEmpty()) "/" else theAd.description

        dialogView.tv_ad_duration.text =
            if (theAd.duration.isNullOrEmpty()) "/" else theAd.duration

        dialogView.tv_ad_price.text =
            if (theAd.price.isNullOrEmpty()) "/" else (theAd.price.toInt() / 2).toString()

        dialogView.ll_ad_price.setOnClickListener {
            dialog.dismiss()
            val i = Intent(this, WatchAdActivity::class.java)
            i.putExtra(MyConstants.EXTRA_AD_URL, theAd.url)
            i.putExtra(MyConstants.EXTRA_AD_ID, theAd.id)
            i.putExtra(
                MyConstants.EXTRA_AD_DURATION,
                if (theAd.duration != null) theAd.duration.toLong() else 0
            )
            i.putExtra(
                MyConstants.EXTRA_AD_PRICE,
                if (theAd.price != null) theAd.price.toInt() / 2 else 0
            )
            startActivityForResult(i, MyConstants.REQUEST_RESULT_WATCH_AD)
        }

        dialogView.ivClose.setOnClickListener { dialog.dismiss() }

        dialog.setContentView(dialogView)
        dialog.show()
    }

    @SuppressLint("InflateParams")
    private fun showWithdrawOptions() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_withdraw_options, null)

        val dialog = BottomSheetDialog(this, R.style.SheetDialog)

        dialogView.btnWithdrawalBtc.setOnClickListener {
            dialog.dismiss()
            val i = Intent(this, DepositActivity::class.java)
            i.putExtra(MyConstants.EXTRA_IS_DEPOSIT, false)
            startActivity(i)
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
            Toast.makeText(
                this,
                getString(com.rainads.rainadsapp.R.string.code_copied),
                Toast.LENGTH_SHORT
            ).show()
        }

        //Copy btc address
        view.ll_btc_container.setOnClickListener {
            val myClip =
                ClipData.newPlainText(
                    getString(com.rainads.rainadsapp.R.string.btc_address),
                    view.tv_btc_address.text
                )
            clipboardManager?.primaryClip = myClip
            Toast.makeText(
                this,
                getString(com.rainads.rainadsapp.R.string.btc_copied),
                Toast.LENGTH_SHORT
            ).show()
        }

        //open qr code bigger
        view.ivQrCodeSmall.setOnClickListener {
            showFullQrCodeDialog()
        }

        //logout
        view.ll_logout.setOnClickListener {
            dialog.dismiss()
            showLogoutDialog()
        }

    }

    private fun logout() {
        presenter.logoutUser()
        val i = Intent(this, InitialActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun showLogoutDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialog.window != null)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_logout)

        dialog.btnLogOut.setOnClickListener {
            dialog.dismiss()
            if (profileBottomSheetDialog != null)
                profileBottomSheetDialog!!.dismiss()
            logout()
        }

        dialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showFullQrCodeDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_full_qr_code)

        dialog.ivQrCode.setImageBitmap(bitmap)

        dialog.ivQrCode.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @Throws(WriterException::class)
    private fun generateQrCode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                500, 500, null
            )

        } catch (Illegalargumentexception: IllegalArgumentException) {

            return null
        }

        val bitMatrixWidth = bitMatrix.getWidth()

        val bitMatrixHeight = bitMatrix.getHeight()

        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.black)
                else
                    resources.getColor(R.color.white)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }
}
