package com.andrognito.flashbar

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.os.Build.VERSION_CODES.M
import android.support.annotation.ColorInt
import android.text.Spanned
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.widget.*
import com.andrognito.flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.FlashbarPosition.TOP
import com.andrognito.flashbar.utils.*

/**
 * The actual Flashbar view representation that can consist of the message, button, icon, etc.
 * Its size is adaptive and depends solely on the amount of content present in it. It always matches
 * the width of the screen.
 *
 * It can either be present at the top or at the bottom of the screen. It will always consume touch
 * events and respond as necessary.
 */
internal class FlashbarView : RelativeLayout {

    private lateinit var flashbarRootView: LinearLayout

    private lateinit var title: TextView
    private lateinit var message: TextView
    private lateinit var icon: ImageView
    private lateinit var button: Button

    constructor(context: Context) : super(context, null, 0) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.flash_bar_view, this)

        flashbarRootView = findViewById(R.id.fb_root) as LinearLayout

        with(flashbarRootView) {
            title = findViewById(R.id.fb_title) as TextView
            message = findViewById(R.id.fb_message) as TextView
            icon = findViewById(R.id.fb_icon) as ImageView
            button = findViewById(R.id.fb_action) as Button
        }
    }

    internal fun adjustWitPositionAndOrientation(activity: Activity,
                                                 flashbarPosition: FlashbarPosition) {
        val flashbarViewLp = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val statusBarHeight = activity.getStatusBarHeightInPx()

        when (flashbarPosition) {
            TOP -> {
                val flashbarViewContent = findViewById(R.id.fb_content)
                val flashbarViewContentLp = flashbarViewContent.layoutParams as LinearLayout.LayoutParams

                flashbarViewContentLp.topMargin = statusBarHeight
                flashbarViewContent.layoutParams = flashbarViewContentLp
                flashbarViewLp.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            }
            BOTTOM -> {
                flashbarViewLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            }
        }

        layoutParams = flashbarViewLp
    }

    internal fun setBarBackground(drawable: Drawable?) {
        if (drawable == null) return

        if (SDK_INT >= JELLY_BEAN) {
            this.flashbarRootView.background = drawable
        } else {
            this.flashbarRootView.setBackgroundDrawable(drawable)
        }
    }

    internal fun setBarBackgroundColor(@ColorInt color: Int?) {
        if (color == null) return
        this.flashbarRootView.setBackgroundColor(color)
    }

    internal fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) return

        this.title.text = title
        this.title.visibility = VISIBLE
    }

    internal fun setTitleSpanned(title: Spanned?) {
        if (title == null) return

        this.title.text = title
        this.title.visibility = VISIBLE
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        if (typeface == null) return
        title.typeface = typeface
    }

    internal fun setTitleSizeInPx(size: Float?) {
        if (size == null) return
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setTitleSizeInSp(size: Float?) {
        if (size == null) return
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setTitleColor(color: Int?) {
        if (color == null) return
        title.setTextColor(color)
    }

    internal fun setTitleAppearance(titleAppearance: Int?) {
        if (titleAppearance == null) return
        
        if (SDK_INT >= M) {
            this.title.setTextAppearance(titleAppearance)
        } else {
            this.title.setTextAppearance(title.context, titleAppearance)
        }
    }

    internal fun setMessage(message: String?) {
        if (TextUtils.isEmpty(message)) return

        this.message.text = message
        this.message.visibility = VISIBLE
    }

    fun setMessageSpanned(message: Spanned?) {
        if (message == null) return

        this.message.text = message
        this.message.visibility = VISIBLE
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.message.typeface = typeface
    }

    internal fun setMessageSizeInPx(size: Float?) {
        if (size == null) return
        this.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setMessageSizeInSp(size: Float?) {
        if (size == null) return
        this.message.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setMessageColor(color: Int?) {
        if (color == null) return
        this.message.setTextColor(color)
    }

    fun setMessageAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.message.setTextAppearance(messageAppearance)
        } else {
            this.message.setTextAppearance(message.context, messageAppearance)
        }
    }

    internal fun setButtonText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.button.text = text
        this.button.visibility = VISIBLE
    }

    fun setButtonTextSpanned(text: Spanned?) {
        if (text == null) return

        this.button.text = text
        this.button.visibility = VISIBLE
    }

    internal fun setButtonTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.button.typeface = typeface
    }

    internal fun setButtonTextSizeInPx(size: Float?) {
        if (size == null) return
        this.button.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setButtonTextSizeInSp(size: Float?) {
        if (size == null) return
        this.button.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setButtonTextColor(color: Int?) {
        if (color == null) return
        this.button.setTextColor(color)
    }

    fun setButtonTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.button.setTextAppearance(messageAppearance)
        } else {
            this.button.setTextAppearance(button.context, messageAppearance)
        }
    }

    internal fun showIcon(showIcon: Boolean) {
        icon.visibility = if (showIcon) VISIBLE else GONE
    }

    internal fun setIconDrawable(icon: Drawable?) {
        if (icon == null) return
        this.icon.setImageDrawable(icon)
    }

    internal fun setIconBitmap(bitmap: Bitmap?) {
        if (bitmap == null) return
        this.icon.setImageBitmap(bitmap)
    }

    internal fun setIconFilter(colorFilter: Int?, filterMode: PorterDuff.Mode?) {
        if (colorFilter == null) return
        if (filterMode == null) {
            this.icon.setColorFilter(colorFilter)
        } else {
            this.icon.setColorFilter(colorFilter, filterMode)
        }
    }
}

/**
 * Container view matching the height and width of the parent to hold a FlashbarView.
 * It will occupy the entire screens size but will be completely transparent. The
 * FlashbarView inside is the only visible component in it.
 */
internal class FlashbarContainerView(context: Context) : RelativeLayout(context) {

    private lateinit var flashbarView: FlashbarView

    private lateinit var enterAnimation: Animation
    private lateinit var exitAnimation: Animation

    private var isBarShowing = false
    private var isBarShown = false
    private var isBarDismissing = false

    private val enterAnimationListener = object : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation) {
            isBarShowing = true
        }

        override fun onAnimationEnd(animation: Animation) {
            isBarShowing = false
            isBarShown = true
        }

        override fun onAnimationRepeat(animation: Animation) {
            // NO-OP
        }
    }

    private val exitAnimationListener = object : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation?) {
            isBarDismissing = true
        }

        override fun onAnimationEnd(animation: Animation?) {
            isBarDismissing = false
            isBarShown = false

            // Removing container after animation end
            post { (parent as? ViewGroup)?.removeView(this@FlashbarContainerView) }
        }

        override fun onAnimationRepeat(animation: Animation?) {
            // NO-OP
        }
    }

    internal fun add(flashbarView: FlashbarView) {
        this.flashbarView = flashbarView
        addView(flashbarView)
    }

    internal fun adjustPositionAndOrientation(activity: Activity) {
        val flashbarContainerViewLp = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        val navigationBarPosition = activity.getNavigationBarPosition()
        val navigationBarSize = activity.getNavigationBarSizeInPx()

        when (navigationBarPosition) {
            NavigationBarPosition.LEFT -> flashbarContainerViewLp.leftMargin = navigationBarSize
            NavigationBarPosition.RIGHT -> flashbarContainerViewLp.rightMargin = navigationBarSize
            NavigationBarPosition.BOTTOM -> flashbarContainerViewLp.bottomMargin = navigationBarSize
        }

        layoutParams = flashbarContainerViewLp
    }

    internal fun show(activity: Activity) {
        if (isBarShowing || isBarShown) {
            return
        }

        val activityRootView = activity.getRootView()
        activityRootView?.addView(this)

        enterAnimation.setAnimationListener(enterAnimationListener)
        flashbarView.startAnimation(enterAnimation)
    }

    internal fun dismiss() {
        if (isBarDismissing || isBarShowing || !isBarShown) {
            return
        }

        exitAnimation.setAnimationListener(exitAnimationListener)
        flashbarView.startAnimation(exitAnimation)
    }

    internal fun isBarShowing() = isBarShowing

    internal fun isBarShown() = isBarShown

    internal fun setTitle(title: String?) {
        flashbarView.setTitle(title)
    }

    internal fun setTitleSpanned(title: Spanned?) {
        flashbarView.setTitleSpanned(title)
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        flashbarView.setTitleTypeface(typeface)
    }

    internal fun setTitleSizeInPx(size: Float?) {
        flashbarView.setTitleSizeInPx(size)
    }

    internal fun setTitleSizeInSp(size: Float?) {
        flashbarView.setTitleSizeInSp(size)
    }

    internal fun setTitleColor(color: Int?) {
        flashbarView.setTitleColor(color)
    }

    internal fun setTitleAppearance(titleAppearance: Int?) {
        flashbarView.setTitleAppearance(titleAppearance)
    }

    internal fun setMessage(message: String?) {
        flashbarView.setMessage(message)
    }

    internal fun setMessageSpanned(message: Spanned?) {
        flashbarView.setMessageSpanned(message)
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        flashbarView.setMessageTypeface(typeface)
    }

    internal fun setMessageSizeInPx(size: Float?) {
        flashbarView.setMessageSizeInPx(size)
    }

    internal fun setMessageSizeInSp(size: Float?) {
        flashbarView.setMessageSizeInSp(size)
    }

    internal fun setMessageColor(color: Int?) {
        flashbarView.setMessageColor(color)
    }

    internal fun setMessageAppearance(messageAppearance: Int?) {
        flashbarView.setMessageAppearance(messageAppearance)
    }

    internal fun setButtonText(text: String?) {
        flashbarView.setButtonText(text)
    }

    internal fun setButtonTextSpanned(text: Spanned?) {
        flashbarView.setButtonTextSpanned(text)
    }

    internal fun setButtonTextTypeface(typeface: Typeface?) {
        flashbarView.setButtonTextTypeface(typeface)
    }

    internal fun setButtonTextSizeInPx(size: Float?) {
        flashbarView.setButtonTextSizeInPx(size)
    }

    internal fun setButtonTextSizeInSp(size: Float?) {
        flashbarView.setButtonTextSizeInSp(size)
    }

    internal fun setButtonTextColor(color: Int?) {
        flashbarView.setButtonTextColor(color)
    }

    internal fun setButtonTextAppearance(appearance: Int?) {
        flashbarView.setButtonTextAppearance(appearance)
    }

    internal fun showIcon(showIcon: Boolean) {
        flashbarView.showIcon(showIcon)
    }

    internal fun setEnterAnimation(animation: Animation) {
        enterAnimation = animation
    }

    internal fun setExitAnimation(animation: Animation) {
        exitAnimation = animation
    }

    internal fun setBarBackgroundColor(@ColorInt color: Int?) {
        flashbarView.setBarBackgroundColor(color)
    }

    internal fun setBarBackgroundDrawable(drawable: Drawable?) {
        flashbarView.setBarBackground(drawable)
    }

    internal fun setIconDrawable(icon: Drawable?) {
        flashbarView.setIconDrawable(icon)
    }

    internal fun setIconBitmap(bitmap: Bitmap?) {
        flashbarView.setIconBitmap(bitmap)
    }

    internal fun setIconColorFilter(colorFilter: Int?, filterMode: PorterDuff.Mode?) {
        flashbarView.setIconFilter(colorFilter, filterMode)
    }
}
