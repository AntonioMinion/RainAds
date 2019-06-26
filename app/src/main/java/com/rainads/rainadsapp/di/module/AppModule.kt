package com.rainads.rainadsapp.di.module

import android.app.Application
import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.ApiHeader
import com.rainads.rainadsapp.data.network.AppApiHelper
import com.rainads.rainadsapp.data.preferences.AppPreferenceHelper
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.di.PreferenceInfo
import com.rainads.rainadsapp.util.MyConstants
import com.rainads.rainadsapp.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    /*   @Provides
       @Singleton
       internal fun provideAppDatabase(context: Context): AppDatabase =
           Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.APP_DB_NAME).build()*/

    @Provides
    @PreferenceInfo
    internal fun provideprefFileName(): String = MyConstants.PREF_NAME

    @Provides
    @Singleton
    internal fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper

    @Provides
    internal fun provideProtectedApiHeader(preferenceHelper: PreferenceHelper)
            : ApiHeader.ProtectedApiHeader = ApiHeader.ProtectedApiHeader(
        accessToken = preferenceHelper.getAccessToken()
    )

    @Provides
    @Singleton
    internal fun provideApiHelper(appApiHelper: AppApiHelper): ApiCalls = appApiHelper

    /*@Provides
    @Singleton
    internal fun provideQuestionRepoHelper(appDatabase: AppDatabase): QuestionRepo = QuestionRepository(appDatabase.questionsDao())

    @Provides
    @Singleton
    internal fun provideOptionsRepoHelper(appDatabase: AppDatabase): OptionsRepo = OptionsRepository(appDatabase.optionsDao())*/

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()

}