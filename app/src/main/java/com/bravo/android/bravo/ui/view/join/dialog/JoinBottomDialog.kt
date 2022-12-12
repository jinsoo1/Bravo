package com.bravo.android.bravo.ui.view.join.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.bravo.android.bravo.R
import com.bravo.android.bravo.databinding.DialogBottomsheetJoinBinding
import com.bravo.android.bravo.util.listener.BottomSheetListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class JoinBottomDialog(val mContext : Context, val listener : BottomSheetListener) : BottomSheetDialogFragment() {

    lateinit var binding : DialogBottomsheetJoinBinding

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_bottomsheet_join, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.ivX.setOnClickListener {
            listener.beginListener()
            dismiss()
        }

        binding.btnBegin.setOnClickListener {
            listener.beginListener()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            listener.cancelListener()
            dismiss()
        }




    }




}