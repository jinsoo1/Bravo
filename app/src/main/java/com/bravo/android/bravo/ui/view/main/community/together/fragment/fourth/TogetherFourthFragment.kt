package com.bravo.android.bravo.ui.view.main.community.together.fragment.fourth

import androidx.navigation.fragment.findNavController
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentTogetherFourthBinding
import com.bravo.android.bravo.ui.view.main.community.together.TogetherViewModel
import com.bravo.android.bravo.util.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TogetherFourthFragment : BaseVmFragment<FragmentTogetherFourthBinding>(
    R.layout.fragment_together_fourth,
    TogetherFourthViewModel::class.java
) {
    override val viewModel by lazy { vm as TogetherFourthViewModel }

    val activityViewModel by sharedViewModel<TogetherViewModel>()

    override fun initFragment() {

        viewModel.setObserves()

    }

    private fun TogetherFourthViewModel.setObserves(){
        action.observe(this@TogetherFourthFragment, EventObserver{
            when(it){
                TogetherFourthViewModel.TogetherFourthAction.NEXT ->{
                    if (ageState.value!!){
                        if(firstAge.value == null || secondAge.value == null){
                            toast("나이를 설정해주세요.")?.show()
                            return@EventObserver
                        }
                    }
                    activityViewModel.settingGenderAge(gender.value!!, ageState.value!!, firstAge.value ?: null, secondAge.value ?: null)
                    findNavController().navigate(R.id.action_togetherFourthFragment_to_togetherFifthFragment)
                }
            }
        })
    }
}