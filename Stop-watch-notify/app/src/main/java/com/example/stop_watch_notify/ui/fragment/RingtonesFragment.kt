package com.example.stop_watch_notify.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.appcompat.widget.PopupMenu
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.model.RingtoneModel
import com.example.stop_watch_notify.utils.DataProvider
import com.example.stop_watch_notify.adapter.OptionAdapter
import com.example.stop_watch_notify.model.Notification
import com.example.stop_watch_notify.model.Time
import com.example.stop_watch_notify.ui.view.dialog.NotificationDialogFragment
import com.example.stop_watch_notify.ui.view.dialog.SetNameDialogFragment
import com.example.stop_watch_notify.ui.view.dialog.TimeSetDialogFragment
import com.example.stop_watch_notify.ui.view.viewholder.RingtoneVH
import com.example.stop_watch_notify.utils.DataType
import com.example.stop_watch_notify.utils.SoundManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RingtonesFragment : ListFragment<RingtoneModel, RingtoneVH>() {

    private lateinit var adapter: OptionAdapter<RingtoneModel, RingtoneVH>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        val ringtoneList = DataType.RINGTONES.dataList as MutableList<RingtoneModel>
        adapter = OptionAdapter(
            ringtoneList,
            RingtoneVH::viewHolderConstructor,
            R.layout.view_ringtone_option,
            DataType.RINGTONES,
            requireContext()
        )

        adapter.setMoreButtonListener { button, view, ringtone ->
            val popupMenu = PopupMenu(view.context, button)
            popupMenu.menuInflater.inflate(R.menu.ringtones_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.play -> {
                        Log.d("debug", "ringtone.location")
                        if (ringtone.resId != null) {
                            SoundManager.playSound(requireContext(), ringtone.resId!!)
                        } else if (ringtone.location != null) {
                            SoundManager.playSound(requireContext(), ringtone.location!!)
                        } else {
                            Log.d("debug", "nothing was read")
                        }
                        true
                    }

                    R.id.action_change -> {
                        val dialogFragment = SetNameDialogFragment()
                        dialogFragment.setOptions(
                            null
                        ) { name ->
                            ringtone.changeName(name)
//                            DataProvider.addRingtone(ringtone, requireContext())
                            adapter.updateList(DataType.RINGTONES.dataList as MutableList<RingtoneModel>)
                        }
                        dialogFragment.show(childFragmentManager, "TimeSetDialogFragment")
                        true
                    }

                    R.id.action_delete -> {
                        val dialogFragment = NotificationDialogFragment()
                        dialogFragment.setOptions(
                            "Are you sure?",
                            "Yes",
                            "No",
                            {
                                DataProvider.deleteNotification(
                                    DataType.RINGTONES,
                                    ringtone.id,
                                    requireContext()
                                )
                                adapter.updateList(DataType.RINGTONES.dataList as MutableList<RingtoneModel>)
                            },
                            null
                        )
                        dialogFragment.show(childFragmentManager, "NotificationDialogFragment")
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }

        setAdapter(adapter)

        addButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, AUDIO_PICKER)
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUDIO_PICKER && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            uri?.let { selectedFileUri ->
                try {
                    val inputStream = requireContext().contentResolver.openInputStream(selectedFileUri)
                    val dialogFragment = SetNameDialogFragment()
                    dialogFragment.setOptions(
                        null
                    ) { name ->
                        val extension = requireContext().contentResolver.getType(selectedFileUri)?.let { type ->
                            MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
                        } ?: ""

                        val fileName = "$name.$extension"
                        val outputFile = File(requireContext().filesDir, fileName)

                        inputStream?.use { input ->
                            FileOutputStream(outputFile).use { outputStream ->
                                input.copyTo(outputStream)
                            }
                        }

                        val ringtone = RingtoneModel(
                            DataProvider.findSmallestUnusedIndex(DataType.RINGTONES),
                            name,
                            "${requireContext().filesDir}/$fileName"
                        )
                        DataProvider.addRingtone(ringtone, requireContext())
                        adapter.updateList(DataType.RINGTONES.dataList as MutableList<RingtoneModel>)
                    }
                    dialogFragment.show(childFragmentManager, "TimeSetDialogFragment")

                }catch (e: IOException) {
                    Log.e("Error", "Error copying file", e)
                }
            }
        }
        Log.d("debug", "onActivityResult")
    }

    companion object {
        const val AUDIO_PICKER = 1
    }

}