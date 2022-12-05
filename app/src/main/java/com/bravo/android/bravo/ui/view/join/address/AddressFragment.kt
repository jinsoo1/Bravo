package com.bravo.android.bravo.ui.view.join.address

import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentAddressBinding
import com.bravo.android.bravo.databinding.FragmentNicknameBinding
import com.bravo.android.bravo.ui.view.join.nickname.NicknameViewModel

class AddressFragment : BaseVmFragment<FragmentAddressBinding>(
    R.layout.fragment_address,
    AddressViewModel::class.java
) {

    override val viewModel by lazy { vm as AddressViewModel }

    override fun initFragment() {

    }

}