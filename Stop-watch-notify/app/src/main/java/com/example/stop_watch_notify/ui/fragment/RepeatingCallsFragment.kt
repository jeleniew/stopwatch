package com.example.stop_watch_notify.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.adapter.OptionAdapter
import com.example.stop_watch_notify.model.Notification
import com.example.stop_watch_notify.model.Time
import com.example.stop_watch_notify.ui.view.dialog.ChooseSoundDialog
import com.example.stop_watch_notify.ui.view.dialog.NotificationDialogFragment
import com.example.stop_watch_notify.ui.view.dialog.TimeSetDialogFragment
import com.example.stop_watch_notify.ui.view.viewholder.NotificationVH
import com.example.stop_watch_notify.utils.DataProvider
import com.example.stop_watch_notify.utils.DataType

class RepeatingCallsFragment : ListFragment<Notification, NotificationVH>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        val repeatingCallList = DataType.REPEATINGS.dataList as MutableList<Notification>

        val adapter: OptionAdapter<Notification, NotificationVH> = OptionAdapter(
            repeatingCallList,
            NotificationVH::viewHolderConstructor,
            R.layout.view_notification_option,
            DataType.REPEATINGS,
            requireContext()
        )
        adapter.setMoreButtonListener { button, view, notification ->
            val popupMenu = PopupMenu(view.context, button)
            popupMenu.menuInflater.inflate(R.menu.notification_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_delete -> {
                        val dialogFragment = NotificationDialogFragment()
                        dialogFragment.setOptions(
                            "Are you sure?",
                            "Yes",
                            "No",
                            {
                                DataProvider.deleteNotification(DataType.REPEATINGS, notification.id, requireContext())
                                adapter.updateList(DataType.REPEATINGS.dataList as MutableList<Notification>)
                            },
                            null
                        )
                        dialogFragment.show(childFragmentManager, "NotificationDialogFragment")
                        true
                    }

                    R.id.action_edit -> {
                        val dialogFragment = TimeSetDialogFragment()
                        dialogFragment.setOptions(
                            String.format("%02d", notification.time.hours),
                            String.format("%02d", notification.time.minutes),
                            String.format("%02d", notification.time.seconds),
                            null
                        ) { hours, minutes, seconds ->
                            val hoursInt = if (hours.isNotBlank()) hours.toInt() else 0
                            val minutesInt = if (minutes.isNotBlank()) minutes.toInt() else 0
                            val secondsInt = if (seconds.isNotBlank()) seconds.toInt() else 0

                            val time = Time(hoursInt, minutesInt, secondsInt)
                            notification.setNotificationTime(
                                time,
                                requireContext(),
                                DataType.REPEATINGS
                            )
                            adapter.updateList(DataType.REPEATINGS.dataList as MutableList<Notification>)
                        }
                        dialogFragment.show(childFragmentManager, "TimeSetDialogFragment")
                        true
                    }

                    R.id.action_sound -> {
                        val dialogFragment = ChooseSoundDialog()
                        dialogFragment.setOptions(
                            leftOptionListener = { ringtone ->
                                notification.setSound(
                                    ringtone.id,
                                    requireContext(),
                                    DataType.REPEATINGS
                                )
                            },
                            rightOptionListener = null
                        )
                        dialogFragment.show(childFragmentManager, "TimeSetDialogFragment")
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }
        setAdapter(adapter)

        addButton.setOnClickListener {
            val dialogFragment = TimeSetDialogFragment()
            dialogFragment.setOptions(
                "00",
                "00",
                "00",
                null
            ) { hours, minutes, seconds ->
                var time = Time(hours.toInt(), minutes.toInt(), seconds.toInt())
                var notification = Notification(
                    DataProvider.findSmallestUnusedIndex(DataType.REPEATINGS),
                    time,
                    isOn= true,
                    -1
                )
                DataProvider.addNotification(DataType.REPEATINGS, notification, requireContext())
                adapter.updateList(DataType.REPEATINGS.dataList as MutableList<Notification>)
            }
            dialogFragment.show(getChildFragmentManager(), "TimeSetDialogFragment")
        }

        return root
    }
}