package com.bravo.android.bravo.ui.view.main

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.databinding.ActivityMainBinding

class MainActivity : BaseVmActivity<ActivityMainBinding>(
    R.layout.activity_main,
    MainViewModel::class.java
) {
    override val viewModel by lazy { vm as MainViewModel }
    override val toolbarId = 0

    override fun initActivity() {

    }

}