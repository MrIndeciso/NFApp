package com.mrindeciso.nfapp.ui.administration

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.mrindeciso.lib.extensions.onClick
import com.mrindeciso.lib.models.News
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.databinding.FragmentNewPostBinding
import com.mrindeciso.nfapp.ui.administration.mvvm.NewPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostFragment : ViewBoundFragment<FragmentNewPostBinding>(FragmentNewPostBinding::inflate) {

    companion object {
        private const val REQUEST_CODE = 2758
    }

    private val newPostViewModel by viewModels<NewPostViewModel>()

    override fun onStart() {
        super.onStart()

        viewBinding.button.onClick {
            withBinding {
                val post = News(
                    Timestamp.now(),
                    it.tilTitle.editText?.text.toString(),
                    it.tilAuthor.editText?.text.toString(),
                    it.tilContent.editText?.text.toString(),
                    null,
                    null,
                    null
                )

                newPostViewModel.createPost(post).observe(this) {
                    findNavController().navigateUp()
                }
            }
        }

        viewBinding.tilImage.setEndIconOnClickListener {
            Intent(ACTION_OPEN_DOCUMENT).also {
                it.addCategory(Intent.CATEGORY_OPENABLE)
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE)
            }
        }

        newPostViewModel.imageByteArray.observe(this) {
            it?.let {
                viewBinding.tilImage.editText?.setText(it.displayName)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                newPostViewModel.convertImageURIToByteArray(uri)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}

