package com.bravo.android.bravo.ui.view.healing

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseBottomSheetDialog
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.data.common.model.HealingCommentListData
import com.bravo.android.bravo.databinding.ActivityHealingDetailBinding
import com.bravo.android.bravo.databinding.DialogBottomsheetReportBinding
import com.bravo.android.bravo.databinding.DialogBottomsheetUserBanBinding
import com.bravo.android.bravo.databinding.ItemHealingCommentListBinding
import com.bravo.android.bravo.util.EventObserver
import com.bravo.android.bravo.util.MediaUtil.Companion.getPath
import gun0912.tedbottompicker.TedRxBottomPicker
import io.reactivex.disposables.Disposable

class HealingDetailActivity : BaseVmActivity<ActivityHealingDetailBinding>(
    R.layout.activity_healing_detail,
    HealingDetailViewModel::class.java
) {
    override val viewModel by lazy { vm as HealingDetailViewModel }
    override val toolbarId = 0

    private val infoToken by lazy {
        intent.getStringExtra("infoToken")
    }

    private val userBanBTDialog by lazy { BanUserBTDialog(viewModel) }
    private val reportBTDialog by lazy { ReportBTDialog(viewModel) }

    override fun initActivity() {
        viewModel.setObserves()

        infoToken?.let { viewModel.settingInfoToken(it) }

        binding.rvComment.adapter = HealingCommentList(viewModel)
    }

    private fun HealingDetailViewModel.setObserves(){

        action.observe(this@HealingDetailActivity, EventObserver{
            when(it){
                HealingDetailViewModel.HealingDetailAction.SEND -> {
                    if(comment.value!!.isEmpty()){
                        toast("댓글을 입력하세요.")?.show()
                    }else{
                        postCommentToHealing()
                    }
                }
                HealingDetailViewModel.HealingDetailAction.GALLERY -> requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                HealingDetailViewModel.HealingDetailAction.CLEAR -> {
                    commentImg.value = null
                    photo = null
                }
                HealingDetailViewModel.HealingDetailAction.REPORT ->{
                    toast("신고하기로이동")?.show()
                }
                HealingDetailViewModel.HealingDetailAction.BAN ->{
                    reportBTDialog.dismiss()
                    userBanBTDialog.show(supportFragmentManager, "")
                }
            }
        })

        infoToken.observe(this@HealingDetailActivity, Observer {
            getDetailHealingData()
            getHealingCommentData()
            binding.scrollview.smoothScrollTo(0,0)
        })

        userProfile.observe(this@HealingDetailActivity, Observer{
            reportBTDialog.show(supportFragmentManager, "")
        })
    }

    private var selectedUri: Uri? = null
    private var singleImageDisposable: Disposable? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){
            imagePicker()
        }else{
            //권한 획득 거부시
            toast("이미지를 등록할 수 없습니다.")?.show()
        }
    }


    private fun imagePicker(){
        singleImageDisposable = TedRxBottomPicker.with(this)
            .setSelectedUri(selectedUri)
            .setPeekHeight(1200)
            .show()
            .subscribe({uri ->
                viewModel.commentImg.value = uri.toString()
                viewModel.photo = getPath(this, uri)
             }, Throwable::printStackTrace)

    }


}


class HealingCommentList(vm : HealingDetailViewModel) : BaseRecyclerAdapter<HealingCommentListData, ItemHealingCommentListBinding>(
    R.layout.item_healing_comment_list, vm
)

class BanUserBTDialog(vm : HealingDetailViewModel) : BaseBottomSheetDialog<DialogBottomsheetUserBanBinding>(
    R.layout.dialog_bottomsheet_user_ban, vm
)

class ReportBTDialog(vm : HealingDetailViewModel) : BaseBottomSheetDialog<DialogBottomsheetReportBinding>(
    R.layout.dialog_bottomsheet_report, vm
)