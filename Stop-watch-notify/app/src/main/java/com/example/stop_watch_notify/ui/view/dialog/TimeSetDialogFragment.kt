package com.example.stop_watch_notify.ui.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.stop_watch_notify.R

class TimeSetDialogFragment : DialogFragment() {

    private lateinit var hoursEditText: EditText
    private lateinit var minutesEditText: EditText
    private lateinit var secondsEditText: EditText
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private var hours: String = ""
    private var minutes: String = ""
    private var seconds: String = ""
    private var leftOptionListener: (() -> Unit)? = null
    private var rightOptionListener: ((String, String, String) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_time_set, container, false)
        hoursEditText = view.findViewById(R.id.hours)
        minutesEditText = view.findViewById(R.id.minutes)
        secondsEditText = view.findViewById(R.id.seconds)
        leftButton = view.findViewById(R.id.leftButton)
        rightButton = view.findViewById(R.id.rightButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hoursEditText.setText(hours)
        minutesEditText.setText(minutes)
        secondsEditText.setText(seconds)
        leftButton.setOnClickListener {
            leftOptionListener?.invoke()
            dismiss()
        }

        rightButton.setOnClickListener {
            rightOptionListener?.invoke(
                hoursEditText.text.toString(),
                minutesEditText.text.toString(),
                secondsEditText.text.toString())
            dismiss()
        }
    }

    fun setOptions(
        hours: String,
        minutes: String,
        seconds: String,
        leftOptionListener: (() -> Unit)?,
        rightOptionListener: ((String, String, String) -> Unit)?
    ) {
        this.hours = hours
        this.minutes = minutes
        this.seconds = seconds
        this.leftOptionListener = leftOptionListener
        this.rightOptionListener = rightOptionListener
    }
}