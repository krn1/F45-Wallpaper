package com.f45

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class WallPaperScreenActivity : AppCompatActivity() {
    
    private var currentColor: Int = R.color.red
    private lateinit var currentWallpaper: Drawable
    private var rotatingTimer: CountDownTimer? = null
    private lateinit var rectangularWallpaper: GradientDrawable
    private lateinit var circularWallpaper: GradientDrawable
    private lateinit var triangularWallpaper: LayerDrawable
    
    // region override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setWallPaperImage()
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
        rotatingTimer = object : CountDownTimer(6 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000 % 60
                timer.text = getString(R.string.timer_text, second)
            }
            
            override fun onFinish() {
                changeWallpaper()
            }
        }.start()
    }
    
    private fun changeWallpaper() {
        nextColor()
        nextShape()
        setWallPaperImage()
        
        startSlideShow()
    }
    
    /**
     *  Creates wallpaper image from current shapes and current color and displays it to the user
     */
    private fun setWallPaperImage() {
        if (isTriangle()) {
            val rotateDrawable = triangularWallpaper
                .findDrawableByLayerId(R.id.item_triangle).mutate() as RotateDrawable
            val drawable = rotateDrawable.drawable!!.mutate() as GradientDrawable
            
            drawable.setColor(ContextCompat.getColor(this, currentColor));
            view.background = triangularWallpaper
        } else {
            val drawable = currentWallpaper.mutate() as GradientDrawable
            drawable.setColor(ContextCompat.getColor(this, currentColor));
            view.background = drawable
        }
    }
    
    private fun nextColor() {
        when (currentColor) {
            R.color.red -> currentColor = R.color.green
            R.color.green -> currentColor = R.color.blue
            R.color.blue -> currentColor = R.color.purple
            R.color.purple -> currentColor = R.color.yellow
            else -> {
                currentColor = R.color.red
            }
        }
    }
    
    private fun nextShape() {
        when (currentWallpaper) {
            
            rectangularWallpaper -> currentWallpaper = circularWallpaper
            circularWallpaper -> currentWallpaper = triangularWallpaper
            else -> {
                currentWallpaper = rectangularWallpaper
            }
        }
    }
    
    private fun isTriangle(): Boolean {
        return currentWallpaper == triangularWallpaper;
    }
    
    /**
     *  Initializes all the shapes from the xml into drawable memory objects so it is reused for rotation
     */
    private fun init() {
        rectangularWallpaper =
            ContextCompat.getDrawable(this, R.drawable.rectangle)?.mutate()!! as GradientDrawable
        circularWallpaper = ContextCompat.getDrawable(this, R.drawable.circle)?.mutate()!! as GradientDrawable
        triangularWallpaper = ContextCompat.getDrawable(this, R.drawable.triangle)!!.mutate() as LayerDrawable
        
        currentWallpaper = rectangularWallpaper
    }
    // endregion
}
