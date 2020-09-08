package com.mrindeciso.lib.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

inline fun <reified T> ViewGroup.inflateBinding(
    func: (LayoutInflater, ViewGroup?, Boolean) -> T
): T {
    return func(
        LayoutInflater.from(this.context),
        this,
        false
    )
}