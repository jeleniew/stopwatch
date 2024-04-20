package com.example.stop_watch_notify.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.databinding.FragmentStopwatchBinding
import com.example.stop_watch_notify.model.Notification
import com.example.stop_watch_notify.model.RingtoneModel
import com.example.stop_watch_notify.utils.DataProvider
import com.example.stop_watch_notify.utils.DataType
import com.example.stop_watch_notify.utils.SharedPreferencesHelper
import com.example.stop_watch_notify.utils.SoundManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StopwatchFragment : Fragment() {

    private var binding: FragmentStopwatchBinding? = null
    private lateinit var time: TextView
    private lateinit var resetButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private var isRunning = false
    private var countDownTimer: CountDownTimer? = null
    private var h = 0
    private var m = 0
    private var s = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        val root = binding!!.root
        time = root.findViewById(R.id.time)
        resetButton = root.findViewById(R.id.resetButton)
        pauseButton = root.findViewById(R.id.pauseButton)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pauseButton.setOnClickListener {
            if (isRunning) {
                pauseButton.background = resources.getDrawable(R.drawable.play)
                countDownTimer?.cancel()
                countDownTimer = null
                isRunning = false
            } else {
                isRunning = true
                pauseButton.background = resources.getDrawable(R.drawable.pause)
                if (countDownTimer == null) {
                    countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
                        override fun onTick(p0: Long) {
                            if (s < 59) {
                                s++
                            } else {
                                s = 0
                                if (m < 59) {
                                    m++
                                } else {
                                    m = 0
                                    h++
                                }
                            }
                            var currentTime = (h.toLong() * 60 + m) * 60 + s
                            time.text = String.format("%02d:%02d:%02d", h, m, s)
                            if (currentTime.toInt() != 0) {
                                for (notification in DataType.NOTIFICATIONS.dataList as MutableList<Notification>) {
                                    if (notification.isOn && currentTime == notification.time.value()) {
                                        var ringtone: RingtoneModel? = if (notification.soundId == -1) {
                                            DataType.RINGTONES.dataList.find { it.id == SharedPreferencesHelper.defaultRingtone } as RingtoneModel
                                        } else {
                                            DataType.RINGTONES.dataList.find { it.id == notification.soundId } as RingtoneModel
                                        }

                                        if (ringtone == null) {
                                            ringtone = DataType.RINGTONES.dataList.first() as RingtoneModel
                                            SharedPreferencesHelper.defaultRingtone = ringtone.id
                                        }
                                        if (ringtone.resId != null) {
                                            SoundManager.playSound(
                                                requireContext(),
                                                ringtone.resId!!
                                            )
                                        } else if (ringtone.location != null) {
                                            SoundManager.playSound(
                                                requireContext(),
                                                ringtone.location!!
                                            )
                                        }
                                    }
                                }

                                for (call in DataType.REPEATINGS.dataList as MutableList<Notification>) {
                                    if (call.isOn && (currentTime % call.time.value()).toInt() == 0) {
                                        var ringtone: RingtoneModel? = if (call.soundId == -1) {
                                            DataType.RINGTONES.dataList.find { it.id == SharedPreferencesHelper.defaultRingtone } as RingtoneModel
                                        } else {
                                            DataType.RINGTONES.dataList.find { it.id == call.soundId } as RingtoneModel
                                        }

                                        if (ringtone == null) {
                                            ringtone = DataType.RINGTONES.dataList.first() as RingtoneModel
                                            SharedPreferencesHelper.defaultRingtone = ringtone.id
                                        }
                                        if (ringtone == null) {
                                            ringtone = DataType.RINGTONES.dataList.first() as RingtoneModel
                                            SharedPreferencesHelper.defaultRingtone = ringtone.id
                                        }
                                        if (ringtone.resId != null) {
                                            SoundManager.playSound(
                                                requireContext(),
                                                ringtone.resId!!
                                            )
                                        } else if (ringtone.location != null) {
                                            SoundManager.playSound(
                                                requireContext(),
                                                ringtone.location!!
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        override fun onFinish() {
//                            TODO("Not yet implemented")
                        }
                    }.start()
                }
            }
        }

        resetButton.setOnClickListener {
            countDownTimer?.cancel()
            countDownTimer = null
            isRunning = false
            h = 0
            m = 0
            s = 0
            time.text = String.format("%02d:%02d:%02d", h, m, s)
            pauseButton.background = resources.getDrawable(R.drawable.play)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isRunning) {
            pauseButton.background = resources.getDrawable(R.drawable.pause)
        }
    }
}