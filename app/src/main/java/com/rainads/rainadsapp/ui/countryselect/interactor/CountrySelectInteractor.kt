package com.rainads.rainadsapp.ui.countryselect.interactor

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.internal.`$Gson$Types`
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.MyConstants
import io.reactivex.Observable
import javax.inject.Inject

class CountrySelectInteractor @Inject internal constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) :
    BaseInteractor(preferenceHelper = preferenceHelper, apiCalls = apiCalls), CountrySelectMVPInterator {

    override fun getCountries(): Observable<List<Country>> {

        val builder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        val gson = builder.create()
        val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Country::class.java)
        var countries = gson.fromJson<List<Country>>(
            AppUtils.loadJSONFromAsset(
                mContext,
                MyConstants.COUNTRY_DATABASE
            ),
            type
        )
        return Observable.just(countries)
    }

}