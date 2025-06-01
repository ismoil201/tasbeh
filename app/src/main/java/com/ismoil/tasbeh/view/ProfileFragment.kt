package com.ismoil.tasbeh.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ismoil.tasbeh.R
import com.ismoil.tasbeh.databinding.FragmentProfileBinding
import com.ismoil.tasbeh.notification.DailyNotificationWorker
import com.ismoil.tasbeh.utils.ThemeUtils
import java.util.concurrent.TimeUnit


class ProfileFragment : Fragment() {


    private lateinit var  binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theme = ThemeUtils.loadTheme(requireContext())


        binding.root.setBackgroundResource(theme.backgroundImage)
        binding.btnClose.setBackgroundResource(theme.buttonBg)


        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.mainFragment);
        }
        val themes = view.findViewById<View>(R.id.themes)
        themes.setOnClickListener {
            findNavController().navigate(R.id.themeFragment)
        }

        val language = view.findViewById<View>(R.id.language)
        language.setOnClickListener {
            Toast.makeText(requireContext(), "Kechirasiz, vaqtinchalik mavjud emas!", Toast.LENGTH_SHORT).show()
        }





        val sharedPref = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isNotificationEnabled = sharedPref.getBoolean("notifications", true)


        val switchNotification = view.findViewById<Switch>(R.id.switchNotification)

        val textStatus = view.findViewById<TextView>(R.id.tv_yoqilgan)


        switchNotification.isChecked = isNotificationEnabled
        textStatus.text = if (isNotificationEnabled) "Yoqilgan" else "O'chirilgan"


        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("notifications", isChecked).apply()
            textStatus.text = if (isChecked) "Yoqilgan" else "O'chirilgan"

            if (isChecked) {
                scheduleDailyNotification()
            } else {
                cancelDailyNotification()
            }
        }



        val touchSwitch = view.findViewById<Switch>(R.id.touch_switch)

        val textTouch = view.findViewById<TextView>(R.id.tv_touch_title)


        switchNotification.isChecked = isNotificationEnabled
        textTouch.text = if (isNotificationEnabled) "Yoqilgan" else "O'chirilgan"


        touchSwitch.setOnCheckedChangeListener { _, isChecked ->
            textTouch.text = if (isChecked) "Yoqilgan" else "O'chirilgan"
        }
    }

    private fun scheduleDailyNotification() {
        val request = PeriodicWorkRequestBuilder<DailyNotificationWorker>(
            1, TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "daily_notification",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun cancelDailyNotification() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork("daily_notification")
    }
}
