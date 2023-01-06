package com.bravo.android.bravo.ui.view.main.community.together.fragment.third

import androidx.navigation.fragment.findNavController
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentTogetherThirdBinding
import com.bravo.android.bravo.ui.view.main.community.together.TogetherViewModel
import com.bravo.android.bravo.util.EventObserver
import org.jetbrains.anko.support.v4.find
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TogetherThirdFragment : BaseVmFragment<FragmentTogetherThirdBinding>(
    R.layout.fragment_together_third,
    TogetherThirdViewModel::class.java
) {
    override val viewModel by lazy { vm as TogetherThirdViewModel }

    val activityViewModel by sharedViewModel<TogetherViewModel>()

    override fun initFragment() {


        viewModel.setObserves()



    }


    private fun TogetherThirdViewModel.setObserves(){
        action.observe(this@TogetherThirdFragment, EventObserver{
            when(it){
                TogetherThirdViewModel.TogetherThirdAction.NEXT ->{
                    activityViewModel.settingPeopleNumLocation(peopleNum.value!!, location.value!!)
                    findNavController().navigate(R.id.action_togetherThirdFragment_to_togetherFourthFragment)
                }
            }
        })
    }


}