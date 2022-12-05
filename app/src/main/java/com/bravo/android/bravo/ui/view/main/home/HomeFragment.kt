package com.bravo.android.bravo.ui.view.main.home

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentHealingInfoBinding
import com.bravo.android.bravo.databinding.FragmentHomeBinding
import com.bravo.android.bravo.ui.view.main.healinginfo.HealingViewModel

class HomeFragment : BaseVmFragment<FragmentHomeBinding>(
    R.layout.fragment_home,
    HomeViewModel::class.java
) {

    override val viewModel by lazy { vm as HomeViewModel }

    override fun initFragment() {

    }

}