package com.bravo.android.bravo.ui.view.main.community.together.fragment.second

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.persistableBundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentTogetherSecondBinding
import com.bravo.android.bravo.databinding.ItemTogetherImageListBinding
import com.bravo.android.bravo.databinding.ItemTogetherPlusBinding
import com.bravo.android.bravo.ui.view.main.community.together.TogetherViewModel
import com.bravo.android.bravo.util.EventObserver
import com.bravo.android.bravo.util.MediaUtil
import com.bumptech.glide.Glide
import gun0912.tedbottompicker.TedRxBottomPicker
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TogetherSecondFragment : BaseVmFragment<FragmentTogetherSecondBinding>(
    R.layout.fragment_together_second,
    TogetherSecondViewModel::class.java
){
    override val viewModel by lazy { vm as TogetherSecondViewModel }


    val activityViewModel by sharedViewModel<TogetherViewModel>()

    override fun initFragment() {
        viewModel.setObserves()

        binding.apply {
            editor.apply {
                setEditorHeight(246)
                setEditorFontSize(18)
                setEditorFontColor(Color.GRAY)
                setPadding(24, 24, 24, 24)
                setPlaceholder("여기에 내용을 적어주세요.")
                setBackgroundColor(Color.parseColor("#F3F3F3"))
                setOnTextChangeListener {
                    //preview.text = it
                    viewModel.settingUploadHtml(it)
                }
            }
        }

    }


    private fun TogetherSecondViewModel.setObserves(){

        action.observe(this@TogetherSecondFragment, EventObserver{
            when(it){
                TogetherSecondViewModel.TogetherSecondAction.GALLERY -> startLocationPermissionRequest()
                TogetherSecondViewModel.TogetherSecondAction.NEXT -> {
                    if(title.value == null || uploadHtml.value == null){
                        toast("제목과 내용은 필수입니다.")?.show()
                    }else{
                        activityViewModel.settingTitleContent(title.value!!, uploadHtml.value!!, viewModel.photo)
                        activityViewModel.uploadImg = viewModel.uploadImg
                        viewModel.listUrl.value?.clear()
                        findNavController().navigate(R.id.action_togetherSecondFragment_to_togetherThirdFragment)
                    }
                }

            }
        })

        listUrl.observe(this@TogetherSecondFragment, Observer {

                binding.apply {
                    rvTogetherImage.apply {
                        adapter = TogetherImageList(viewModel, it)
                    }
                }

        })

    }



    private var multiImageDisposable: Disposable? = null
    private var selectedUriList : MutableList<Uri> = mutableListOf()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted){

            multiImageDisposable = TedRxBottomPicker.with(requireActivity())
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("완료")
                .setEmptySelectionText("선택된 이미지 없음")
                .setSelectMaxCount(10)
                .setSelectedUriList(selectedUriList)
                .showMultiImage()
                .subscribe({ it ->
                    var its : MutableList<Uri> = mutableListOf()
                    it.forEachIndexed { index, uri ->
                        if(index >= 10) {
                            Log.d("asdassdsdd1", index.toString())
                            toast("사진은 최대 10장입니다.")?.show()
                            return@forEachIndexed
                        }
                        its.add(uri)
                    }
                    selectedUriList = its
                    val uriList : MutableList<String> = mutableListOf()
                    viewModel.uploadImg.clear()

                    runBlocking {
                        launch {
                            selectedUriList.forEach { it ->
                                val delete = "<img src=\"$it\" alt=\"alt\" style=\"max-width:100%; height:auto; margin-top:10px; margin-bottom:10px;\">"
                                if(viewModel.uploadHtml.value?.indexOf(delete) == -1) {
                                    binding.editor.insertImage(
                                        it.toString(),
                                        "alt\" style=\"max-width:100%; height:auto; margin-top:10px; margin-bottom:10px;"
                                    )
                                    viewModel.uploadImg.add(it.toString())
                                    viewModel.photo.add(
                                        MediaUtil.getPath(
                                            requireContext(),
                                            it
                                        )!!)
                                    uriList.add(MediaUtil.getPath(requireContext(), it)!!)
                                }
                            }
                        }
                    }

                    viewModel.settingUriList(uriList)
                    selectedUriList = mutableListOf()

                }, Throwable::printStackTrace)

        }else{
            //권한 획득 거부시
            App.toast("이미지를 등록할 수 없습니다.")?.show()
        }
    }

    // Ex. Launching ACCESS_FINE_LOCATION permission.
    private fun startLocationPermissionRequest() {
        requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

}


class TogetherImageList(val vm : TogetherSecondViewModel, val items : MutableList<String?>) :
    RecyclerView.Adapter<TogetherImageList.ListViewHolder>() {

    private val TYPE_ITEM : Int = 1
    private val TYPE_FOOTER : Int = 2

    class ListViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val view = when(viewType){
            TYPE_ITEM ->{
                LayoutInflater.from(parent.context).inflate(R.layout.item_together_image_list, parent, false)
            }
            TYPE_FOOTER ->{
                LayoutInflater.from(parent.context).inflate(R.layout.item_together_plus, parent, false)
            }
            else -> throw Exception("Unknow viewType $viewType")

        }
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        if(position == items.size) {
            // FOOTER
            holder.layout.setOnClickListener {
                vm.setOnGalleryAction()
            }
        } else {
            // ITEM
            Glide.with(holder.layout)
                .load(items[position])
                .into(holder.layout.findViewById(R.id.iv_image))
        }
    }

    override fun getItemCount() : Int = items.size + 1

    override fun getItemViewType(position: Int): Int =
        when(position){
            position + 1 -> TYPE_FOOTER
            else -> TYPE_ITEM
        }

}