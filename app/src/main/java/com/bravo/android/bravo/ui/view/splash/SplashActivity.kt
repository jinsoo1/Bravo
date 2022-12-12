package com.bravo.android.bravo.ui.view.splash

import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.ActivityMainBinding
import com.bravo.android.bravo.databinding.ActivitySplashBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.join.JoinActivity
import com.bravo.android.bravo.ui.view.join.terms.TermsViewModel
import com.bravo.android.bravo.ui.view.login.LoginActivity
import com.bravo.android.bravo.ui.view.main.MainActivity
import com.bravo.android.bravo.ui.view.main.MainViewModel
import com.bravo.android.bravo.util.Event
import com.bravo.android.bravo.util.EventObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.intentFor

class SplashActivity  : BaseVmActivity<ActivitySplashBinding>(
    R.layout.activity_splash,
    SplashViewModel::class.java
) {
    override val viewModel by lazy { vm as SplashViewModel }
    override val toolbarId = 0

    override fun initActivity() {
        viewModel.setObserves()
    }

    fun SplashViewModel.setObserves(){

        action.observe(this@SplashActivity, EventObserver { it ->
            viewModelScope.launch {
                delay(1500)
                when(it){
                    SplashViewModel.SplashAction.MOVE_LOGIN ->{
                        startActivity(
                            intentFor<LoginActivity>()
                        )
                        finish()
                    }

                    SplashViewModel.SplashAction.MOVE_MAIN ->{
                        startActivity(
                            intentFor<MainActivity>()
                        )
                        finish()
                    }
                }
            }
        })
    }
}