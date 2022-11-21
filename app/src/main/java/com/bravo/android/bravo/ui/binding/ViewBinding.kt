package com.bravo.android.bravo.ui.binding

import android.view.View
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