package com.rainads.rainadsapp.ui.scanner.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.ui.base.view.BaseDialogView
import com.rainads.rainadsapp.ui.scanner.interactor.IScannerInteractor
import com.rainads.rainadsapp.ui.scanner.presenter.IScannerPresenter
import com.rainads.rainadsapp.util.MyConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_scanner.*
import javax.inject.Inject


class ScannerDialog : BaseDialogView(), IScannerView {

    override fun showErrorMessage(error: Throwable) {
        //not implemented
    }

    private val TAG = "ScannerDialog"

    private var mDisposable: Disposable? = null

    @Inject
    internal lateinit var presenter: IScannerPresenter<IScannerView, IScannerInteractor>

    private lateinit var scanFinishedListener: ScanFinishedListener

    interface ScanFinishedListener {
        fun onScanSuccessful(refCode: String)
        fun onScanError(errorMsg: String)
    }

    companion object {
        fun newInstance(): ScannerDialog? {
            return ScannerDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_scanner, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)

        setListeners()
        activateScanner()
    }

    private fun setListeners() {
        btnClose.setOnClickListener { dismissDialog() }
    }

    private fun activateScanner() {
        if (parentActivity?.hasPermission(Manifest.permission.CAMERA)!!) {
            mDisposable = barcodeView
                    .getObservable()
                    .firstOrError()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { barcode ->
                                scanFinishedListener.onScanSuccessful(barcode.displayValue)
                                dismissDialog()
                            },
                            { throwable ->
                                scanFinishedListener.onScanError(
                                        if (throwable.message.isNullOrEmpty())
                                            "An error has occurred while scanning"
                                        else
                                            throwable.message!!
                                )
                                dismissDialog()
                            })

        } else
            parentActivity?.requestPermission(Manifest.permission.CAMERA, MyConstants.CAMERA_REQUEST_CODE)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun dismissDialog() = super.dismissDialog(TAG)

    internal fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    internal fun setListener(listener: ScanFinishedListener) {
        this.scanFinishedListener = listener
    }
}