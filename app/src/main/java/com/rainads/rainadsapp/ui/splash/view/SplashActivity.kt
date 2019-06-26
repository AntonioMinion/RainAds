package com.rainads.rainadsapp.ui.splash.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.initial.view.InitialActivity
import com.rainads.rainadsapp.ui.main.view.MainActivity
import com.rainads.rainadsapp.ui.splash.interactor.SplashMVPInteractor
import com.rainads.rainadsapp.ui.splash.presenter.SplashMVPPresenter
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


class SplashActivity : BaseActivity(), SplashMVPView {

    @Inject
    lateinit var presenter: SplashMVPPresenter<SplashMVPView, SplashMVPInteractor>


    private val startDelay: Long = 1500

    override fun onFragmentDetached(tag: String) {
        TODO("not implemented")
    }

    override fun onFragmentAttached() {
        TODO("not implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rainads.rainadsapp.R.layout.activity_splash)
        presenter.onAttach(this)
    }

    override fun openMainActivity() {
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
