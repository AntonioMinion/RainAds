package com.rainads.rainadsapp.ui.levels.presenter

import com.rainads.rainadsapp.ui.base.presenter.MVPPresenter
import com.rainads.rainadsapp.ui.levels.interactor.ILevelsInteractor
import com.rainads.rainadsapp.ui.levels.view.LevelsView

interface ILevelsPresenter<V : LevelsView, I : ILevelsInteractor> : MVPPresenter<V, I> {
    fun onViewPrepared()
}