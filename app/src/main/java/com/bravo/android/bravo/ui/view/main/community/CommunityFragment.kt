package com.bravo.android.bravo.ui.view.main.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseBottomSheetDialog
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.data.common.model.StoryListData
import com.bravo.android.bravo.data.local.pref.PreferencesController
import com.bravo.android.bravo.databinding.*
import com.bravo.android.bravo.ui.view.main.community.story.story_detail.StoryDetailActivity
import com.bravo.android.bravo.ui.view.main.community.story.story_write.StoryWriteActivity
import com.bravo.android.bravo.ui.view.main.community.together.TogetherActivity
import com.bravo.android.bravo.ui.view.main.community.together.fragment.second.TogetherImageList
import com.bravo.android.bravo.ui.view.main.community.together.together_detail.TogetherDetailActivity
import com.bravo.android.bravo.util.EventObserver
import com.bravo.android.bravo.util.ext.StringExt
import com.bravo.android.bravo.util.ext.StringExt.removeHTMLTag
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.textColor

class CommunityFragment : BaseVmFragment<FragmentCommunityBinding>(
    R.layout.fragment_community,
    CommunityViewModel::class.java
) {

    override val viewModel by lazy { vm as CommunityViewModel }
    private val categoryBTDialog by lazy { StoryCategoryBTDialog(viewModel) }
    private val writeBTDialog by lazy { StoryWriteBTDialog(viewModel) }

    var openFlag = false

    override fun initFragment() {

        viewModel.setObserves()
        binding.apply {

            layoutSort.setOnClickListener {
                categoryBTDialog.show(requireActivity().supportFragmentManager, "")
            }

            cdStory.apply {
                startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close))
                isClickable = false
                setOnClickListener {
                    writeBTDialog.show(requireActivity().supportFragmentManager, "")
                }
                visibility = View.GONE
            }
            cdTogether.apply {
                startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close))
                isClickable = false
                setOnClickListener {
                    startActivity(
                        intentFor<TogetherActivity>()
                    )
                }
                visibility = View.GONE
            }

            fbFloating.apply {
                setOnClickListener{
                    toggleFab()
                }
            }

            vBack.apply {
                visibility = View.GONE
                setOnClickListener {  }
            }

            rvStory.apply {
                adapter = StoryListAdapter(viewModel)
            }

        }



    }

    private fun CommunityViewModel.setObserves(){

        actionBts.observe(this@CommunityFragment, EventObserver{
            when(it){
                CommunityViewModel.CommunityAction.EXERCISE -> {
                    if(filterState.value!!){
                        settingCategory("??????")
                    }else{
                        settingCategory("??????")
                    }

                    categoryBTDialog.dismiss()
                }
                CommunityViewModel.CommunityAction.HOBBY -> {
                    if(filterState.value!!){
                        settingCategory("??????")
                    }else{
                        settingCategory("??????")
                    }

                    categoryBTDialog.dismiss()
                }
                CommunityViewModel.CommunityAction.SOCIAL -> {
                    if(filterState.value!!){
                        settingCategory("??????")
                    }else{
                        settingCategory("??????")
                    }

                    categoryBTDialog.dismiss()
                }
                CommunityViewModel.CommunityAction.FAMOUS -> {
                    if(filterState.value!!){
                        settingCategory("??????")
                    }else{
                        settingCategory("??????")
                    }

                    categoryBTDialog.dismiss()
                }
                CommunityViewModel.CommunityAction.PET -> {
                    if(filterState.value!!){
                        settingCategory("????????????")
                    }else{
                        settingCategory("??????")
                    }

                    categoryBTDialog.dismiss()
                }
                CommunityViewModel.CommunityAction.ETC -> {
                    if(filterState.value!!){
                        settingCategory("??????")
                    }else{
                        settingCategory("??????")
                    }

                    categoryBTDialog.dismiss()
                }
                CommunityViewModel.CommunityAction.X -> categoryBTDialog.dismiss()
                CommunityViewModel.CommunityAction.MAIN_X -> {
                    settingCategory("????????? ??????")
                    categoryBTDialog.dismiss()
                }

            }
        })

        actionWrite.observe(this@CommunityFragment, EventObserver{
            when(it){
                CommunityViewModel.WriteAction.EXERCISE -> {
                    settingWrite("??????")
                    toggleFab()
                }
                CommunityViewModel.WriteAction.HOBBY -> {
                    settingWrite("??????")
                    toggleFab()
                }
                CommunityViewModel.WriteAction.SOCIAL -> {
                    settingWrite("??????")
                    toggleFab()
                }
                CommunityViewModel.WriteAction.FAMOUS -> {
                    settingWrite("??????")
                    toggleFab()
                }
                CommunityViewModel.WriteAction.PET -> {
                    settingWrite("??????")
                    toggleFab()
                }
                CommunityViewModel.WriteAction.ETC -> {
                    settingWrite("??????")
                    toggleFab()
                }
                CommunityViewModel.WriteAction.X -> {
                    writeBTDialog.dismiss()
                    toggleFab()
                }
            }

        })

        category.observe(this@CommunityFragment, Observer {
            getStoryListData()
        })
        write.observe(this@CommunityFragment, EventObserver {
            startActivity(
                intentFor<StoryWriteActivity>(
                    "category" to it
                )
            )
        })

        action.observe(this@CommunityFragment, EventObserver{
            when(it.state){
                false -> {
                    startActivity(
                        intentFor<StoryDetailActivity>(
                            "feedToken" to it.token
                        )
                    )
                }
                true -> {
                    startActivity(
                        intentFor<TogetherDetailActivity>(
                            "togetherToken" to it.token
                        )
                    )
                }
            }

        })

    }

    private fun toggleFab(){
        if (openFlag){
            binding.cdStory.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close))
            binding.cdTogether.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close))
            binding.cdStory.isClickable = false
            binding.cdTogether.isClickable = false
            openFlag = false
            binding.vBack.visibility = View.GONE
            binding.cdStory.visibility =  View.GONE
            binding.cdTogether.visibility =  View.GONE
        } else {
            binding.cdStory.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open))
            binding.cdTogether.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open))
            binding.cdStory.isClickable = true
            binding.cdTogether.isClickable = true
            openFlag = true
            binding.vBack.visibility = View.VISIBLE
            binding.cdStory.visibility =  View.VISIBLE
            binding.cdTogether.visibility =  View.VISIBLE
        }
    }

}


class StoryListAdapter(val vm : CommunityViewModel) :
    RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder>(){
    private val items = ObservableArrayList<StoryListData>()

    private val TYPE_STORY : Int = 1
    private val TYPE_TOGETHER : Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerAdapter.ViewHolder {
        return  when(viewType){
            TYPE_STORY ->{
                BaseRecyclerAdapter.ViewHolder(DataBindingUtil.inflate<ItemStoryListBinding>(LayoutInflater.from(parent.context),R.layout.item_story_list, parent, false))
            }
            TYPE_TOGETHER ->{
                BaseRecyclerAdapter.ViewHolder(DataBindingUtil.inflate<ItemTogetherListBinding>(LayoutInflater.from(parent.context),R.layout.item_together_list, parent, false))
            }
            else -> throw Exception("Unknow viewType $viewType")
        }

    }

    fun updateItems(updateItems: List<StoryListData>) {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        items.addAll(updateItems)
        notifyItemRangeInserted(0, updateItems.size)
    }


    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        if(items[position].state){
            return TYPE_TOGETHER
        }else{
            return TYPE_STORY
        }
    }

    override fun onBindViewHolder(holder: BaseRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], vm)

        if(items[position].state){
            (holder.binding as ItemTogetherListBinding).apply {
                tvGender.apply {
                    var gender : String = ""
                    when(items[position].togetherGender){
                        "??????" -> gender = "??????"
                        "??????" -> gender = "?????????"
                        "??????" -> gender = "?????????"
                    }
                    if(!items[position].ageState!!){
                        text = "?????? ??????,$gender"
                    }else{
                        text = items[position].firstAge.toString() + "??????" + " ~ " + items[position].secondAge.toString() + "??????" + "," + gender
                    }
                }
                tvDayList.apply {
                    if(items[position].activityState == true){

                        var date = items[position].date
                        date = date!!.replace("???", "??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")
                        date = date.replace("???", " ??? /")

                        text = StringExt.removeLastNchars(date, 2)
                    }else{
                        text = items[position].date
                    }

                }
                tvLocation.apply {
                    text = PreferencesController.userInfoPref.region.toString()
                }
            }
        }
    }


}

//class StoryListAdapter(vm : CommunityViewModel) : BaseRecyclerAdapter<StoryListData, ItemStoryListBinding>(
//    R.layout.item_story_list, vm
//){
//
//    val vms = vm
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        super.onBindViewHolder(holder, position)
//
//        (holder.binding as ItemStoryListBinding).apply {
//            tvContent.text = removeHTMLTag(vms.storyList.value!![position].content)
//        }
//
//    }
//
//}

class StoryCategoryBTDialog(vm : CommunityViewModel) : BaseBottomSheetDialog<DialogBottomsheetStoryCategoryBinding>(
    R.layout.dialog_bottomsheet_story_category, vm
){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnStory.apply {
                setOnClickListener {
                    vm!!.setOnFilterState()
                    textColor = ContextCompat.getColor(requireContext(),R.color.grey_5959)
                    btnTogether.textColor = ContextCompat.getColor(requireContext(),R.color.grey_B4B4)
                    tv1.text = "?????? ?????? ?????????????"
                    tv11.text = "??????"
                    tv2.text = "?????? ???, ?????? ????????????"
                    tv22.text = "??????"
                    tv3.text = "?????? ?????? ?????????"
                    tv33.text = "??????"
                    tv4.text = "????????? ????????????"
                    tv44.text = "??????"
                    tv5.text = "?????? ?????? ?????? ?????????"
                    tv55.text = "??????"
                    tv6.text = "?????? ???????????? ????????????"
                    tv66.text = "??????"
                }

            }
            btnTogether.apply {
                setOnClickListener {
                    vm!!.setOnFilterState()
                    textColor = ContextCompat.getColor(requireContext(),R.color.grey_5959)
                    btnStory.textColor = ContextCompat.getColor(requireContext(),R.color.grey_B4B4)
                    tv1.text = "??????, ??????, ??????, ?????????"
                    tv11.text = "??????"
                    tv2.text = "?????? ?????? ????????????"
                    tv22.text = "??????"
                    tv3.text = "????????? ????????? ????????? ???"
                    tv33.text = "??????"
                    tv4.text = "???????????? ?????? ?????????"
                    tv44.text = "??????"
                    tv5.text = "???????????? ????????? ????????????"
                    tv55.text = "????????????"
                    tv6.text = "??? ?????? ????????? ?????? ???"
                    tv66.text = "??????"
                }
            }
        }
    }
}

class StoryWriteBTDialog(vm : CommunityViewModel) : BaseBottomSheetDialog<DialogBottomsheetStoryWriteBinding>(
    R.layout.dialog_bottomsheet_story_write, vm
)
