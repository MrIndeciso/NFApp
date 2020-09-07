package com.mrindeciso.lib.extensions

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Suppress("FunctionName")
fun MaterialDialog(
    context: Context,
    @LayoutRes layout: Int? = null,
    params: (MaterialAlertDialogBuilder.() -> Unit)? = null
): AlertDialog {
    return MaterialAlertDialogBuilder(context).apply {
        if (layout != null) {
            setView(layout)
        }
        params?.invoke(this)
    }.show()
}

fun MaterialAlertDialogBuilder.message(
    text: String? = null,
    @StringRes textRes: Int? = null
) {
    if (text != null) {
        setMessage(text)
    }
    if (textRes != null) {
        setMessage(textRes)
    }
}