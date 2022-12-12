package com.bravo.android.bravo.ui.view.join.addinfo

import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentAddinfoBinding
import com.bravo.android.bravo.databinding.FragmentAddressBinding
import com.bravo.android.bravo.databinding.ItemCityListBinding
import com.bravo.android.bravo.databinding.ItemYearListBinding
import com.bravo.android.bravo.ui.view.join.JoinViewModel
import com.bravo.android.bravo.ui.view.join.address.AddressViewModel
import com.bravo.android.bravo.ui.view.join.nickname.NicknameViewModel
import com.bravo.android.bravo.util.EventObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AddInfoFragment : BaseVmFragment<FragmentAddinfoBinding>(
    R.layout.fragment_addinfo,
    AddInfoViewModel::class.java
) {

    override val viewModel by lazy { vm as AddInfoViewModel }
    private val activityViewModel by sharedViewModel<JoinViewModel>()
    override fun initFragment() {
        viewModel.setObserves()


        binding.apply {
            rvYear.apply {
                adapter = YearListAdapter(viewModel)
            }
        }



    }

    private fun AddInfoViewModel.setObserves(){

        action.observe(this@AddInfoFragment, EventObserver{
            when(it){
                AddInfoViewModel.AddInfoAction.NEXT ->{
                    if(gender.value == 2){
                        toast("성별을 선택하세요.")?.show()
                    }else if(tvBirth.value == null){
                        toast("출생년도를 선택하세요")?.show()
                    }else{
                        activityViewModel.settingAddInfo(gender.value!!, tvBirth.value!!.toInt())
                        findNavController().navigate(R.id.action_addInfoFragment_to_fragment_terms)

                    }
                }
            }
        })

        birth.observe(this@AddInfoFragment, Observer {
            viewModelScope.launch {
                delay(100)
                if(it.size != 0){
                    binding.rvYear.scrollToPosition(60)
                }
            }
        })
    }




}

class YearListAdapter(vm : AddInfoViewModel) : BaseRecyclerAdapter<String, ItemYearListBinding>(
    R.layout.item_year_list, vm
)