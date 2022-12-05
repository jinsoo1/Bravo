package com.bravo.android.bravo.ui.view.join

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.databinding.ActivityJoinBinding
import com.bravo.android.bravo.databinding.ActivityMainBinding
import com.bravo.android.bravo.ui.view.main.MainViewModel

class JoinActivity: BaseVmActivity<ActivityJoinBinding>(
    R.layout.activity_join,
    JoinViewModel::class.java
) {
    override val viewModel by lazy { vm as JoinViewModel }
    override val toolbarId = 0

    override fun initActivity() {

    }

}