package com.rainads.rainadsapp.ui.countryselect

import com.rainads.rainadsapp.ui.countryselect.view.CountrySelectDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CountrySelectDialogFragmentProvider {
    @ContributesAndroidInjector(modules = [CountrySelectFragmentModule::class])
    internal abstract fun provideCountrySelectFragment(): CountrySelectDialog
}