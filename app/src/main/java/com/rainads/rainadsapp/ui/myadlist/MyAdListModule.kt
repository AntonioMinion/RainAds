package com.rainads.rainadsapp.ui.myadlist

import androidx.recyclerview.widget.LinearLayoutManager
import com.rainads.rainadsapp.ui.myadlist.interactor.IMyAdListInteractor
import com.rainads.rainadsapp.ui.myadlist.interactor.MyAdListInteractor
import com.rainads.rainadsapp.ui.myadlist.presenter.IMyAdListPresenter
import com.rainads.rainadsapp.ui.myadlist.presenter.MyAdListPresenter
import com.rainads.rainadsapp.ui.myadlist.view.MyAdListActivity
import com.rainads.rainadsapp.ui.myadlist.view.MyAdListView
import com.rainads.rainadsapp.ui.myadlist.view.MyAdsListAdapter
import dagger.Module
import dagger.Provides

@Module
class MyAdListModule {

    @Provides
    internal fun provideMyAdListInteractor(interactor: MyAdListInteractor): IMyAdListInteractor = interactor

    @Provides
    internal fun provideMyAdListPresenter(presenter: MyAdListPresenter<MyAdListView, IMyAdListInteractor>)
            : IMyAdListPresenter<MyAdListView, IMyAdListInteractor> = presenter

    @Provides
    internal fun provideMyAdsListAdapter(): MyAdsListAdapter = MyAdsListAdapter(ArrayList())

    @Provides
    internal fun provideLinearLayoutManager(activity: MyAdListActivity): LinearLayoutManager =
        LinearLayoutManager(activity)


}