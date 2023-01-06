package com.bravo.android.bravo.ui.view.main.community.together.fragment.first

import androidx.navigation.fragment.findNavController
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentTogetherFirstBinding
import com.bravo.android.bravo.ui.view.main.community.together.TogetherViewModel
import com.bravo.android.bravo.util.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TogetherFirstFragment : BaseVmFragment<FragmentTogetherFirstBinding>(
    R.layout.fragment_together_first,
    TogetherFirstViewModel::class.java
) {
    override val viewModel by lazy { vm as TogetherFirstViewModel }

    val activityViewModel by sharedViewModel<TogetherViewModel>()

    override fun initFragment() {
        viewModel.setObserves()
    }


    private fun TogetherFirstViewModel.setObserves(){
        actionTogether.observe(this@TogetherFirstFragment, EventObserver{
            when(it){
                TogetherFirstViewModel.TogetherFirstAction.EXERCISE -> settingCategory("운동")

                TogetherFirstViewModel.TogetherFirstAction.HOBBY -> settingCategory("취미")

                TogetherFirstViewModel.TogetherFirstAction.SOCIAL -> settingCategory("사교")

                TogetherFirstViewModel.TogetherFirstAction.FAMOUS -> settingCategory("맛집")

                TogetherFirstViewModel.TogetherFirstAction.PET -> settingCategory("반려동물")

                TogetherFirstViewModel.TogetherFirstAction.ETC -> settingCategory("기타")

                TogetherFirstViewModel.TogetherFirstAction.NEXT ->{
                    activityViewModel.settingCategory(category.value!!)
                    findNavController().navigate(R.id.action_togetherFirstFragment_to_togetherSecondFragment)
                }

            }

        })
    }
}