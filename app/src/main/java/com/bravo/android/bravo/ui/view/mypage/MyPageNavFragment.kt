package com.bravo.android.bravo.ui.view.mypage

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentHomeBinding
import com.bravo.android.bravo.databinding.FragmentMypageNavBinding
import com.bravo.android.bravo.ui.view.main.home.HomeViewModel

class MyPageNavFragment : BaseVmFragment<FragmentMypageNavBinding>(
    R.layout.fragment_mypage_nav,
    MyPageNavViewModel::class.java
) {

    override val viewModel by lazy { vm as MyPageNavViewModel }

    override fun initFragment() {

    }

}