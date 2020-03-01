package com.f45

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import androidx.core.content.ContextCompat

/**
 *  This helper class  takes care of all the leg work of managing and keeping track of the
 *  wallpaper slide show.
 */
class WallPaperManager(private val context: Context) {
    
    private var currentColor: Int = R.color.yellow
    private var currentWallpaper: Drawable
    
    private val rectangularWallpaper: GradientDrawable
    private val circularWallpaper: GradientDrawable
    private val triangularWallpaper: LayerDrawable
    
    init {
        rectangularWallpaper =
            ContextCompat.getDrawable(context, R.drawable.rectangle)?.mutate()!! as GradientDrawable
        circularWallpaper =
            ContextCompat.getDrawable(context, R.drawable.circle)?.mutate()!! as GradientDrawable
        triangularWallpaper =
            ContextCompat.getDrawable(context, R.drawable.triangle)!!.mutate() as LayerDrawable
        
        currentWallpaper = triangularWallpaper
        createNextWallPaperImage()
    }
    
    // region helper methods
    fun nextSlide() : Drawable{
        createNextWallPaperImage()
        return currentSlide()
    }
    
    fun currentSlide(): Drawable {
        return currentWallpaper
    }
    // endregion helper methods
    
    // region private
    /**
     *  Creates the next wallpaper slide from the combination of color and the shape in
     *  a sequence
     */
    private fun createNextWallPaperImage() {
        updateNextColor()
        updateNextShape()
        if (isTriangle()) {
            val rotateDrawable = triangularWallpaper
                .findDrawableByLayerId(R.id.item_triangle).mutate() as RotateDrawable
            val drawable = rotateDrawable.drawable!!.mutate() as GradientDrawable
            
            drawable.setColor(ContextCompat.getColor(context, currentColor))
            currentWallpaper = triangularWallpaper
        } else {
            val drawable = currentWallpaper.mutate() as GradientDrawable
            drawable.setColor(ContextCompat.getColor(context, currentColor))
            currentWallpaper = drawable
        }
    }
    
    /**
     *  Updates the color in a sequence to help creating next slide
     */
    private fun updateNextColor() {
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
    
    /**
     *  Updates the shape in a sequence to create next slide
     */
    private fun updateNextShape() {
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
    
    // endregion
}