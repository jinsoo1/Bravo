package com.bravo.android.bravo.ui.binding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import org.jetbrains.anko.browse

@BindingAdapter(value = ["onLongClick"])
fun View.bindLongClickAction(action: () -> Unit) {
    this.setOnLongClickListener {
        action.invoke()
        false
    }
}

@BindingAdapter(value = ["bindUrlToOpen"])
fun View.bindUrlToOpen(url: String? = "") {
    if(url.isNullOrBlank().not()) {
        this.setOnClickListener {
            try {
                this.context.browse(url ?: "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@BindingAdapter("layoutWidth")
fun setLayoutWidth(layout: ViewGroup, dimen: Float) {
    val layoutParams = layout.layoutParams
    layoutParams.width = dimen.toInt()
    layout.layoutParams = layoutParams
}

@BindingAdapter("layoutHeight")
fun setLayoutHeight(layout: ViewGroup, dimen: Float) {
    val layoutParams = layout.layoutParams
    layoutParams.height = dimen.toInt()
    layout.layoutParams = layoutParams
}