package com.rainads.rainadsapp.ui.splash.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.ads.MobileAds
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.initial.view.InitialActivity
import com.rainads.rainadsapp.ui.main.view.MainActivity
import com.rainads.rainadsapp.ui.splash.interactor.SplashMVPInteractor
import com.rainads.rainadsapp.ui.splash.presenter.SplashMVPPresenter
import com.rainads.rainadsapp.util.AppUtils
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


class SplashActivity : BaseActivity(), SplashMVPView {

    @Inject
    lateinit var presenter: SplashMVPPresenter<SplashMVPView, SplashMVPInteractor>

    private val startDelay: Long = 1500

    private fun initAdProviders(){
        MobileAds.initialize(
            this,
            "ca-app-pub-6953773192251170~7601411476"
        )

/*        StartAppSDK.init(this, "207141024", false)

        StartAppSDK.setUserConsent (this,
            "pas",
            System.currentTimeMillis(),
            false)*/

    }

    override fun onFragmentDetached(tag: String) {
        TODO("not implemented")
    }

    override fun onFragmentAttached() {
        TODO("not implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rainads.rainadsapp.R.layout.activity_splash)
        tvVersionCode.text = AppUtils.getVersionName(this@SplashActivity)
        presenter.onAttach(this)
    }

    override fun openMainActivity() {
        initAdProviders()
        Handler().postDelayed({
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }, startDelay)
    }

    override fun openInitialActivity() {
        window.exitTransition = null

        Handler().postDelayed({
            val i = Intent(this, InitialActivity::class.java)
            val options =
                ActivityOptions.makeSceneTransitionAnimation(this, iv_logo, "logo")
            startActivity(i, options.toBundle())
            //ActivityCompat.finishAfterTransition(this)
            Handler().postDelayed({
                finish()
            }, 2000)
        }, startDelay)
    }

}
