package com.example.stop_watch_notify.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.databinding.FragmentListBinding
import com.example.stop_watch_notify.model.OptionModel
import com.example.stop_watch_notify.adapter.OptionAdapter
import com.example.stop_watch_notify.ui.view.viewholder.OptionVH
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class ListFragment<T: OptionModel, U: OptionVH<T>> : Fragment() {

    private var binding: FragmentListBinding? = null
    private lateinit var recyclerView: RecyclerView
    protected lateinit var addButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        recyclerView = root.findViewById(R.id.recyclerView)
        addButton = root.findViewById(R.id.addButton)

        recyclerView.layoutManager = LinearLayoutManager(context)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected open fun setAdapter(adapter: OptionAdapter<T, U>) {
        recyclerView.adapter = adapter
    }

}