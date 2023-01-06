package com.bravo.android.bravo.ui.view.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.data.local.pref.PreferencesController
import com.bravo.android.bravo.databinding.ActivityMainBinding
import com.bravo.android.bravo.util.KeepStateNavigator

class MainActivity : BaseVmActivity<ActivityMainBinding>(
    R.layout.activity_main,
    MainViewModel::class.java
) {
    override val viewModel by lazy { vm as MainViewModel }
    override val toolbarId = 0

    lateinit var navHostFragment : NavHostFragment
    lateinit var navController : NavController

    override fun initActivity() {
        navHostFragment = supportFragmentManager.findFragmentById(binding.navHost.id) as NavHostFragment
        navController = navHostFragment.navController

        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, binding.navHost.id)
        navController.navigatorProvider.addNavigator(navigator)

        navController.setGraph(R.navigation.nav_graph)

        binding.navBottom.setupWithNavController(navController)
    }

}