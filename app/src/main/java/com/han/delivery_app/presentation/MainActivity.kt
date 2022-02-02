package com.han.delivery_app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.han.delivery_app.R
import com.han.delivery_app.databinding.ActivityMainBinding
import com.han.delivery_app.work.TrackingCheckWorker
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initWorker()
    }

    private fun initWorker() {
        val workerStartTime = Calendar.getInstance()
        workerStartTime.set(Calendar.HOUR_OF_DAY, 16)
        val initialDelay = workerStartTime.timeInMillis - System.currentTimeMillis()
        val dailyTrackingCheckRequest =
            PeriodicWorkRequestBuilder<TrackingCheckWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "DailyTrackingCheck",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyTrackingCheckRequest
            )
    }

    private fun initView() {
        val navigationController =
            (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController
        binding.toolbar.setupWithNavController(navigationController)
    }
}