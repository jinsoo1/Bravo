package com.bravo.android.bravo.ui.view.mypage.mypost

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentPostBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.mypage.terms.TermsViewModel

class PostFragment: BaseVmFragment<FragmentPostBinding>(
    R.layout.fragment_post,
    PostViewModel::class.java
) {

    override val viewModel by lazy { vm as PostViewModel }

    override fun initFragment() {

    }

}