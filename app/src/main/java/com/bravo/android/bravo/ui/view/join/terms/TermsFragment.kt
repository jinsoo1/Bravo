package com.bravo.android.bravo.ui.view.join.terms

import androidx.lifecycle.Observer
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.data.local.UserLoginLocalDataSource
import com.bravo.android.bravo.data.remote.model.response.UserResponse
import com.bravo.android.bravo.databinding.FragmentTermsBinding
import com.bravo.android.bravo.ui.view.join.JoinViewModel
import com.bravo.android.bravo.ui.view.main.MainActivity
import com.bravo.android.bravo.util.EventObserver
import org.jetbrains.anko.support.v4.intentFor
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.ext.android.inject

class TermsFragment : BaseVmFragment<FragmentTermsBinding>(
    R.layout.fragment_terms,
    TermsViewModel::class.java
) {

    override val viewModel by lazy { vm as TermsViewModel }
    private val activityViewModel by sharedViewModel<JoinViewModel>()

    private val userLoginLocalDataSource: UserLoginLocalDataSource by inject()
    override fun initFragment() {
        viewModel.setObserves()

    }

    private fun TermsViewModel.setObserves(){

        action.observe(this@TermsFragment, EventObserver{
            when(it){
                TermsViewModel.TermsAction.NEXT ->{
                    loginData(activityViewModel.userToken.value!!,
                        activityViewModel.email.value!!,
                        activityViewModel.type.value!!,
                        activityViewModel.name.value!!,
                        activityViewModel.region.value!!,
                        activityViewModel.gender.value!!,
                        activityViewModel.year.value!!
                    )
                }

                TermsViewModel.TermsAction.SING_IN ->{
                    startActivity(
                        intentFor<MainActivity>()
                    )
                    requireActivity().finish()
                }
            }
        })

        userData.observe(this@TermsFragment, Observer {
            onLoginSuccess(it)
        })

    }

    //기기에 유저정보 저장
    private fun onLoginSuccess(response: UserResponse) {
        userLoginLocalDataSource.saveLoginInfo(response.user)
        userLoginLocalDataSource.saveAccessToken(response.accessToken)
        userLoginLocalDataSource.saveRefreshToken(response.refreshToken)

        viewModel.onSignIn()
    }

}