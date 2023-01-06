package com.bravo.android.bravo.ui.view.mypage.setting

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentSettingBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.mypage.terms.TermsViewModel

class SettingFragment: BaseVmFragment<FragmentSettingBinding>(
    R.layout.fragment_setting,
    SettingViewModel::class.java
) {

    override val viewModel by lazy { vm as SettingViewModel }

    override fun initFragment() {

    }

}