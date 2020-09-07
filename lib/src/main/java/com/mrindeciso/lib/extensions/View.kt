package com.mrindeciso.lib.extensions

import android.view.View

inline fun <T: View> T.onClick(
    crossinline action: () -> Unit
) {
    this.setOnClickListener {
        action.invoke()
    }
}