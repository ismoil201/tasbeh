package com.ismoil.tasbeh

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ismoil.tasbeh.databinding.ActivityMainBinding
import com.ismoil.tasbeh.notification.DailyNotificationWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 설정
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // NavController 설정
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val navController = navHostFragment.navController
        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyNotificationWorker>(
            1, TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(layoutInflater.context).enqueueUniquePeriodicWork(
            "daily_notification",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()

    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    // 뒤로가기 버튼 동작

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

}
