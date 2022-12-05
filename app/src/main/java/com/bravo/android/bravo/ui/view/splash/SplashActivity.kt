package com.bravo.android.bravo.ui.view.splash

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.ActivityMainBinding
import com.bravo.android.bravo.databinding.ActivitySplashBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.join.terms.TermsViewModel
import com.bravo.android.bravo.ui.view.main.MainViewModel

class SplashActivity  : BaseVmActivity<ActivitySplashBinding>(
    R.layout.activity_splash,
    SplashViewModel::class.java
) {
    override val viewModel by lazy { vm as SplashViewModel }
    override val toolbarId = 0

    override fun initActivity() {

    }

}