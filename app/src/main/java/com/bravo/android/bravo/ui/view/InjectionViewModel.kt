package com.bravo.android.bravo.ui.view

import com.bravo.android.bravo.ui.view.join.JoinViewModel
import com.bravo.android.bravo.ui.view.join.address.AddressViewModel
import com.bravo.android.bravo.ui.view.join.nickname.NicknameViewModel
import com.bravo.android.bravo.ui.view.join.terms.TermsViewModel
import com.bravo.android.bravo.ui.view.main.MainViewModel
import com.bravo.android.bravo.ui.view.mypage.MyPageNavViewModel
import com.bravo.android.bravo.ui.view.mypage.MyPageViewModel
import com.bravo.android.bravo.ui.view.mypage.block.BlockViewModel
import com.bravo.android.bravo.ui.view.mypage.follow.FollowViewModel
import com.bravo.android.bravo.ui.view.mypage.information.InformationViewModel
import com.bravo.android.bravo.ui.view.mypage.mypost.PostViewModel
import com.bravo.android.bravo.ui.view.mypage.setting.SettingViewModel
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


    //myPage
    viewModel { MyPageViewModel() }
    viewModel { MyPageNavViewModel() }
    viewModel { BlockViewModel() }
    viewModel { FollowViewModel() }
    viewModel { InformationViewModel() }
    viewModel { PostViewModel() }
    viewModel { SettingViewModel() }


}