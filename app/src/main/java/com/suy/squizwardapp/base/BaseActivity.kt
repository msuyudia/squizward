package com.suy.squizwardapp.base

import androidx.appcompat.app.AppCompatActivity
import com.suy.squizwardapp.dialog.LoadingDialog

abstract class BaseActivity : AppCompatActivity() {
    private val loading by lazy { LoadingDialog(this) }

    fun showLoading() {
        if (!loading.isShowing) loading.show()
    }

    fun hideLoading() {
        if (loading.isShowing) loading.dismiss()
    }
}