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

class WallPaperActivity : AppCompatActivity() {
    
    private var wallPaperTimer: CountDownTimer? = null
    private var currentColor: Int = R.color.red
    
    private lateinit var currentShape: Drawable
    private lateinit var rectangle: GradientDrawable
    private lateinit var circle: GradientDrawable
    private lateinit var triangle: LayerDrawable
    
    // region override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setWallPaperImage()
    }
    
    override fun onStart() {
        super.onStart()
        startWallpaperTimer()
    }
    
    override fun onStop() {
        super.onStop()
        stopWallpaperTimer()
    }
    // endregion
    
    // region private
    
    private fun stopWallpaperTimer() {
        wallPaperTimer?.cancel()
    }
    
    private fun startWallpaperTimer() {
        wallPaperTimer = object : CountDownTimer(6 * 1000, 1000) {
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
        
        startWallpaperTimer()
    }
    
    private fun setWallPaperImage() {
        if (isTriangle()) {
            val rotateDrawable = triangle
                .findDrawableByLayerId(R.id.item_triangle).mutate() as RotateDrawable
            val drawable = rotateDrawable.drawable!!.mutate() as GradientDrawable
            
            drawable.setColor(ContextCompat.getColor(this, currentColor));
            view.background = triangle
        } else {
            val drawable = currentShape.mutate() as GradientDrawable
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
        when (currentShape) {
            
            rectangle -> currentShape = circle
            circle -> currentShape = triangle
            else -> {
                currentShape = rectangle
            }
        }
    }
    
    private fun isTriangle(): Boolean {
        return currentShape == triangle;
    }
    
    private fun init() {
        rectangle =
            ContextCompat.getDrawable(this, R.drawable.rectangle)?.mutate()!! as GradientDrawable
        circle = ContextCompat.getDrawable(this, R.drawable.circle)?.mutate()!! as GradientDrawable
        triangle = ContextCompat.getDrawable(this, R.drawable.triangle)!!.mutate() as LayerDrawable
        
        rectangle.setColor(ContextCompat.getColor(this, currentColor));
        view.background = rectangle
    
        currentShape = rectangle
    }
    // endregion
}
