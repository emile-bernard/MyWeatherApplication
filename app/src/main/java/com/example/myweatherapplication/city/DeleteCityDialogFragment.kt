package com.example.myweatherapplication.city

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.example.myweatherapplication.R

class DeleteCityDialogFragment : DialogFragment() {

    interface DeleteCityDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    companion object {
        val EXTRA_CITY_NAME = "training.kotlin.weather.extras.EXTRA_CITY_NAME"

        fun newInstance(cityName: String) : DeleteCityDialogFragment {
            val fragment = DeleteCityDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_CITY_NAME, cityName)
            }

            return fragment
        }
    }

    var listener: DeleteCityDialogListener? = null

    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = arguments!!.getString(EXTRA_CITY_NAME)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        builder.setTitle(getString(R.string.deletecity_title, cityName))
                .setPositiveButton(R.string.deletecity_positive,
                        { _, _ -> listener?.onDialogPositiveClick() })
                .setNegativeButton(R.string.deletecity_negative,
                        { _, _ -> listener?.onDialogNegativeClick() })

        return builder.create()
    }
}