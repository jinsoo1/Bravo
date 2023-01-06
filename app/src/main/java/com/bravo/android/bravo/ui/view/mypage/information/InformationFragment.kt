package com.bravo.android.bravo.ui.view.mypage.information

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentInformationBinding
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.mypage.terms.TermsViewModel

class InformationFragment : BaseVmFragment<FragmentInformationBinding>(
    R.layout.fragment_information,
    InformationViewModel::class.java
) {

    override val viewModel by lazy { vm as InformationViewModel }

    override fun initFragment() {

    }

}