package com.bravo.android.bravo.ui.view

import com.bravo.android.bravo.ui.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel() }

}