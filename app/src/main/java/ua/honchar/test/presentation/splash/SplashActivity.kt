package ua.honchar.test.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.honchar.test.R
import ua.honchar.test.base.BaseActivity
import ua.honchar.test.core.ext.click
import ua.honchar.test.core.ext.observe
import ua.honchar.test.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate)