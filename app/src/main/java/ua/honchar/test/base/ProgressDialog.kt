package ua.honchar.test.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import ua.honchar.test.databinding.DialogProgressBinding

class ProgressDialog : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogProgressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.attributes?.gravity = Gravity.CENTER
    }

    fun show(activity: FragmentActivity) {
        try {
            val fm = activity.supportFragmentManager
            if (isAdded || fm.findFragmentByTag(PROGRESS_TAG) != null) return
            val ft = fm.beginTransaction()
            show(ft, PROGRESS_TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun removeDialog(activity: FragmentActivity) {
        val prev = activity.supportFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (prev != null) {
            val ft = activity.supportFragmentManager.beginTransaction()
            (prev as DialogFragment).dismissAllowingStateLoss()
            try {
                ft.remove(prev).commit()
                activity.supportFragmentManager.executePendingTransactions()
            } catch (ignored: IllegalStateException) {
            }
        }
    }

    companion object {
        private const val PROGRESS_TAG = "progress_tag"
    }
}