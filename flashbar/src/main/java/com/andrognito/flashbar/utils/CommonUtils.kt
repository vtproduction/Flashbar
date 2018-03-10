package com.andrognito.flashbar.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.Display
import android.view.Surface.*
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.andrognito.flashbar.utils.NavigationBarPosition.*
import java.lang.reflect.InvocationTargetException

enum class NavigationBarPosition {
    BOTTOM,
    RIGHT,
    LEFT,
    TOP
}

internal fun Activity.getStatusBarHeightInPx(): Int {
    val rectangle = Rect()

    window.decorView.getWindowVisibleDisplayFrame(rectangle)

    val statusBarHeight = rectangle.top
    val contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).top

    return contentViewTop - statusBarHeight
}

internal fun Activity.getNavigationBarPosition(): NavigationBarPosition {
    return when (windowManager.defaultDisplay.rotation) {
        ROTATION_0 -> BOTTOM
        ROTATION_90 -> RIGHT
        ROTATION_270 -> LEFT
        else -> TOP
    }
}

internal fun Activity.getNavigationBarSizeInPx(): Int {
    val realScreenSize = getRealScreenSize()
    val appUsableScreenSize = getAppUsableScreenSize()
    val navigationBarPosition = getNavigationBarPosition()

    return if (navigationBarPosition == LEFT || navigationBarPosition == RIGHT) {
        realScreenSize.x - appUsableScreenSize.x
    } else {
        realScreenSize.y - appUsableScreenSize.y
    }
}

internal fun Activity?.getRootView(): ViewGroup? {
    if (this == null || window == null || window.decorView == null) {
        return null
    }
    return window.decorView as ViewGroup
}

private fun Activity.getRealScreenSize(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val defaultDisplay = windowManager.defaultDisplay
    val size = Point()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        defaultDisplay.getRealSize(size)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        try {
            size.x = Display::class.java.getMethod("getRawWidth").invoke(defaultDisplay) as Int
            size.y = Display::class.java.getMethod("getRawHeight").invoke(defaultDisplay) as Int
        } catch (e: IllegalAccessException) {
        } catch (e: InvocationTargetException) {
        } catch (e: NoSuchMethodException) {
        }
    }
    return size
}

private fun Activity.getAppUsableScreenSize(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val defaultDisplay = windowManager.defaultDisplay
    val size = Point()
    defaultDisplay.getSize(size)
    return size
}