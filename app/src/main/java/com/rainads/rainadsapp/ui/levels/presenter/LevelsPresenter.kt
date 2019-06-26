package com.rainads.rainadsapp.ui.levels.presenter

import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.levels.interactor.ILevelsInteractor
import com.rainads.rainadsapp.ui.levels.view.LevelsView
import com.rainads.rainadsapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class LevelsPresenter<V : LevelsView, I : ILevelsInteractor>
@Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), ILevelsPresenter<V, I> {

    override fun onViewPrepared() {
        getView()?.showProgress()
        interactor?.let {
            compositeDisposable.add(it.getReferralList()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe { user ->
                    getView()?.hideProgress()

                    val referralList = ArrayList<String>()

                    referralList.add(user.referralsNetwork?.networkLvl1?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl2?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl3?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl4?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl5?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl6?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl7?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl8?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl9?.size.toString())
                    referralList.add(user.referralsNetwork?.networkLvl10?.size.toString())

                    getView()?.displayLevels(referralList)
                })
        }
    }

}