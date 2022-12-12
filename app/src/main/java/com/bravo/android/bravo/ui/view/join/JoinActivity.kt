package com.bravo.android.bravo.ui.view.join

import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.databinding.ActivityJoinBinding
import com.bravo.android.bravo.util.KeepStateNavigator

class JoinActivity: BaseVmActivity<ActivityJoinBinding>(
    R.layout.activity_join,
    JoinViewModel::class.java
) {
    override val viewModel by lazy { vm as JoinViewModel }
    override val toolbarId = 0

    private val id by lazy {
        intent.getStringExtra("id")
    }
    private val email by lazy {
        intent.getStringExtra("email")
    }
    private val type by lazy {
        intent.getStringExtra("type")
    }
    override fun initActivity() {

        viewModel.settingUser(id!!, email!!, type!!)

    }

}