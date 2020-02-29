package com.wallpaper

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null
    private var currentColor: Int = Color.RED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view.setBackgroundResource(R.drawable.rectangle)
        downTimer()

    }

    private fun downTimer() {
        countDownTimer = object : CountDownTimer(5 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000 % 60
                val minutes = millisUntilFinished / (1000 * 60) % 60
                // textViewCountDownTimer.setText("$minutes:$second")
            }

            override fun onFinish() {
               print("changing ..")
                changeColor()
            }
        }.start()
    }

    private fun changeColor() {
        if (currentColor == Color.RED) {
            view.setBackgroundColor(Color.GREEN)
        } else {
            view.setBackgroundColor(Color.RED)
        }

        downTimer()
    }
}
