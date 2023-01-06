package com.bravo.android.bravo.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsetsController
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.bravo.android.bravo.R
import com.bravo.android.bravo.BR
import org.koin.android.viewmodel.compat.ViewModelCompat.viewModel

abstract class BaseVmActivity<T : ViewDataBinding>(
    @LayoutRes val layoutRes: Int,
    cls: Class<out ViewModel>
) : AppCompatActivity() {

    val vm by viewModel(this, cls)

    abstract val viewModel : BaseViewModel
    lateinit var binding: T
    lateinit var toolbar : Toolbar
    internal var menu : Menu? = null
    protected abstract val toolbarId : Int
    val lifecycleOwner : LifecycleOwner by lazy{ this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        binding = DataBindingUtil.setContentView(this, layoutRes)
        bindVariables()
        initToolbarIfReady()
        initActivity()

    }

    private fun initToolbarIfReady() {

        if (toolbarId != 0) {
            toolbar = findViewById<View>(toolbarId) as Toolbar
            setSupportActionBar(toolbar)
            toolbar.setTitleTextAppearance(this@BaseVmActivity, R.style.TextBold_20)
            toolbar.setSubtitleTextAppearance(
                this@BaseVmActivity,
                R.style.TextRegular_12_GhostPrimary
            )
            toolbar.setTitleTextColor(Color.BLACK)
            toolbar.setBackgroundColor(Color.WHITE)
            toolbar.contentInsetStartWithNavigation = 0
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic__back__appbar)
        }
        window.statusBarColor = Color.WHITE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }else{
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    abstract fun initActivity()

    open fun bindVariables(){
        binding.setVariable(BR.vm, vm)
        binding.lifecycleOwner = this
    }

    var toolbarTitle : String
        get() = supportActionBar!!.title.toString()
        set(value){
            supportActionBar!!.title = value
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


}