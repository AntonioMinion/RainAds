package com.rainads.rainadsapp.ui.levels.interactor

import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

interface ILevelsInteractor : MVPInteractor {
    fun getReferralList(): Observable<User>
}