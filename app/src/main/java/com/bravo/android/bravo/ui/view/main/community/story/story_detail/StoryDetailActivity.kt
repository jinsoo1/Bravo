package com.bravo.android.bravo.ui.view.main.community.story.story_detail

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.data.common.model.StoryCommentListData
import com.bravo.android.bravo.databinding.ActivityStoryDetailBinding
import com.bravo.android.bravo.databinding.ItemStoryCommentListBinding
import com.bravo.android.bravo.databinding.ItemStoryListBinding
import com.bravo.android.bravo.util.EventObserver
import com.bravo.android.bravo.util.MediaUtil
import com.bravo.android.bravo.util.ext.StringExt.removeHTMLTag
import gun0912.tedbottompicker.TedRxBottomPicker
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

class StoryDetailActivity : BaseVmActivity<ActivityStoryDetailBinding>(
    R.layout.activity_story_detail,
    StoryDetailViewModel::class.java
) {
    override val viewModel by lazy { vm as StoryDetailViewModel }
    override val toolbarId = 0

    private val feedToken by lazy {
        intent.getStringExtra("feedToken")
    }

    override fun initActivity() {

        viewModel.setObserves()

        feedToken?.let { viewModel.settingFeedToken(it) }

        binding.rvComment.adapter = StoryCommentList(viewModel)
    }


    private fun StoryDetailViewModel.setObserves(){

        action.observe(this@StoryDetailActivity, EventObserver{
            when(it){
                StoryDetailViewModel.StoryDetailAction.SEND ->{
                    if(comment.value!!.isEmpty()){
                        App.toast("댓글을 입력하세요.")?.show()
                    }else{
                        postCommentToStory()
                    }
                }
                StoryDetailViewModel.StoryDetailAction.GALLERY -> requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        })

        feedToken.observe(this@StoryDetailActivity, Observer {
            getDetailStoryData()
            getStoryCommentData()
            binding.scrollview.smoothScrollTo(0,0)
        })

        detailData.observe(this@StoryDetailActivity, Observer {

            runBlocking {
                binding.tvContent.loadDataWithBaseURL("a", it.content, "text/html", "UTF-8", null)
                binding.tvBefores.text = removeHTMLTag(it.beforeS.content)
                binding.tvAfters.text = removeHTMLTag(it.afterS.content)

            }
            setProgress()
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
            App.toast("이미지를 등록할 수 없습니다.")?.show()
        }
    }


    private fun imagePicker(){
        singleImageDisposable = TedRxBottomPicker.with(this)
            .setSelectedUri(selectedUri)
            .setPeekHeight(1200)
            .show()
            .subscribe({uri ->
                viewModel.commentImg.value = uri.toString()
                viewModel.photo = MediaUtil.getPath(this, uri)
            }, Throwable::printStackTrace)

    }



}

class StoryCommentList(vm : StoryDetailViewModel) : BaseRecyclerAdapter<StoryCommentListData, ItemStoryCommentListBinding>(
    R.layout.item_story_comment_list, vm
)