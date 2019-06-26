package com.rainads.rainadsapp.ui.watchad.view

import com.rainads.rainadsapp.ui.base.view.MVPView
import com.rainads.rainadsapp.util.ToastType

interface WatchAdView : MVPView {
    fun onTimerTick(progress: Int, secondsUntilFinish: String)
    fun onTimerFinished()
    fun onAdWatched(type: ToastType, message: String)
}