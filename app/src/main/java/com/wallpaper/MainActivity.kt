package com.wallpaper

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    
    private var wallPaperTimer: CountDownTimer? = null
    private var currentColor: Int = R.color.red
    private var currentShape: Int = R.drawable.rectangle
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        startWallPaper()
    }
    
    override fun onStart() {
        super.onStart()
        startWallpaperTimer()
    }
    
    override fun onStop() {
        super.onStop()
        stopWallpaperTimer()
    }
    
    // region private
    
    private fun startWallPaper() {
        val drawable = ContextCompat.getDrawable(this, currentShape)?.mutate()!! as GradientDrawable
        drawable.setColor(ContextCompat.getColor(this, currentColor));
        view.background = drawable
    }
    
    private fun stopWallpaperTimer() {
        wallPaperTimer?.cancel()
    }
    
    private fun startWallpaperTimer() {
        wallPaperTimer = object : CountDownTimer(6 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000 % 60
                val timerText = resources.getString(R.string.timer_text, second) //"You have 4 new messages."
                timer.text = timerText
            }
            
            override fun onFinish() {
                changeWallpaper()
            }
        }.start()
    }
    
    private fun changeWallpaper() {
        nextColor()
        nextShape()
        
        val drawable: GradientDrawable
        if (isTriangle()) {
            Log.e("", "Triangle");
            val layerDrawable =
                ContextCompat.getDrawable(this, currentShape)!!.mutate() as LayerDrawable
            val rotateDrawable = layerDrawable
                .findDrawableByLayerId(R.id.item_triangle).mutate() as RotateDrawable
            drawable = rotateDrawable.drawable!!.mutate() as GradientDrawable
            
            drawable.setColor(ContextCompat.getColor(this, currentColor));
            view.background = layerDrawable
        } else {
            drawable = ContextCompat.getDrawable(this, currentShape)?.mutate()!! as GradientDrawable
            Log.e("", "not a Triangle");
            drawable.setColor(ContextCompat.getColor(this, currentColor));
            view.background = drawable
        }
        
        startWallpaperTimer()
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
            R.drawable.rectangle -> currentShape = R.drawable.circle
            R.drawable.circle -> currentShape = R.drawable.triangle
            R.drawable.triangle -> currentShape = R.drawable.rectangle
            else -> {
                currentShape = R.drawable.rectangle
            }
        }
    }
    
    private fun isTriangle(): Boolean {
        return currentShape == R.drawable.triangle;
    }
    
    // endregion
}
