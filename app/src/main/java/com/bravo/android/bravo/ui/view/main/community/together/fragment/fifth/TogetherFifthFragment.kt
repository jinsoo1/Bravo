package com.bravo.android.bravo.ui.view.main.community.together.fragment.fifth

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentTogetherFifthBinding
import com.bravo.android.bravo.ui.view.main.community.together.TogetherViewModel
import com.bravo.android.bravo.util.EventObserver
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.support.v4.find
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TogetherFifthFragment : BaseVmFragment<FragmentTogetherFifthBinding>(
    R.layout.fragment_together_fifth,
    TogetherFifthViewModel::class.java
) {
    override val viewModel by lazy { vm as TogetherFifthViewModel }

    val activityViewModel by sharedViewModel<TogetherViewModel>()

    override fun initFragment() {

        viewModel.setObserves()
    }


    private fun TogetherFifthViewModel.setObserves(){
        action.observe(this@TogetherFifthFragment, EventObserver{
            when(it){
                TogetherFifthViewModel.TogetherFifthAction.NEXT_ONE ->{

                    activityViewModel.settingActivityFalse(month.value!!, day.value!!, time.value!!)

                    activityViewModel.setLogTest()

                    findNavController().navigate(R.id.action_togetherFifthFragment_to_togetherCompleteFragment)
                }
                TogetherFifthViewModel.TogetherFifthAction.NEXT_TWO ->{
                    Log.d("asd23e1we", dayState.value.toString())
                    if(dayState.value!!){
                        if(dayList.value!!.size == 0){
                            toast("선택된 요일이 없습니다.")?.show()
                            return@EventObserver
                        }else{
                            sortDayList()
                        }
                    }

                    activityViewModel.settingActivityTrue(dayState.value!!, dayListState.value!!)

                    activityViewModel.setLogTest()
                    findNavController().navigate(R.id.action_togetherFifthFragment_to_togetherCompleteFragment)
                }
            }
        })
        dayState.observe(this@TogetherFifthFragment, Observer {
            Log.d("asd23e1we", it.toString())
            if(!it){
                settingAllDayList("월화수목금토일")
            }
        })
    }
}