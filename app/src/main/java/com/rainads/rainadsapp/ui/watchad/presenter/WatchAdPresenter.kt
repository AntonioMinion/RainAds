package com.rainads.rainadsapp.ui.watchad.presenter

import android.os.CountDownTimer
import com.rainads.rainadsapp.ui.base.presenter.BasePresenter
import com.rainads.rainadsapp.ui.watchad.interactor.IWatchAdInteractor
import com.rainads.rainadsapp.ui.watchad.view.WatchAdView
import com.rainads.rainadsapp.util.Handler
import com.rainads.rainadsapp.util.SchedulerProvider
import com.rainads.rainadsapp.util.ToastType
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class WatchAdPresenter<V : WatchAdView, I : IWatchAdInteractor>
@Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), IWatchAdPresenter<V, I> {

    var lastTick: Long = 0L
    var actualDuration: Long = 0L

    private lateinit var countDownTimer: CountDownTimer

    override fun startTimer(adId: String, adDuration: Long) {
        actualDuration = if (lastTick != 0L) lastTick else adDuration * 1000

        countDownTimer = object : CountDownTimer(actualDuration, 10) {
            override fun onFinish() {
                getView()?.onTimerFinished()
                interactor?.let {
                    compositeDisposable.add(
                        it.watchAd(adId)
                            .compose(schedulerProvider.ioToMainObservableScheduler())
                            .subscribe({ msg ->
                                getView()?.onAdWatched(ToastType.SUCCESS, msg)
                            }, { err ->
                                println(err)
                                getView()?.onAdWatched(
                                    ToastType.ERROR,
                                    if (!err.message.isNullOrEmpty()) "" else Handler.getErrorMessage(0)
                                )
                            })
                    )
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                lastTick = millisUntilFinished

                val progress: Int = (adDuration * 1000 - millisUntilFinished).toInt()
                val secondsUntilFinish: String = (millisUntilFinished / 1000).toString()
                getView()?.onTimerTick(progress, secondsUntilFinish)
            }
        }
        countDownTimer.start()
    }

    override fun pauseTimer() {
        countDownTimer.cancel()
    }

}