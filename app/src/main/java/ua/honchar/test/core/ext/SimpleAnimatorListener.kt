package ua.honchar.test.core.ext

import android.animation.Animator

abstract class SimpleAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator) = Unit

    override fun onAnimationEnd(animation: Animator) = Unit

    override fun onAnimationCancel(animation: Animator) = Unit

    override fun onAnimationStart(animation: Animator) = Unit
}