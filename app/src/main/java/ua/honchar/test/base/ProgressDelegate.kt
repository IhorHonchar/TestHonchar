package ua.honchar.test.base

import androidx.fragment.app.FragmentActivity

interface ProgressDelegate {
    fun showLoading(activity: FragmentActivity)
    fun hideLoading(activity: FragmentActivity)
}

class ProgressDelegateImpl(
    private val progress: ProgressDialog
): ProgressDelegate {
    override fun showLoading(activity: FragmentActivity) {
        progress.show(activity)
    }

    override fun hideLoading(activity: FragmentActivity) {
        progress.removeDialog(activity)
    }
}