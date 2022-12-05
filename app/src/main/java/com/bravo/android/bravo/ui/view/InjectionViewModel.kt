package com.bravo.android.bravo.ui.view

import com.bravo.android.bravo.ui.view.join.JoinViewModel
import com.bravo.android.bravo.ui.view.join.address.AddressViewModel
import com.bravo.android.bravo.ui.view.join.nickname.NicknameViewModel
import com.bravo.android.bravo.ui.view.join.terms.TermsViewModel
import com.bravo.android.bravo.ui.view.main.MainViewModel
import com.bravo.android.bravo.ui.view.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    //Main
    viewModel { MainViewModel() }
    viewModel { SplashViewModel() }


    //Join
    viewModel { AddressViewModel() }
    viewModel { NicknameViewModel() }
    viewModel { TermsViewModel() }
    viewModel { JoinViewModel() }


}