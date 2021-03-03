package com.suy.squizwardapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.suy.squizwardapp.databinding.BtsAlertBinding
import com.suy.squizwardapp.listener.AlertListener

class BottomSheetAlertDialog(private val listener: AlertListener) : BottomSheetDialogFragment() {
    private var _binding: BtsAlertBinding? = null
    private val binding get() = _binding
    private var title: Int? = null

    fun setTitle(title: Int) {
        this.title = title
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BtsAlertBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (title != null) {
            true -> binding?.tvTitleBtsAlert?.text = getString(title ?: 0)
        }
        binding?.btnNoBtsAlert?.setOnClickListener { dismiss() }
        binding?.btnYesBtsAlert?.setOnClickListener { listener.onAlertClicked(true) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}