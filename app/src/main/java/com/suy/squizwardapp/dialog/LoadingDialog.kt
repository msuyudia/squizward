package com.suy.squizwardapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.suy.squizwardapp.R
import com.suy.squizwardapp.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        window?.setBackgroundDrawableResource(R.color.colorTransparant)
    }
}