package com.example.mvvm.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mvvm.R
import com.example.mvvm.view.AboutActivity
import java.lang.IllegalStateException

class AboutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_text)
                .setNegativeButton(R.string.dialog_negative_button, null)
                .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                    dialog.cancel()
                    startActivity(
                        Intent(
                            it,
                            AboutActivity::class.java
                        )
                    )
                }
                .create()
        } ?: throw IllegalStateException("Activity не может быть null")
    }
}