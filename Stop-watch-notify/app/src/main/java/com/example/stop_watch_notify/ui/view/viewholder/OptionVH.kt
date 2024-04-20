package com.example.stop_watch_notify.ui.view.viewholder

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stop_watch_notify.model.Notification
import com.example.stop_watch_notify.model.OptionModel
import com.example.stop_watch_notify.utils.DataType

open class OptionVH<T: OptionModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun create(name: T, context: Context, dataType: DataType) {
        Log.d("debug", "create: null")
    }

    open fun setMoreButton(moreOptionListener: ((buttonView: View, itemView: View, item: T) -> Unit)?) {

    }
}