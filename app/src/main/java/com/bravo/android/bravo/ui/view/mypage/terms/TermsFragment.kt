package com.bravo.android.bravo.ui.view.mypage.terms

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentMypageNavBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.mypage.MyPageNavViewModel

class TermsFragment : BaseVmFragment<FragmentTermsBinding>(
    R.layout.fragment_terms,
    TermsViewModel::class.java
) {

    override val viewModel by lazy { vm as TermsViewModel }

    override fun initFragment() {

    }

}