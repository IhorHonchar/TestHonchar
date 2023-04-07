package ua.honchar.test.core.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

const val animationDuration = 200L

fun View.click(action: () -> Unit) {
    setOnClickListener { action.invoke() }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleIfOrGone(predicate: () -> Boolean) {
    if (predicate.invoke()) visible() else gone()
}

fun View.visibleWithAnimation(duration: Long, endAlpha: Float = 1f, onEnd: () -> Unit = {}) {
    if (View.VISIBLE == visibility) return
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, endAlpha).setDuration(duration)
    animator.addListener(object : SimpleAnimatorListener() {
        override fun onAnimationStart(animation: Animator) {
            this@visibleWithAnimation.visible()
        }

        override fun onAnimationEnd(animation: Animator) = onEnd.invoke()
    })
    animator.start()
}

fun View.goneWithAnimation(duration: Long, listener: () -> Unit = {}) {
    if (View.GONE == visibility) return
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f).setDuration(duration)
    animator.addListener(object : SimpleAnimatorListener() {
        override fun onAnimationEnd(animation: Animator) {
            listener.invoke()
            this@goneWithAnimation.gone()
        }
    })
    animator.start()
}

fun View.animateMargin(
    lMargin: Int,
    duration: Long = animationDuration
) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    ValueAnimator.ofInt(params?.leftMargin.orZero(), lMargin).apply {
        addUpdateListener { valueAnimator ->
            layoutParams = params?.apply { leftMargin = valueAnimator.animatedValue as Int }
        }
        this.duration = duration
    }.start()
}

fun View.showView(end: () -> Unit = {}) {
    if (isVisible) return
    this.alpha = 0f
    this.visibility = View.VISIBLE
    this.animate()
        .alpha(1f)
        .setDuration(animationDuration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                end()
            }
        })
}

fun View.goneView(end: () -> Unit = {}) {
    if (!isVisible) return
    this.animate()
        .alpha(0f)
        .setDuration(animationDuration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@goneView.visibility = View.GONE
                end()
            }
        })
}

fun View.visibleIfOrGoneAnimated(predicate: () -> Boolean, end: () -> Unit = {}) {
    if (predicate()) showView { end() } else goneView { end() }
}

fun View.animateDown(
    mDuration: Long = animationDuration,
    translation: Float
): ObjectAnimator =
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, translation).apply {
        duration = mDuration
        start()
    }

fun View.animateUp(mDuration: Long = animationDuration, translation: Float) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, translation).apply {
        duration = mDuration
    }.start()
}