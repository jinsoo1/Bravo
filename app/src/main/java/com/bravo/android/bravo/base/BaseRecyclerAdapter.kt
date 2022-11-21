package com.bravo.android.bravo.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.BR

abstract class BaseRecyclerAdapter<ITEM, BINDING : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val vm: BaseViewModel? = null
) : RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder>() {

    val items = mutableListOf<ITEM>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate<BINDING>(
                LayoutInflater.from(parent.context), layoutResId, parent, false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], vm)
    }

    open fun updateItems(updateItems: List<ITEM>) {
        items.clear()
        items.addAll(updateItems)
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Any?, vm: BaseViewModel?) {
            binding.run {
                setVariable(BR.item, item)
                if (vm != null) setVariable(BR.vm, vm)

                executePendingBindings()
            }
        }
    }
}

abstract class BaseBindRecyclerAdapter<ITEM, BINDING : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val vm: BaseViewModel? = null
) : BaseRecyclerAdapter<ITEM, BINDING>(
    layoutResId, vm
) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        onBind(holder, items[position], position)
    }

    abstract fun onBind(holder: ViewHolder?, t: ITEM?, position: Int)
}