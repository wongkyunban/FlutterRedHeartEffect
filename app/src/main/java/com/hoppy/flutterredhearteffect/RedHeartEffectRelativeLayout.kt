package com.hoppy.flutterredhearteffect

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.CycleInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.animation.addListener

class RedHeartEffectRelativeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mDuration = 3000L

    private val color = intArrayOf(
        -0xcb4d, -0x6532ce, -0x6bff2d, -0x116600,
        -0x493f, -0x258f2a, -0x74ff75, -0xb4ff7e,
        -0xb7c275, -0xe16f01, -0xff4001, -0xff0081
    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun createHeartView(): ImageView {
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.ic_heart)
//        val layoutParams = LayoutParams(50, 50)
//        layoutParams.addRule(ALIGN_PARENT_BOTTOM)
//        layoutParams.addRule(CENTER_HORIZONTAL)
//        imageView.layoutParams = layoutParams
        imageView.imageTintList =
            ColorStateList.valueOf(color[(color.size * Math.random()).toInt()])
        return imageView
    }

    @SuppressLint("Recycle")
    private fun createAlpha(imageView: ImageView): Animator {
        val animator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.1f)
        animator.duration = mDuration
        animator.interpolator = AccelerateInterpolator()
        return animator
    }

    @SuppressLint("Recycle")
    private fun createRotation(imageView: ImageView): Animator {
        val animator =
            ObjectAnimator.ofFloat(imageView, "rotation", 0f, (25f * Math.random()).toFloat())
        animator.duration = mDuration
        animator.interpolator = CycleInterpolator((6f * Math.random()).toFloat())
        return animator
    }

    @SuppressLint("Recycle")
    private fun createScale(imageView: ImageView): Animator {
        val animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1.5f)
        val animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.5f)
        val animatorSet = AnimatorSet()
        animatorSet.duration = mDuration
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.play(animatorX).with(animatorY)
        return animatorSet
    }

    private fun createTranslationX(imageView: ImageView,x:Float): Animator {
        val animator = ObjectAnimator.ofFloat(imageView, "translationX", x,(x+Math.random()*60).toFloat(),(x-Math.random()*60).toFloat())
        animator.duration = mDuration
        animator.interpolator = CycleInterpolator((1 * Math.random()).toFloat())
        return animator
    }

    private fun createTranslationY(imageView: ImageView,y:Float): Animator {
        val animator = ObjectAnimator.ofFloat(imageView, "translationY", y, 0f)
        animator.duration = mDuration
        animator.interpolator = AccelerateInterpolator()
        return animator
    }

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun start(x:Float=(width/2).toFloat(),y:Float = (height/2).toFloat()) {
        val imageView = createHeartView()
        addView(imageView)
        val animatorSet = AnimatorSet()
        animatorSet.play(createTranslationX(imageView,x))
            .with(createTranslationY(imageView,y))
            .with(createScale(imageView))
            .with(createRotation(imageView))
            .with(createAlpha(imageView))
        animatorSet.addListener(onEnd = {
            removeView(imageView)
        })
        animatorSet.start()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}