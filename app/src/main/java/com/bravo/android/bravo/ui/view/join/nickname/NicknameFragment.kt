package com.bravo.android.bravo.ui.view.join.nickname

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentNicknameBinding

class NicknameFragment : BaseVmFragment<FragmentNicknameBinding>(
    R.layout.fragment_nickname,
    NicknameViewModel::class.java
) {

    override val viewModel by lazy { vm as NicknameViewModel }

    override fun initFragment() {

    }

}