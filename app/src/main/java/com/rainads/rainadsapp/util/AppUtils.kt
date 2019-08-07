package com.rainads.rainadsapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rainads.rainadsapp.R
import kotlinx.android.synthetic.main.custom_toast.view.*
import java.io.IOException
import java.nio.charset.Charset

object AppUtils {
    fun getScreenWidth(context: Context): Int {
        val windowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.let {
            val dm = DisplayMetrics()
            it.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }
    }

    fun getScreenHeight(context: Context): Int {
        val windowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.let {
            val dm = DisplayMetrics()
            it.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        (context.assets).open(jsonFileName).let {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            it.close()
            return String(buffer, Charset.forName("UTF-8"))
        }
    }

    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog = Dialog(context)
        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.window?.requestFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(R.layout.dialog_custom_loading)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }

    @SuppressLint("InflateParams")
    fun showMyToast(layoutInflater: LayoutInflater, context: Context, text: String?, type: ToastType) {
        val myToast = Toast(context)
        myToast.duration = Toast.LENGTH_SHORT
        val layout = layoutInflater.inflate(R.layout.custom_toast, null)
        myToast.view = layout

        val img: Int = when (type) {
            ToastType.ERROR -> R.drawable.ic_error
            ToastType.SUCCESS -> R.drawable.ic_success
            ToastType.NETWORK -> R.drawable.ic_no_connection
            else -> R.drawable.ic_information
        }

        val textColor: Int = when (type) {
            ToastType.ERROR -> ContextCompat.getColor(context, R.color.red)
            ToastType.SUCCESS -> ContextCompat.getColor(context, R.color.green)
            else -> ContextCompat.getColor(context, R.color.colorPrimary)
        }

        myToast.view.iv_toast_icon.setImageResource(img)
        myToast.view.tv_toast_text.setTextColor(textColor)
        myToast.view.tv_toast_text.text = text

        myToast.show()
    }

    fun showMySnackBar(view: View, text: String, type: SnackBarType) {

        val img: Drawable? = when (type) {
            SnackBarType.ERROR -> ContextCompat.getDrawable(view.context, R.drawable.ic_error)
            SnackBarType.SUCCESS -> ContextCompat.getDrawable(view.context, R.drawable.ic_success)
            SnackBarType.NETWORK -> ContextCompat.getDrawable(view.context, R.drawable.ic_no_connection)
            else -> ContextCompat.getDrawable(view.context, R.drawable.ic_information)
        }

        val textColor: Int = when (type) {
            SnackBarType.ERROR -> ContextCompat.getColor(view.context, R.color.red)
            SnackBarType.SUCCESS -> ContextCompat.getColor(view.context, R.color.green)
            else -> ContextCompat.getColor(view.context, R.color.colorPrimary)
        }

        val snackBar = Snackbar.make(
            view, // Parent view
            text, // Message to show
            Snackbar.LENGTH_SHORT // How long to display the message.
        )

        val sbView = snackBar.view
        sbView.setBackgroundResource(R.drawable.background_rounded_corners_12)

        val parentParams = sbView.layoutParams as FrameLayout.LayoutParams
        parentParams.setMargins(58, 0, 58, 58)
        sbView.layoutParams = parentParams

        val textView = sbView.findViewById(R.id.snackbar_text) as TextView

        img?.setBounds(0, 0, 60, 60)
        textView.setCompoundDrawables(img, null, null, null)
        textView.maxLines = 3
        textView.compoundDrawablePadding = 26

        textView.setTextColor(textColor)

        snackBar.show()
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isConnected(ctx: Context): Boolean {
        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getDurationValueFromSpinnerItem(chosenItem: String): String = chosenItem.take(2)

    fun getPriceValueFromSpinnerItem(chosenItem: String): String {
        val index = chosenItem.indexOf('/')
        return if (index == -1) "" else chosenItem.substring(index + 1, index + 3)
    }

    fun listToString(list: List<String>): String {
        var text = ""
        list.forEachIndexed { index, it -> text += it + (if (index == list.size - 1) "" else ", ") }
        return text
    }

    fun getVersionName(context: Context): String {
        val manager = context.packageManager
        val info = manager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        return info.versionName
    }
}