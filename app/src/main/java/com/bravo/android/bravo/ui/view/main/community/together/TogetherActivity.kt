package com.bravo.android.bravo.ui.view.main.community.together

import android.widget.Toast
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.databinding.ActivityTogetherBinding
import com.bravo.android.bravo.util.EventObserver

class TogetherActivity : BaseVmActivity<ActivityTogetherBinding>(
    R.layout.activity_together,
    TogetherViewModel::class.java
){
    override val viewModel by lazy { vm as TogetherViewModel }
    override val toolbarId = 0

    override fun initActivity() {
        viewModel.setObserves()
    }


    private fun TogetherViewModel.setObserves(){
        action.observe(this@TogetherActivity, EventObserver{
            when(it){
                TogetherViewModel.TogetherAction.FINISH -> {
                    toast("함께하기 개설 성공")?.show()
                    finish()
               }
            }
        })
    }
}