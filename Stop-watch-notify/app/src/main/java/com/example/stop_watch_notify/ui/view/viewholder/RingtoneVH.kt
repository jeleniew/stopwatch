package com.example.stop_watch_notify.ui.view.viewholder

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.model.Notification
import com.example.stop_watch_notify.model.RingtoneModel
import com.example.stop_watch_notify.utils.DataType

class RingtoneVH(itemView: View) : OptionVH<RingtoneModel>(itemView) {

    private val text: TextView = itemView.findViewById(R.id.ringtoneName)
    private val moreButton: ImageButton = itemView.findViewById(R.id.moreButton)
    private lateinit var ringtone: RingtoneModel

    override fun create(ringtone: RingtoneModel, context: Context, dataType: DataType) {
        this.ringtone = ringtone
        text.text = ringtone.name
    }

    override fun setMoreButton(moreOptionListener: ((buttonView: View, itemView: View, item: RingtoneModel) -> Unit)?) {
        moreButton.setOnClickListener {
            moreOptionListener?.invoke(it, itemView, ringtone)
        }
    }

    companion object {
        fun viewHolderConstructor(itemView: View): RingtoneVH {
            return RingtoneVH(itemView)
        }
    }

}