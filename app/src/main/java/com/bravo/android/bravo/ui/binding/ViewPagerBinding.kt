package com.bravo.android.bravo.ui.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bravo.android.bravo.data.common.model.HomeHealingData
import com.bravo.android.bravo.ui.view.main.home.adapter.HomeHealingPagerAdapter

@BindingAdapter(value = ["bindHomeHealingToPager"], requireAll = true)
fun ViewPager2.bindHomeHealingToPager(item: List<HomeHealingData>?) {

    if(this.adapter == null && item != null) {
        this.adapter = HomeHealingPagerAdapter(item)
    }

    this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    if(item != null){
        (adapter as HomeHealingPagerAdapter?)?.updateItems(item)
    }

}