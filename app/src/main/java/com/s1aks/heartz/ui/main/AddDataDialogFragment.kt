package com.s1aks.heartz.ui.main

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.s1aks.heartz.R
import com.s1aks.heartz.databinding.DialogAddDataBinding

class AddDataDialogFragment(
    private val saveClickListener: OnSaveClickListener
) : DialogFragment() {
    private var _binding: DialogAddDataBinding? = null
    private val binding
        get() = _binding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddDataBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(getString(R.string.dialog_title))
            .setView(binding?.root)
            .setPositiveButton(
                getString(R.string.dialog_save_button_text)
            ) { _, _ ->
                binding?.let {
                    saveClickListener.onSaveClicked(
                        it.topPressure.text.toString(),
                        it.lowPressure.text.toString(),
                        it.pulse.text.toString()
                    )
                }
            }
            .setNegativeButton(getString(R.string.dialog_cancel_button_text), null)
            .create()
            .apply {

//                binding?.topPressure?.addTextChangedListener {
//                    checkPositiveButtonEnabled(it)
//                }
            }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

interface OnSaveClickListener {
    fun onSaveClicked(topPressure: String, lowPressure: String, pulse: String)
}