package com.mrindeciso.lib.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBoundFragment<T: ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T
): Fragment() {

    private var _binding: T? = null
    protected val viewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun withBinding(body: (T) -> Unit) {
        body.invoke(viewBinding)
    }

}