package com.example.mvvm.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.DialogFragment
import com.example.mvvm.R
import com.example.mvvm.view.AboutActivity
import java.lang.IllegalStateException

class EnableGPSDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.dialog_title_geo)
                .setMessage(R.string.dialog_text_geo)
                .setNegativeButton(R.string.dialog_negative_button, null)
                .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                    dialog.cancel()
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .create()
        } ?: throw IllegalStateException("Activity не может быть null")
    }
}