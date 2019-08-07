package com.rainads.rainadsapp.ui.main.view

import com.rainads.rainadsapp.data.network.models.AdModel
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.MVPView

interface MainMVPView : MVPView {
    fun referralCodeFound(code: String?)
    fun adFound(theAd: AdModel)
    fun userDownloaded(user: User)
    fun btcAddressFound(btcAddress: String?)
}