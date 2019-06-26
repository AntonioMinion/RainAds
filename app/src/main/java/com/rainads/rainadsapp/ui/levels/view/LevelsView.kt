package com.rainads.rainadsapp.ui.levels.view

import com.rainads.rainadsapp.ui.base.view.MVPView

interface LevelsView : MVPView {
    fun displayLevels(referralList: List<String>?)
}