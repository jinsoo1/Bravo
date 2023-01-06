package com.bravo.android.bravo.ui.view.main.home.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.data.common.model.HomeHealingData
import com.bravo.android.bravo.databinding.ItemHomeHealingBinding
import com.bravo.android.bravo.ui.view.healing.HealingDetailActivity
import org.jetbrains.anko.intentFor

class HomeHealingPagerAdapter(val item: List<HomeHealingData>) : BaseRecyclerAdapter<HomeHealingData, ItemHomeHealingBinding>(
    R.layout.item_home_healing
){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.root.setOnClickListener {
            it.context.apply {
                startActivity(
                    intentFor<HealingDetailActivity>(
                        "infoToken" to item[position].healingToken
                    )
                )
            }
        }
    }

}