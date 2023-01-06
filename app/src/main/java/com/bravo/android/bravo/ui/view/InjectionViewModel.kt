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
    viewModel { HomeViewModel(get()) }
    viewModel { HealingViewModel(get()) }
    viewModel { CommunityViewModel(get()) }



    //healing
    viewModel { HealingDetailViewModel(get()) }

    //Community
    viewModel { StoryWriteViewModel(get()) }
    viewModel { StoryDetailViewModel(get()) }
    viewModel { TogetherViewModel(get()) }
    viewModel { TogetherFirstViewModel() }
    viewModel { TogetherSecondViewModel() }
    viewModel { TogetherThirdViewModel() }
    viewModel { TogetherFourthViewModel() }
    viewModel { TogetherFifthViewModel() }
    viewModel { TogetherCompleteViewModel() }
    viewModel { TogetherDetailViewModel(get()) }

    //myPage
    viewModel { MyPageViewModel() }
    viewModel { MyPageNavViewModel() }
    viewModel { BlockViewModel() }
    viewModel { FollowViewModel() }
    viewModel { InformationViewModel() }
    viewModel { PostViewModel() }
    viewModel { SettingViewModel() }


}