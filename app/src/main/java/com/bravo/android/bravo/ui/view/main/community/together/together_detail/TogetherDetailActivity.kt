package com.bravo.android.bravo.ui.view.main.community.together.together_detail

import androidx.lifecycle.Observer
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseBottomSheetDialog
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.data.common.model.TogetherDetailData
import com.bravo.android.bravo.data.common.model.TogetherUserListData
import com.bravo.android.bravo.data.local.pref.PreferencesController
import com.bravo.android.bravo.databinding.ActivityTogetherDetailBinding
import com.bravo.android.bravo.databinding.DialogTogetherJoinBinding
import com.bravo.android.bravo.databinding.ItemTogetherUserListBinding
import com.bravo.android.bravo.util.EventObserver
import com.bravo.android.bravo.util.ext.StringExt.removeLastNchars
import kotlinx.coroutines.coroutineScope

class TogetherDetailActivity : BaseVmActivity<ActivityTogetherDetailBinding>(
    R.layout.activity_together_detail,
    TogetherDetailViewModel::class.java
) {
    override val viewModel by lazy { vm as TogetherDetailViewModel }
    override val toolbarId = 0

    private val joinTogetherBTDialog by lazy { JoinTogetherBTDialog(viewModel) }

    private val togetherToken by lazy {
        intent.getStringExtra("togetherToken")
    }

    override fun initActivity() {

        viewModel.setObserves()
        //viewModel.settingTogetherToken("U9Swj0JJ3O")

        binding.apply {
            rvUserList.apply {
                adapter = TogetherUserListAdapter(viewModel)
            }
        }


        togetherToken?.let { viewModel.settingTogetherToken(it) }
    }


    private fun TogetherDetailViewModel.setObserves(){

        action.observe(this@TogetherDetailActivity, EventObserver {
            when(it){
                TogetherDetailViewModel.TogetherDetailAction.BOTTOM_SHEET ->{
                    if(!togetherDetail.value!!.isAuth){
                        joinTogetherBTDialog.show(supportFragmentManager, "")
                    }

                }
                TogetherDetailViewModel.TogetherDetailAction.DISMISS ->{
                    joinTogetherBTDialog.dismiss()
                }
                TogetherDetailViewModel.TogetherDetailAction.JOIN ->{
                    if(togetherDetail.value!!.isAuth){
                        toast("?????? ????????????????????????.")?.show()
                    }
                }
            }
        })

        togetherToken.observe(this@TogetherDetailActivity, Observer{
            getTogetherDetail(it)
            getUserList(it)
        })

        togetherDetail.observe(this@TogetherDetailActivity, Observer {
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
                    when(it.togetherGender){
                        "??????" -> gender = "??????"
                        "??????" -> gender = "?????????"
                        "??????" -> gender = "?????????"
                    }
                    if(!it.ageState){
                        text = "?????? ??????,$gender"
                    }else{
                        text = it.firstAge.toString() + "??????" + " ~ " + it.secondAge.toString() + "??????" + "," + gender
                    }
                }
                tvDayList.apply {
                    if(it.activityState){

                        var date = it.date
                        date = date.replace("???", "??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")

                        text = removeLastNchars(date, 2)
                    }else{
                        text = it.date
                    }

                }
                tvLocation.apply {
                    text = PreferencesController.userInfoPref.region.toString()
                }
                tvContent.loadDataWithBaseURL("a",
                    it.content, "text/html", "UTF-8", null)
            }
        })
    }
}

class TogetherUserListAdapter(vm : TogetherDetailViewModel) : BaseRecyclerAdapter<TogetherUserListData, ItemTogetherUserListBinding>(
    R.layout.item_together_user_list, vm
)

class JoinTogetherBTDialog(vm : TogetherDetailViewModel) : BaseBottomSheetDialog<DialogTogetherJoinBinding>(
    R.layout.dialog_together_join, vm
)

