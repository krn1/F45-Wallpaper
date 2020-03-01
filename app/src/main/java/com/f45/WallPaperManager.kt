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
    private var currentSlide: Drawable
    
    private val rectangularVector: GradientDrawable
    private val circularVector: GradientDrawable
    private val triangularVector: LayerDrawable
    
    init {
        rectangularVector =
            ContextCompat.getDrawable(context, R.drawable.rectangle)?.mutate()!! as GradientDrawable
        circularVector =
            ContextCompat.getDrawable(context, R.drawable.circle)?.mutate()!! as GradientDrawable
        triangularVector =
            ContextCompat.getDrawable(context, R.drawable.triangle)!!.mutate() as LayerDrawable
        
        currentSlide = triangularVector
        createNextSlide()
    }
    
    // region helper methods
    fun rotate() : Drawable{
        createNextSlide()
        return currentSlide()
    }
    
    fun currentSlide(): Drawable {
        return currentSlide
    }
    // endregion helper methods
    
    // region private
    /**
     *  Creates the next slide from the combination of color and the shape in
     *  a sequence
     */
    private fun createNextSlide() {
        updateNextColor()
        updateNextShape()
        if (isTriangle()) {
            val rotateDrawable = triangularVector
                .findDrawableByLayerId(R.id.item_triangle).mutate() as RotateDrawable
            val drawable = rotateDrawable.drawable!!.mutate() as GradientDrawable
            
            drawable.setColor(ContextCompat.getColor(context, currentColor))
            currentSlide = triangularVector
        } else {
            val drawable = currentSlide.mutate() as GradientDrawable
            drawable.setColor(ContextCompat.getColor(context, currentColor))
            currentSlide = drawable
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
        when (currentSlide) {
            
            rectangularVector -> currentSlide = circularVector
            circularVector -> currentSlide = triangularVector
            else -> {
                currentSlide = rectangularVector
            }
        }
    }
    
    private fun isTriangle(): Boolean {
        return currentSlide == triangularVector;
    }
    
    // endregion
}