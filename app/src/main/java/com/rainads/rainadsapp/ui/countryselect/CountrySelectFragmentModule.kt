package com.rainads.rainadsapp.ui.countryselect

import androidx.recyclerview.widget.LinearLayoutManager
import com.rainads.rainadsapp.ui.addad.view.AddAdActivity
import com.rainads.rainadsapp.ui.countryselect.interactor.CountrySelectInteractor
import com.rainads.rainadsapp.ui.countryselect.interactor.CountrySelectMVPInterator
import com.rainads.rainadsapp.ui.countryselect.presenter.CountrySelectMVPPresenter
import com.rainads.rainadsapp.ui.countryselect.presenter.CountrySelectPresenter
import com.rainads.rainadsapp.ui.countryselect.view.CountrySelectAdapter
import com.rainads.rainadsapp.ui.countryselect.view.CountrySelectDialogMVPView
import dagger.Module
import dagger.Provides

@Module
class CountrySelectFragmentModule {

    @Provides
    internal fun provideCountrySelectInteractor(interactor: CountrySelectInteractor): CountrySelectMVPInterator =
        interactor

    @Provides
    internal fun provideCountrySelectPresenter(presenter: CountrySelectPresenter<CountrySelectDialogMVPView, CountrySelectMVPInterator>)
            : CountrySelectMVPPresenter<CountrySelectDialogMVPView, CountrySelectMVPInterator> = presenter

    @Provides
    internal fun provideMainAdapter(): CountrySelectAdapter = CountrySelectAdapter(ArrayList())

    @Provides
    internal fun provideLinearLayoutManager(activity: AddAdActivity): LinearLayoutManager =
        LinearLayoutManager(activity)

}