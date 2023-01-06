package com.bravo.android.bravo.ui.view.main.community.together.fragment.complete

import android.webkit.WebChromeClient
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.data.local.pref.PreferencesController
import com.bravo.android.bravo.databinding.FragmentTogetherCompleteBinding
import com.bravo.android.bravo.ui.view.main.community.together.TogetherViewModel
import com.bravo.android.bravo.util.EventObserver
import com.bravo.android.bravo.util.ext.StringExt.removeLastNchars
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TogetherCompleteFragment : BaseVmFragment<FragmentTogetherCompleteBinding>(
    R.layout.fragment_together_complete,
    TogetherCompleteViewModel::class.java
){
    override val viewModel by lazy { vm as TogetherCompleteViewModel }

    val activityViewModel by sharedViewModel<TogetherViewModel>()

    override fun initFragment() {

        viewModel.setObserves()

        binding.apply {
            tvContent.apply {
                settings.apply {
                    javaScriptEnabled = true
                    allowContentAccess = true
                    allowFileAccess = true
                }

            }

            tvGender.apply {
                var gender : String = ""
                when(activityViewModel.gender.value){
                    "무관" -> gender = "모두"
                    "남성" -> gender = "남자만"
                    "여성" -> gender = "여자만"
                }
                if(activityViewModel.ageState.value == 0){

                    text = "모든 연령," + gender
                }else{
                    text = activityViewModel.firstAge.value.toString() + "년생" + " ~ " + activityViewModel.secondAge.value.toString() + "년생" + "," + gender
                }
            }
            tvDayList.apply {
                if(activityViewModel.activityState.value == 1){
                    var date = activityViewModel.date.value!!
                    date = date.replace("월", "월 /")
                    date = date.replace("화", " 화 /")
                    date = date.replace("수", " 수 /")
                    date = date.replace("목", " 목 /")
                    date = date.replace("금", " 금 /")
                    date = date.replace("토", " 토 /")
                    date = date.replace("일", " 일 /")



                    text = removeLastNchars(date, 2)
                }else{
                    text = activityViewModel.date.value
                }

            }
            tvLocation.apply {
                text = PreferencesController.userInfoPref.region.toString()
            }

            tvTitle.apply {
                text = activityViewModel.title.value
            }
        }

        activityViewModel.content.value?.let {
            binding.tvContent.loadDataWithBaseURL("a",
                it, "text/html", "UTF-8", null)
        }


    }

    private fun TogetherCompleteViewModel.setObserves(){
        action.observe(this@TogetherCompleteFragment, EventObserver{
            when(it){
                TogetherCompleteViewModel.TogetherCompleteAction.COMPLETE ->{
                    activityViewModel.saveTogetherData()
                }
            }
        })
    }


}