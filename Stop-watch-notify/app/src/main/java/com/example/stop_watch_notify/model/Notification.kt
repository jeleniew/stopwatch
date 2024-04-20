package com.example.stop_watch_notify.model

import android.content.Context
import com.example.stop_watch_notify.utils.DataProvider
import com.example.stop_watch_notify.utils.DataType

class Notification(
    override var id: Int,
    var time: Time,
    var isOn: Boolean,
    var soundId: Int
): OptionModel {

    fun setNotificationTime(time: Time, context: Context, notificationType: DataType) {
        this.time = time
        val notification: Notification = notificationType.dataList.find { it.id == id } as Notification
        notification?.time = time
        DataProvider.saveNotifications(notificationType, context)
    }

    fun changeActivationStatus(isOn: Boolean, context: Context, notificationType: DataType) {
        this.isOn = isOn
        val notification: Notification = notificationType.dataList.find { it.id == id } as Notification
        notification?.isOn = isOn
        DataProvider.saveNotifications(notificationType, context)
    }

    fun setSound(soundId: Int, context: Context, notificationType: DataType) {
        this.soundId = soundId
        val notification: Notification = notificationType.dataList.find { it.id == id } as Notification
        notification?.soundId = soundId
        DataProvider.saveNotifications(notificationType, context)
    }
    // TODO: move those settings to a different place

}