package com.bravo.android.bravo.ui.view.join.address

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.data.common.model.Region
import com.bravo.android.bravo.databinding.FragmentAddressBinding
import com.bravo.android.bravo.databinding.ItemCityListBinding
import com.bravo.android.bravo.databinding.ItemRegionListBinding
import com.bravo.android.bravo.ui.view.join.JoinViewModel
import com.bravo.android.bravo.util.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AddressFragment : BaseVmFragment<FragmentAddressBinding>(
    R.layout.fragment_address,
    AddressViewModel::class.java
) {

    override val viewModel by lazy { vm as AddressViewModel }

    private val activityViewModel by sharedViewModel<JoinViewModel>()
    override fun initFragment() {

        viewModel.setObserves()

        binding.apply {
            rvCity.apply {
                adapter = AddressCityAdapter(viewModel)
            }
            rvDistrict.apply {
                adapter = AddressRegionAdapter(viewModel)
            }
        }

    }

    private fun AddressViewModel.setObserves(){
        action.observe(viewLifecycleOwner, EventObserver{
            when(it){
                AddressViewModel.AddressActions.NEXT ->{
                    region.value?.let {
                        activityViewModel.settingRegionToken(region.value!!.regionToken)
                        findNavController().navigate(R.id.action_fragment_address_to_addInfoFragment) } ?: toast("지역을 선택해주세요.")?.show()
//                    if(region.value == null){
//                        toast("지역을 선택해주세요.")?.show()
//                    }else{
//
//                    }
                }
            }
        })
        city.observe(this@AddressFragment, Observer {
            getRegionList()
        })
    }

}


class AddressCityAdapter(vm: AddressViewModel) : BaseRecyclerAdapter<String, ItemCityListBinding>(
        R.layout.item_city_list, vm
)

class AddressRegionAdapter(vm : AddressViewModel) : BaseRecyclerAdapter<Region, ItemRegionListBinding>(
    R.layout.item_region_list, vm
)