package com.bravo.android.bravo.ui.view.join.addinfo

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentAddinfoBinding
import com.bravo.android.bravo.databinding.FragmentAddressBinding
import com.bravo.android.bravo.ui.view.join.address.AddressViewModel

class AddInfoFragment : BaseVmFragment<FragmentAddinfoBinding>(
    R.layout.fragment_addinfo,
    AddInfoViewModel::class.java
) {

    override val viewModel by lazy { vm as AddInfoViewModel }

    override fun initFragment() {

    }

}