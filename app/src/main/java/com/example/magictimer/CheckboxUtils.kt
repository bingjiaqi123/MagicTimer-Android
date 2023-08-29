package com.example.magictimer

import android.view.View
import android.widget.CheckBox

fun setupCheckboxListeners(view: View) {
    val checkBoxCampusNetwork = view.findViewById<CheckBox>(R.id.checkBoxCampusNetwork)
    val checkBoxDisconnectable = view.findViewById<CheckBox>(R.id.checkBoxDisconnectable)
    val checkBoxCommute = view.findViewById<CheckBox>(R.id.checkBoxCommute)

    checkBoxCampusNetwork.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            checkBoxDisconnectable.isChecked = false
            checkBoxCommute.isChecked = false
        }
    }

    checkBoxDisconnectable.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            checkBoxCampusNetwork.isChecked = false
        }
    }

    checkBoxCommute.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            checkBoxCampusNetwork.isChecked = false
        }
    }
}
