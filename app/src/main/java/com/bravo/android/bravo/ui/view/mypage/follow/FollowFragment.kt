package com.bravo.android.bravo.ui.view.mypage.follow

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentFollowBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.mypage.terms.TermsViewModel

class FollowFragment: BaseVmFragment<FragmentFollowBinding>(
    R.layout.fragment_follow,
    FollowViewModel::class.java
) {

    override val viewModel by lazy { vm as FollowViewModel }

    override fun initFragment() {

    }

}