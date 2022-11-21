package com.bravo.android.bravo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.bravo.android.bravo.BR
import org.koin.android.viewmodel.compat.ViewModelCompat.viewModel

abstract class BaseVmFragment<T : ViewDataBinding>(
    @LayoutRes val layoutRes: Int,
    cls : Class<out ViewModel>
) : Fragment(){

    val vm by viewModel(this, cls)
    abstract val viewModel : BaseViewModel
    lateinit var binding : T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            layoutRes,
            container,
            false
        )
        bindVariables()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFragment()
        super.onViewCreated(view, savedInstanceState)
    }


    open fun bindVariables() {
        binding.setVariable(BR.vm, vm)
        binding.lifecycleOwner = this
    }

    abstract fun initFragment()

}