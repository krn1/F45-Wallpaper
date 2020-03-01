package com.wallpaper

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var wallPaperTimer: CountDownTimer? = null
    private var currentColor: Int = R.color.red
    private var currentShape: Int = R.drawable.triangle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextShape()
        btnWallPaperManager.setOnClickListener {
            if (btnWallPaperManager.text == getString(R.string.btn_start)) {
                startWallpaperTimer()
            } else {
                stopWallpaperTimer()
            }
        }
    }

    // region private
    private fun stopWallpaperTimer() {
        wallPaperTimer?.cancel()
    }

    private fun startWallpaperTimer() {
        wallPaperTimer = object : CountDownTimer(5 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000 % 60

                btnWallPaperManager.setText("00:0$second")
            }

            override fun onFinish() {
                nextShape()
            }
        }.start()
    }

    private fun changeWallpaper() {
        nextColor()
        nextShape()

        if (currentShape == R.drawable.triangle) {
            val layerDrawable = ContextCompat.getDrawable(this, currentShape) as LayerDrawable
            val rotateDrawable = layerDrawable
                .findDrawableByLayerId(R.id.item_triangle) as RotateDrawable
            val drawable = rotateDrawable.drawable as GradientDrawable
            drawable.mutate()
            drawable.setColor(currentColor)
            view.setImageDrawable(drawable)

        } else {
            val drawable = ContextCompat.getDrawable(this, currentShape) as GradientDrawable
            drawable.mutate()
            drawable.setColor(currentColor)
            view.setImageDrawable(drawable)
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

        nextColor()
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


        //drawable?.mutate()?.setTint(currentColor)
        // drawable?.mutate()?.setTint(currentColor);


        // view.setColorFilter(currentColor)
//        if(currentShape == R.drawable.triangle) {
//            val layerDrawable = ContextCompat.getDrawable(this, currentShape) as LayerDrawable
//            val rotateDrawable = layerDrawable
//                .findDrawableByLayerId(R.id.item_triangle) as RotateDrawable
//            val drawable = rotateDrawable.drawable as GradientDrawable
//            drawable.mutate()
//            drawable.setColor(currentColor)
//            view.setImageDrawable(drawable)
//
//        } else {
//            val drawable = ContextCompat.getDrawable(this, currentShape) as GradientDrawable
//            drawable.mutate()
//            drawable.setColor(currentColor)
//            view.setImageDrawable(drawable)
//        }

        //   Log.e("TAG", "changing .." + currentColor)
        startWallpaperTimer()
    }

    private fun isTriangle(): Boolean {
        return currentShape == R.drawable.triangle;
    }


    // endregion
}
