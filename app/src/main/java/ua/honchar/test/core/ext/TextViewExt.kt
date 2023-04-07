package ua.honchar.test.core.ext

import android.widget.TextView

fun TextView.switchText(newText: String?) {
    goneView {
        text = newText
        showView()
    }
}