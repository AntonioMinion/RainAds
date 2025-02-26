package com.rainads.rainadsapp.ui.base.view

import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.androidnetworking.error.ANError
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.ToastType
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), MVPView, BaseFragment.CallBack {

    private var progressDialog: Dialog? = null


    private lateinit var permissionRequestListener: PermissionRequestListener

    interface PermissionRequestListener {
        fun onPermissionGranted()
    }

    internal fun setPermissionRequestListener(listener: PermissionRequestListener) {
        this.permissionRequestListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
    }

    override fun showErrorMessage(error: Throwable) {
        var message = "Unknown Error"
        if (error is ANError)
            if (error.errorDetail.equals("connectionError", ignoreCase = true)) {
                message = getString(R.string.check_internet_connection)
            } else if (!error.message.isNullOrEmpty())
                message = error.message!!
        AppUtils.showMyToast(layoutInflater, this, message, ToastType.ERROR)
    }

    override fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun showProgress() {
        hideProgress()
        progressDialog = AppUtils.buildLoadingDialog(this)
        progressDialog!!.show()
    }

    private fun performDI() = AndroidInjection.inject(this)

    fun hasPermission(permissionType: String): Boolean {
        val permission = ContextCompat.checkSelfPermission(
                this@BaseActivity,
                permissionType
        )

        return permission == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permissionType: String, requestCode: Int) {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(permissionType),
                requestCode
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MyConstants.CAMERA_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Permission has been denied by user")
                } else {
                    permissionRequestListener.onPermissionGranted()
                    Log.i("Permission", "Permission has been granted by user")
                }
            }
        }
    }
}