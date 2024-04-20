package com.example.stop_watch_notify.ui.view.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.model.RingtoneModel
import com.example.stop_watch_notify.utils.DataType

class ChooseSoundDialog : DialogFragment() {

    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var linearLayout: LinearLayout
    private var leftOptionListener: ((ringtone: RingtoneModel) -> Unit)? = null
    private var rightOptionListener: (() -> Unit)? = null
    private var chosenRingtone: RingtoneModel? = null
    private var previousTextView: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_choose_sound, container, false)
        leftButton = view.findViewById(R.id.leftButton)
        rightButton = view.findViewById(R.id.rightButton)
        linearLayout = view.findViewById(R.id.linearLayout)

        DataType.RINGTONES.dataList.forEach { ringtone ->
            val textView = TextView(requireContext())
            textView.text = (ringtone as RingtoneModel).name
            textView.setPadding(16)
            textView.setOnClickListener {
                chosenRingtone = ringtone
                previousTextView?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
                previousTextView = textView
            }
            linearLayout.addView(textView)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leftButton.setOnClickListener {
            if (chosenRingtone != null) {
                leftOptionListener?.invoke(chosenRingtone!!)
                dismiss()
            }
        }

        rightButton.setOnClickListener {
            rightOptionListener?.invoke()
            dismiss()
        }
    }

    fun setOptions(leftOptionListener: ((ringtone: RingtoneModel) -> Unit)?, rightOptionListener: (() -> Unit)?) {
        this.leftOptionListener = leftOptionListener
        this.rightOptionListener = rightOptionListener
    }
}