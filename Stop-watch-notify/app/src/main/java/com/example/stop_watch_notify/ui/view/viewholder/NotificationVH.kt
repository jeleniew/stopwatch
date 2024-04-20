package com.example.stop_watch_notify.ui.view.viewholder

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.model.Notification
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import com.example.stop_watch_notify.model.RingtoneModel
import com.example.stop_watch_notify.model.Time
import com.example.stop_watch_notify.ui.view.dialog.NotificationDialogFragment
import com.example.stop_watch_notify.ui.view.dialog.TimeSetDialogFragment
import com.example.stop_watch_notify.utils.DataProvider
import com.example.stop_watch_notify.utils.DataType

class NotificationVH(itemView: View) : OptionVH<Notification>(itemView) {

    private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    private val text: TextView = itemView.findViewById(R.id.time)
    private val moreButton: ImageButton = itemView.findViewById(R.id.moreButton)
    private lateinit var notification: Notification

    override fun create(notification: Notification, context: Context, dataType: DataType) {
        this.notification = notification
        checkBox.isChecked = notification.isOn
        text.text = notification.time.toString()

        checkBox.setOnClickListener {
            notification.changeActivationStatus(checkBox.isChecked, context, dataType)
        }
    }

    override fun setMoreButton(moreOptionListener: ((buttonView: View, itemView: View, item: Notification) -> Unit)?) {
        moreButton.setOnClickListener {
            moreOptionListener?.invoke(it, itemView, notification)
        }
    }

    companion object {
        fun viewHolderConstructor(itemView: View): NotificationVH {
            return NotificationVH(itemView)
        }
    }

}