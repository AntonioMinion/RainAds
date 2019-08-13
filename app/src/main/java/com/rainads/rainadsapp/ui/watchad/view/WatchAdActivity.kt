package com.rainads.rainadsapp.ui.watchad.view

import android.app.Activity
import android.app.Dialog
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.watchad.interactor.IWatchAdInteractor
import com.rainads.rainadsapp.ui.watchad.presenter.IWatchAdPresenter
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.ToastType
import kotlinx.android.synthetic.main.activity_watch_ad.*
import kotlinx.android.synthetic.main.dialog_extra_ad.*
import javax.inject.Inject

class WatchAdActivity : BaseActivity(), WatchAdView, RewardedVideoAdListener {


    @Inject
    internal lateinit var presenter: IWatchAdPresenter<WatchAdView, IWatchAdInteractor>

    private lateinit var adId: String
    private lateinit var adUrl: String
    private var adDuration = 0L
    private var adPrice = 30

    private var adWatched = false

    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private var pageLoaded = false

    override fun onFragmentAttached() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentDetached(tag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_ad)
        presenter.onAttach(this)

        getExtras()

        tv_ad_timer.text = adDuration.toString()
        progressBarTimer.max = (adDuration * 1000).toInt()

        if (!adUrl.startsWith("http://", ignoreCase = true) && !adUrl.startsWith("https://", ignoreCase = true)) {
            adUrl = "https://".plus(adUrl)
        }

        initWebView()
        setOnClickListeners()


        MobileAds.initialize(this,
                "ca-app-pub-6953773192251170~7601411476")

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this

        loadRewardedVideoAd()
    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-6953773192251170/1327524421",
                AdRequest.Builder().build())
    }


    private fun getExtras() {
        val bundle = intent.extras
        if (bundle != null) {
            adId = bundle.getString(MyConstants.EXTRA_AD_ID, "")
            adDuration = bundle.getLong(MyConstants.EXTRA_AD_DURATION, 15L)
            adUrl = bundle.getString(MyConstants.EXTRA_AD_URL, MyConstants.PLACEHOLDER_WEBSITE)
            adPrice = bundle.getInt(MyConstants.EXTRA_AD_PRICE, 30)
        } else {
            adId = ""
            adDuration = 15L
            adUrl = MyConstants.PLACEHOLDER_WEBSITE
            adPrice = 30
        }
    }


    override fun onTimerTick(progress: Int, secondsUntilFinish: String) {
        progressBarTimer.progress = progress
        tv_ad_timer.text = secondsUntilFinish
    }

    override fun onTimerFinished() {
        tv_ad_timer.visibility = View.GONE
        progressBarAdWatched.visibility = View.VISIBLE
        ivBack.isEnabled = false
        ivBack.alpha = 0.6f
    }

    override fun onAdWatched(type: ToastType, message: String) {
        AppUtils.showMyToast(layoutInflater, this, message, type)

        progressBarAdWatched.visibility = View.GONE
        ivBack.isEnabled = true
        ivBack.alpha = 1f

        if (type == ToastType.SUCCESS) {
            adWatched = true
            setResult(Activity.RESULT_OK)
            askForExtraAd()
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    override fun onAdWatchedExtra(type: ToastType, message: String) {
        AppUtils.showMyToast(layoutInflater, this, message, type)
    }

    private fun askForExtraAd() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_extra_ad)

        val text = getString(R.string.extra_ad_description, adPrice)
        dialog.tvExtraAdDescription.text = text

        dialog.btnYes.setOnClickListener {
            dialog.dismiss()

            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            } else {
                AppUtils.showMyToast(layoutInflater, this, "Ad isn't loaded yet.", ToastType.INFO)
            }

        }

        dialog.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun setOnClickListeners() {
        iv_web_back.setOnClickListener { if (webView.canGoBack()) webView.goBack() }
        iv_web_forward.setOnClickListener { if (webView.canGoForward()) webView.goForward() }
        ivBack.setOnClickListener { onBackPressed() }
    }

    private fun initWebView() {
        // Get the web view settings instance
        val settings = webView.settings

        // Enable java script in web view
        settings.javaScriptEnabled = true

        // Enable and setup web view cache
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCachePath(cacheDir.path)


        // Enable zooming in web view
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true

        // Enable disable images in web view
        settings.blockNetworkImage = false
        // Whether the WebView should load image resources
        settings.loadsImagesAutomatically = true


        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true  // api 26
        }
        //settings.pluginState = WebSettings.PluginState.ON
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false

        // WebView settings
        webView.fitsSystemWindows = true
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                // Page loading started
                // Do something
                tv_web_title.text = getString(R.string.watch_ad)

                // Enable disable back forward button
                iv_web_back.isEnabled = webView.canGoBack()
                iv_web_forward.isEnabled = webView.canGoForward()
            }

            override fun onPageFinished(view: WebView, url: String) {
                // Page loading finished
                // Display the loaded page title in a toast message
                tv_web_title.text = view.title

                // Enable disable back forward button
                iv_web_back.isEnabled = webView.canGoBack()
                iv_web_forward.isEnabled = webView.canGoForward()

                pageLoaded = true
                presenter.startTimer(adId, adDuration)
            }
        }

        // Set web view chrome client
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressBarWeb.progress = newProgress
            }
        }

        webView.clearCache(true)
        webView.clearHistory()

        webView.loadUrl(adUrl)
    }


    override fun onPause() {
        super.onPause()
        if (!adWatched && pageLoaded)
            presenter.pauseTimer()
        mRewardedVideoAd.pause(this)
    }

    override fun onResume() {
        super.onResume()
        if (!adWatched && pageLoaded)
            presenter.startTimer(adId, adDuration)
        mRewardedVideoAd.resume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd.destroy(this)
    }

    override fun onRewardedVideoCompleted() {
        //non implemented
    }

    override fun onRewardedVideoAdClosed() {
        //non implemented
    }

    override fun onRewardedVideoAdLeftApplication() {
        //non implemented
    }

    override fun onRewardedVideoAdLoaded() {
        //non implemented
    }

    override fun onRewardedVideoAdOpened() {
        //non implemented
    }

    override fun onRewarded(p0: RewardItem?) {
        presenter.watchAdExtra(adId)
    }

    override fun onRewardedVideoStarted() {
        //non implemented
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        //non implemented
    }

}
