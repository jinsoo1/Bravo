package com.bravo.android.bravo.ui.view.main.healinginfo

import androidx.lifecycle.Observer
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseBottomSheetDialog
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.data.common.model.HealingListData
import com.bravo.android.bravo.databinding.DialogBottomsheetCategoryBinding
import com.bravo.android.bravo.databinding.FragmentHealingInfoBinding
import com.bravo.android.bravo.databinding.ItemHealingListBinding
import com.bravo.android.bravo.ui.view.healing.HealingDetailActivity
import com.bravo.android.bravo.util.EventObserver
import org.jetbrains.anko.support.v4.intentFor

class HealingFragment : BaseVmFragment<FragmentHealingInfoBinding>(
    R.layout.fragment_healing_info,
    HealingViewModel::class.java
) {

    override val viewModel by lazy { vm as HealingViewModel }
    private val categoryBTDialog by lazy { CategoryBTDialog(viewModel) }

    override fun initFragment() {
        viewModel.setObserves()


        binding.apply {
            rvHealing.apply {
                adapter = HealingListAdapter(viewModel)
            }
            layoutSort.setOnClickListener {
                categoryBTDialog.show(requireActivity().supportFragmentManager, "")
            }
        }
    }

    private fun HealingViewModel.setObserves(){

        action.observe(this@HealingFragment, EventObserver{
            when(it){
                HealingViewModel.HealingAction.HEALING -> settingCategory("힐링")
                HealingViewModel.HealingAction.HUMOR -> settingCategory("유머")
                HealingViewModel.HealingAction.HEALTHY -> settingCategory("건강")
                HealingViewModel.HealingAction.HOBBY -> settingCategory("취미")
                HealingViewModel.HealingAction.TECHNOLOGY -> settingCategory("재테크")
                HealingViewModel.HealingAction.ETC -> settingCategory("기타")
                HealingViewModel.HealingAction.X -> categoryBTDialog.dismiss()
                HealingViewModel.HealingAction.MAIN_X -> settingCategory("주제별 보기")
            }
        })

        selectHealing.observe(this@HealingFragment, EventObserver{
            startActivity(
                intentFor<HealingDetailActivity>(
                    "infoToken" to it.infoToken
                )
            )
        })

        category.observe(this@HealingFragment, Observer {
            getHealingListData()
        })

    }

    override fun onResume() {
        super.onResume()

        viewModel.getHealingListData()
    }

}

class HealingListAdapter(vm : HealingViewModel) : BaseRecyclerAdapter<HealingListData, ItemHealingListBinding>(
    R.layout.item_healing_list, vm
)

class CategoryBTDialog(vm : HealingViewModel) : BaseBottomSheetDialog<DialogBottomsheetCategoryBinding>(
    R.layout.dialog_bottomsheet_category,
    vm
)