# F45-Wallpaper Demo

This app rotates multiple wallpapers in a slide show of 5 seconds. The wallpaper slides are created using a combination of different mathetmatical shapes and colors . Each slide is  displayed for 5 seconds and is rotated forever until the app is closed

## Video link
https://youtu.be/6rwK9TwL7hs

## Some Info about the Project

- The app can Run on any device with OS >= 21.
- Used Kotlin for the whole project 
- Used decoupling approach by separating the View (WallPaperScreenActivity) from the functionality (WallpaperManager)
- The code is organized in a package by feature format
- Used material design style guide in style.xml 
- All the Wallpaper shapes (Rectangle , Circle and Triangle) are created only once in the begining and reused the cached shapes afterwards
- Wallpaper slides are created by applying the color codes to the shape image instance at run time in a sequence
- When app is paused care is taken to cancel the rotating timer and is reset back for the existing screen on app resuming back
- Tested on Samsung 9 device running on OS 26 



## Business Glossaries

 Vector - Rectangle , Circle and Triangle vector shapes
 Color - red, green, blue, purple, yellow used in creating slides
 Wallpaper Slide or Slide - An image slide created by using the combination of one of the above vector and color in a sequence. 
 Rotating time - the duration of 5 seconds during which the newly created wallpaper slide is shown to the user
 Group of shapes - Slides
 Screen - Wallpaper screen on the device
 Rotation - Moving from one slide to another after 5 seconds

## Technology Used :

**Platform** : Android

**Language** : Kotlin

**Libraries** : Android SDK, MaterialDesign framework and Appcompat libraries
                
