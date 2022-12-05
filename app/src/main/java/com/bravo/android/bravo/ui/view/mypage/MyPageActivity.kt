package com.bravo.android.bravo.ui.view.mypage

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.databinding.ActivityMainBinding
import com.bravo.android.bravo.databinding.ActivityMypageBinding
import com.bravo.android.bravo.ui.view.main.MainViewModel

class MyPageActivity: BaseVmActivity<ActivityMypageBinding>(
    R.layout.activity_mypage,
    MyPageViewModel::class.java
) {
    override val viewModel by lazy { vm as MyPageViewModel }
    override val toolbarId = 0

    override fun initActivity() {

    }

}