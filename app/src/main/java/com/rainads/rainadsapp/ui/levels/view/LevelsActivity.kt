package com.rainads.rainadsapp.ui.levels.view

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.ui.base.view.BaseActivity
import com.rainads.rainadsapp.ui.levels.interactor.ILevelsInteractor
import com.rainads.rainadsapp.ui.levels.presenter.LevelsPresenter
import kotlinx.android.synthetic.main.activity_referral_list.*
import kotlinx.android.synthetic.main.custom_back_button.*
import javax.inject.Inject

class LevelsActivity : BaseActivity(), LevelsView {

    @Inject
    internal lateinit var levelsAdapter: LevelsAdapter
    @Inject
    internal lateinit var layoutManager: LinearLayoutManager
    @Inject
    internal lateinit var presenter: LevelsPresenter<LevelsView, ILevelsInteractor>

    override fun onFragmentAttached() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFragmentDetached(tag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral_list)
        presenter.onAttach(this)
        setupList()
        btnBack.setOnClickListener { onBackPressed() }
    }

    private fun setupList() {
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNetwork.layoutManager = layoutManager
        recyclerViewNetwork.itemAnimator = DefaultItemAnimator()
        recyclerViewNetwork.adapter = levelsAdapter
        presenter.onViewPrepared()
    }

    override fun displayLevels(referralList: List<String>?) {
        referralList?.let {
            levelsAdapter.addToList(it)
        }
    }

}
