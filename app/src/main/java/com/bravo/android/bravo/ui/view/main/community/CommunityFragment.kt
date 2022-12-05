package com.bravo.android.bravo.ui.view.main.community

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentCommunityBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.join.terms.TermsViewModel

class CommunityFragment : BaseVmFragment<FragmentCommunityBinding>(
    R.layout.fragment_community,
    CommunityViewModel::class.java
) {

    override val viewModel by lazy { vm as CommunityViewModel }

    override fun initFragment() {

    }

}