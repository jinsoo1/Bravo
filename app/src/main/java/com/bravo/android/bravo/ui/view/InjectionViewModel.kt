package com.bravo.android.bravo.ui.view

import com.bravo.android.bravo.ui.view.healing.HealingDetailViewModel
import com.bravo.android.bravo.ui.view.join.JoinViewModel
import com.bravo.android.bravo.ui.view.join.addinfo.AddInfoViewModel
import com.bravo.android.bravo.ui.view.join.address.AddressViewModel
import com.bravo.android.bravo.ui.view.join.nickname.NicknameViewModel
import com.bravo.android.bravo.ui.view.join.terms.TermsViewModel
import com.bravo.android.bravo.ui.view.login.LoginViewModel
import com.bravo.android.bravo.ui.view.main.MainViewModel
import com.bravo.android.bravo.ui.view.main.community.CommunityViewModel
import com.bravo.android.bravo.ui.view.main.community.story.story_detail.StoryDetailViewModel
import com.bravo.android.bravo.ui.view.main.community.story.story_write.StoryWriteViewModel
import com.bravo.android.bravo.ui.view.main.community.together.TogetherViewModel
import com.bravo.android.bravo.ui.view.main.community.together.fragment.complete.TogetherCompleteViewModel
import com.bravo.android.bravo.ui.view.main.community.together.fragment.fifth.TogetherFifthViewModel
import com.bravo.android.bravo.ui.view.main.community.together.fragment.first.TogetherFirstViewModel
import com.bravo.android.bravo.ui.view.main.community.together.fragment.fourth.TogetherFourthViewModel
import com.bravo.android.bravo.ui.view.main.community.together.fragment.second.TogetherSecondViewModel
import com.bravo.android.bravo.ui.view.main.community.together.fragment.third.TogetherThirdViewModel
import com.bravo.android.bravo.ui.view.main.community.together.together_detail.TogetherDetailViewModel
import com.bravo.android.bravo.ui.view.main.healinginfo.HealingViewModel
import com.bravo.android.bravo.ui.view.main.home.HomeViewModel
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