package com.example.stop_watch_notify.ui.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.stop_watch_notify.R

class NotificationDialogFragment : DialogFragment() {

    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var dialogTextView: TextView
    private var dialogText: String = ""
    private var leftOptionText: String = ""
    private var rightOptionText: String = ""
    private var leftOptionListener: (() -> Unit)? = null
    private var rightOptionListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_dialog_2_options, container, false)
        leftButton = view.findViewById(R.id.leftButton)
        rightButton = view.findViewById(R.id.rightButton)
        dialogTextView = view.findViewById(R.id.dialogText)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogTextView.text = dialogText
        leftButton.text = leftOptionText
        rightButton.text = rightOptionText

        leftButton.setOnClickListener {
            leftOptionListener?.invoke()
            dismiss()
        }

        rightButton.setOnClickListener {
            rightOptionListener?.invoke()
            dismiss()
        }
    }

    fun setOptions(dialogText: String, leftOptionText: String, rightOptionText: String, leftOptionListener: (() -> Unit)?, rightOptionListener: (() -> Unit)?) {
        this.dialogText = dialogText
        this.leftOptionText = leftOptionText
        this.rightOptionText = rightOptionText
        this.leftOptionListener = leftOptionListener
        this.rightOptionListener = rightOptionListener
    }
}