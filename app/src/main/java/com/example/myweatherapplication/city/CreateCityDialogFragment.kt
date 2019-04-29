package com.example.myweatherapplication.city

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import com.example.myweatherapplication.R

class CreateCityDialogFragment : DialogFragment() {

    interface CreateCityDialogListener {
        fun onDialogPositiveClick(cityName: String)
        fun onDialogNegativeClick()
    }

    var listener: CreateCityDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val input = EditText(context)
        with(input) {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = getString(R.string.createcity_cityhint)
        }

        builder.setTitle(R.string.createcity_title)
                .setView(input)
                .setPositiveButton(R.string.createcity_positive,
                        DialogInterface.OnClickListener { _, _ ->
                    listener?.onDialogPositiveClick(input.text.toString())
                })
                .setNegativeButton(R.string.createcity_negative,
                        DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })

        return builder.create()
    }

}