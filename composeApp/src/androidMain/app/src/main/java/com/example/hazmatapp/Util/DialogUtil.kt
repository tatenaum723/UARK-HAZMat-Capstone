package com.example.hazmatapp.Util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/** Class used to create pop up windows to confirm or cancel actions, it needs:
 * 1) Question that the pop up will ask
 * 2) The message of button 1
 * 3) The message of button 2
 */

interface DialogListener{
    fun onYes(flag: Boolean)
}

class DialogUtil(setMessage: String, setAnswer1: String, setAnswer2: String) : DialogFragment() {
    private var message = setMessage
    private var choice1 = setAnswer1
    private var choice2 = setAnswer2
    private var listener: DialogListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
                .setNegativeButton(choice2) { _, _ ->
                    dismiss() // Closes the delete pop up
                }
                .setPositiveButton(choice1) { _, _ ->
                    yesAction(true)
                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setListener(listener: DialogListener) {
        this.listener = listener
    }

    private fun yesAction(setFlag: Boolean){
        listener?.onYes(setFlag)
    }




}
