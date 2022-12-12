package com.bravo.android.bravo.ui.view.join.nickname

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseVmFragment
import com.bravo.android.bravo.databinding.FragmentNicknameBinding
import com.bravo.android.bravo.ui.view.join.JoinViewModel
import com.bravo.android.bravo.ui.view.join.dialog.JoinBottomDialog
import com.bravo.android.bravo.util.EventObserver
import com.bravo.android.bravo.util.listener.BottomSheetListener
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern


class NicknameFragment : BaseVmFragment<FragmentNicknameBinding>(
    R.layout.fragment_nickname,
    NicknameViewModel::class.java
) {

    override val viewModel by lazy { vm as NicknameViewModel }

    private val activityViewModel by sharedViewModel<JoinViewModel>()
    override fun initFragment() {

        viewModel.setObserves()


        // This callback will only be called when MyFragment is at least Started.
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event

                    val dialog = JoinBottomDialog(requireContext(), object : BottomSheetListener{
                        override fun beginListener() {
                            toast("비긴")?.show()
                        }

                        override fun cancelListener() {
                            toast("취소")?.show()
                        }

                    })
                    dialog.show(requireActivity().supportFragmentManager, "")

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)



    }

    private fun NicknameViewModel.setObserves(){
        action.observe(viewLifecycleOwner, EventObserver {
            when(it){
                NicknameViewModel.NicknameActions.NEXT ->{
                    if(nickname.value == null){
                        toast("닉네임을 설정해주세요.")?.show()
                        return@EventObserver
                    }else if(!Pattern.matches("[a-zA-Z0-9-가-힣 ]+", nickname.value!!)){  //닉네임이 이상할때
                        toast("닉네임을 정확하게 입력하세요.")?.show()
                        return@EventObserver
                    }else{
                        activityViewModel.settingName(nickname.value!!)
                        findNavController().navigate(R.id.action_nicknameFragment_to_addressFragment)
                    }

                }
            }
        })
    }

}