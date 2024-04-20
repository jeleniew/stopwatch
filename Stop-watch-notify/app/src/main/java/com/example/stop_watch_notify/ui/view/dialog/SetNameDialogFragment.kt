package com.example.stop_watch_notify.ui.view.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.stop_watch_notify.R

class SetNameDialogFragment : DialogFragment() {

    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var nameTextView: EditText
    private var leftOptionListener: (() -> Unit)? = null
    private var rightOptionListener: ((name: String) -> Unit)? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_dialog_set_name, container, false)
        leftButton = view.findViewById(R.id.leftButton)
        rightButton = view.findViewById(R.id.rightButton)
        nameTextView = view.findViewById(R.id.name)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leftButton.setOnClickListener {
            leftOptionListener?.invoke()
            dismiss()
        }

        rightButton.setOnClickListener {
            rightOptionListener?.invoke(nameTextView.text.toString())
            dismiss()
        }
    }

    fun setOptions(leftOptionListener: (() -> Unit)?, rightOptionListener: ((name: String) -> Unit)?) {
        this.leftOptionListener = leftOptionListener
        this.rightOptionListener = rightOptionListener
    }
}