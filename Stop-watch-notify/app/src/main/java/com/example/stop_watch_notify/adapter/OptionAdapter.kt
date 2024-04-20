package com.example.stop_watch_notify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stop_watch_notify.model.OptionModel
import com.example.stop_watch_notify.ui.view.viewholder.OptionVH
import com.example.stop_watch_notify.utils.DataProvider
import com.example.stop_watch_notify.utils.DataType

class OptionAdapter<T: OptionModel, U: OptionVH<T>>(
    private var optionList: MutableList<T>,
    private val viewHolderConstructor: (View) -> U,
    private val layoutResId: Int,
    private val dataType: DataType,
    private var context: Context
) : RecyclerView.Adapter<U>() {

    private var moreButtonOnClickListener: ((buttonView: View, itemView: View, item: T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): U {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return viewHolderConstructor(view)
    }

    override fun onBindViewHolder(holder: U, position: Int) {
        holder.create(optionList[position], context, dataType)
        holder.setMoreButton(moreButtonOnClickListener)
    }

    override fun getItemCount(): Int {
        return optionList.size
    }

    fun updateList(newOptionList: MutableList<T>) {
        optionList = newOptionList
        notifyDataSetChanged()
    }

    fun setMoreButtonListener(moreOptionListener: ((buttonView: View, itemView: View, item: T) -> Unit)?) {
        this.moreButtonOnClickListener = moreOptionListener
    }

}