package com.bravo.android.bravo.ui.view.main.healinginfo

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentCommunityBinding
import com.bravo.android.bravo.databinding.FragmentHealingInfoBinding
import com.bravo.android.bravo.ui.view.main.community.CommunityViewModel

class HealingFragment : BaseVmFragment<FragmentHealingInfoBinding>(
    R.layout.fragment_healing_info,
    HealingViewModel::class.java
) {

    override val viewModel by lazy { vm as HealingViewModel }

    override fun initFragment() {

    }

}