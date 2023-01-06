package com.bravo.android.bravo.ui.view.mypage.block

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentBlockBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.mypage.terms.TermsViewModel

class BlockFragment: BaseVmFragment<FragmentBlockBinding>(
    R.layout.fragment_block,
    BlockViewModel::class.java
) {

    override val viewModel by lazy { vm as BlockViewModel }

    override fun initFragment() {

    }

}