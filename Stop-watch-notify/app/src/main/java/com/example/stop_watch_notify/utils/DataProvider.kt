package com.example.stop_watch_notify.utils

import android.content.Context
import android.util.Log
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.model.Notification
import com.example.stop_watch_notify.model.OptionModel
import com.example.stop_watch_notify.model.RingtoneModel
import com.example.stop_watch_notify.model.Time
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

enum class DataType(val fileName: String) {
    NOTIFICATIONS("notifications.json") {
        override var dataList: MutableList<OptionModel> = mutableListOf()
    },
    REPEATINGS("repeatings.json") {
        override var dataList: MutableList<OptionModel> = mutableListOf()
    },
    RINGTONES("ringtones.json") {
        override var dataList: MutableList<OptionModel> = mutableListOf()
    };

    abstract var dataList: MutableList<OptionModel>
}

object DataProvider {

    fun init(context: Context) {
        loadNotifications(DataType.NOTIFICATIONS, context)
        loadNotifications(DataType.REPEATINGS, context)
        loadRingtones(context)
    }

    private fun loadNotifications(notificationType: DataType, context: Context) {
        val file = File(context.filesDir, notificationType.fileName)
        if (file.exists()) {
            val jsonString = file.readText()
            val type = object : TypeToken<MutableList<Notification>>() {}.type
            notificationType.dataList = Gson().fromJson(jsonString, type)
        } else {
            saveNotifications(notificationType, context)
        }
    }

    private fun loadRingtones(context: Context) {
        val file = File(context.filesDir, DataType.RINGTONES.fileName)
        if (file.exists()) {
            val jsonString = file.readText()
            val type = object : TypeToken<MutableList<RingtoneModel>>() {}.type
            DataType.RINGTONES.dataList = Gson().fromJson(jsonString, type)
        } else {
            DataType.RINGTONES.dataList = getRingtoneList()
            Log.d("debug", "ringotnes size: ${DataType.RINGTONES.dataList} and ${getRingtoneList().size}")
            saveNotifications(DataType.RINGTONES, context)
        }
    }

    fun saveNotifications(notificationType: DataType, context: Context) {
        sortNotifications(notificationType)
        val jsonString = Gson().toJson(notificationType.dataList)
        Log.d("debug", "json String: $jsonString")
        val file = File(context.filesDir, notificationType.fileName)
        file.writeText(jsonString)
    }

    fun addNotification(notificationType: DataType, notification: Notification, context: Context) {
        notificationType.dataList.add(notification)
        saveNotifications(notificationType, context)
    }

    fun addRingtone(ringtone: RingtoneModel, context: Context) {
        DataType.RINGTONES.dataList.add(ringtone)
        saveNotifications(DataType.RINGTONES, context)
    }

    fun deleteNotification(notificationType: DataType, notificationId: Int, context: Context) {
        notificationType.dataList.removeAll { it.id == notificationId }
        saveNotifications(notificationType, context)
    }

    fun getRingtoneList() : MutableList<OptionModel> {
        return mutableListOf(
            RingtoneModel(1, "Coin", R.raw.coin_sound),
            RingtoneModel(2, "Level up", R.raw.level_up),
            RingtoneModel(3, "Tap", R.raw.tap_notification),
            RingtoneModel(4, "1-2-3", R.raw.plump_123))
    }

    private fun sortNotifications(dataType: DataType) {
        when (dataType) {
            DataType.NOTIFICATIONS -> dataType.dataList.sortBy { (it as Notification).time.value() }
            DataType.REPEATINGS -> dataType.dataList.sortBy { (it as Notification).time.value() }
            DataType.RINGTONES -> dataType.dataList.sortBy { (it as RingtoneModel).name }
        }

    }

    fun findSmallestUnusedIndex(notificationType: DataType): Int {
        val indexSet = notificationType.dataList.map { it.id }.toSet()
        var smallestUnusedIndex = 0

        while (indexSet.contains(smallestUnusedIndex)) {
            smallestUnusedIndex++
        }

        return smallestUnusedIndex
    }

}