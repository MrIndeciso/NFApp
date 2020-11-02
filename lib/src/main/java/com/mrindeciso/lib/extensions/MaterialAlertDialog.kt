package com.mrindeciso.lib.extensions

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mrindeciso.lib.R

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

@Suppress("FunctionName")
inline fun <reified T : ViewBinding> MaterialDialog(
    context: Context,
    layout: T,
    params: (T.(AlertDialog) -> Unit)
): AlertDialog {
    return MaterialAlertDialogBuilder(context).apply {
        setView(layout.root)
    }.show().apply {
        params(layout, this)
    }
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

fun MaterialAlertDialogBuilder.title(
    text: String? = null,
    @StringRes textRes: Int? = null
) {
    if (text != null) {
        setTitle(text)
    }
    if (textRes != null) {
        setTitle(textRes)
    }
}

fun MaterialAlertDialogBuilder.positiveButton(
    text: String? = null,
    @StringRes textRes: Int? = null,
    autoDismiss: Boolean = false
) {
    if (text != null) {
        setPositiveButton(text) { dialog, _ ->
            if (autoDismiss) dialog.dismiss()
        }
    } else if (textRes != null) {
        setPositiveButton(textRes) { dialog, _ ->
            if (autoDismiss) dialog.dismiss()
        }
    } else {
        setPositiveButton(R.string.dialog_defaultPositiveButton) { dialog, _ ->
            if (autoDismiss) dialog.dismiss()
        }
    }
}