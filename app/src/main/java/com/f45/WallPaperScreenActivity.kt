package com.f45

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *  Wallpaper screen responsible for showing the slide show
 */
class WallPaperScreenActivity : AppCompatActivity() {
    
    companion object {
        private const val ROTATING_TIME_IN_MS = 5 * 1000L
        private const val TIME_ONE_MS = 1 * 1000L
        private const val TIME_SIXTY_SEC = 2 * 1000L
    }
    
    private var rotatingTimer: CountDownTimer? = null
    private lateinit var wallPaperManager: WallPaperManager
    
    // region override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wallPaperManager = WallPaperManager(this)
        view.background = wallPaperManager.currentSlide()
    }
    
    override fun onStart() {
        super.onStart()
        startSlideShow()
    }
    
    override fun onStop() {
        super.onStop()
        stopSlideShow()
    }
    // endregion
    
    // region private
    
    private fun stopSlideShow() {
        rotatingTimer?.cancel()
    }
    
    private fun startSlideShow() {
        rotatingTimer = object : CountDownTimer(ROTATING_TIME_IN_MS, TIME_ONE_MS) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / TIME_ONE_MS % TIME_SIXTY_SEC + 1L
                timer.text = getString(R.string.timer_text, second)
            }
            
            override fun onFinish() {
                showNextSlide()
                startSlideShow()
            }
        }.start()
    }
    
    /**
     *  Displays the next wall paper slide
     */
    private fun showNextSlide() {
        view.background = wallPaperManager.rotate()
    }
    // endregion
}
