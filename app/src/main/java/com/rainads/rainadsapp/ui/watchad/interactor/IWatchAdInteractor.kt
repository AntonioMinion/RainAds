package com.rainads.rainadsapp.ui.watchad.interactor

import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

interface IWatchAdInteractor : MVPInteractor {
    fun watchAd(adId: String): Observable<String>
}