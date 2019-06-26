package com.rainads.rainadsapp.ui.levels

import androidx.recyclerview.widget.LinearLayoutManager
import com.rainads.rainadsapp.ui.levels.interactor.ILevelsInteractor
import com.rainads.rainadsapp.ui.levels.interactor.LevelsInteractor
import com.rainads.rainadsapp.ui.levels.presenter.ILevelsPresenter
import com.rainads.rainadsapp.ui.levels.presenter.LevelsPresenter
import com.rainads.rainadsapp.ui.levels.view.LevelsActivity
import com.rainads.rainadsapp.ui.levels.view.LevelsAdapter
import com.rainads.rainadsapp.ui.levels.view.LevelsView
import dagger.Module
import dagger.Provides

@Module
class LevelsModule {

    @Provides
    internal fun provideLevelsInteractor(interactor: LevelsInteractor): ILevelsInteractor = interactor

    @Provides
    internal fun provideLevelsPresenter(presenter: LevelsPresenter<LevelsView, ILevelsInteractor>)
            : ILevelsPresenter<LevelsView, ILevelsInteractor> = presenter

    @Provides
    internal fun provideLevelsAdapter(): LevelsAdapter = LevelsAdapter(ArrayList())

    @Provides
    internal fun provideLinearLayoutManager(activity: LevelsActivity): LinearLayoutManager =
        LinearLayoutManager(activity)


}