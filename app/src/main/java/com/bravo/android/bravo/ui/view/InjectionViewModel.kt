package com.bravo.android.bravo.ui.view

import com.bravo.android.bravo.ui.view.join.JoinViewModel
import com.bravo.android.bravo.ui.view.join.addinfo.AddInfoViewModel
import com.bravo.android.bravo.ui.view.join.address.AddressViewModel
import com.bravo.android.bravo.ui.view.join.nickname.NicknameViewModel
import com.bravo.android.bravo.ui.view.join.terms.TermsViewModel
import com.bravo.android.bravo.ui.view.login.LoginViewModel
import com.bravo.android.bravo.ui.view.main.MainViewModel
import com.bravo.android.bravo.ui.view.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {


    viewModel { SplashViewModel(get()) }

    viewModel { LoginViewModel() }

    //Join
    viewModel { JoinViewModel() }
    viewModel { NicknameViewModel() }
    viewModel { AddressViewModel(get()) }
    viewModel { AddInfoViewModel() }
    viewModel { TermsViewModel(get()) }

    //Main
    viewModel { MainViewModel() }
}