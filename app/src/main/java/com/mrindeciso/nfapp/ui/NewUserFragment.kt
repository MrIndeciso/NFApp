package com.mrindeciso.nfapp.ui

import android.content.Intent
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mrindeciso.lib.extensions.*
import com.mrindeciso.lib.firebase.firestore.UserRepository
import com.mrindeciso.lib.models.User
import com.mrindeciso.lib.preferences.PreferenceManager
import com.mrindeciso.lib.ui.ViewBoundFragment
import com.mrindeciso.nfapp.R
import com.mrindeciso.nfapp.databinding.FragmentNewuserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_newuser.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class NewUserFragment: ViewBoundFragment<FragmentNewuserBinding>(FragmentNewuserBinding::inflate) {

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var preferenceManager: PreferenceManager

    @Inject
    lateinit var userRepository: UserRepository

    override fun onStart() {
        super.onStart()

        activity?.setTitle(R.string.login_button_login)

        withBinding {
            it.buttonGoogle.onClick {
                signInWithGoogle()
            }

            it.tvRegister.onClick {
                register()
            }
        }
    }

    private fun signInWithGoogle() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).apply {
            requestIdToken(getString(R.string.default_web_client_id))
            requestEmail()
            requestProfile()
        }.build()

        val signInClient = GoogleSignIn.getClient(requireContext(), signInOptions)

        val intent = signInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private suspend fun firebaseAuthWithGoogleToken(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        try {
            auth.signInWithCredential(credential)
                .await()
                .user!!
                .let {
                    val user = userRepository.getUserByUID(it.uid)

                    if (user != null) {
                        preferenceManager.currentUser = user
                        findNavController().navigate(R.id.menuHome)
                    } else {
                        register(
                            name = it.displayName?.split(' ')?.dropLast(1)?.joinToString(" "),
                            surname = it.displayName?.split(' ')?.lastOrNull(),
                            email = it.email,
                            uid = it.uid
                        )
                    }
                }
        } catch (e: Exception) {
            showErrorMessage(R.string.login_error_generic)
        }
    }

    private suspend fun firebaseAuthWithEmailPassword(email: String, password: String): String? {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .await()
                .user
                ?.uid
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun firebaseSignInWithEmailPassword(email: String, password: String): String? {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .await()
                .user
                ?.uid
        } catch (e: Exception) {
            null
        }
    }

    private fun showErrorMessage(@StringRes body: Int) {
        MaterialDialog(requireContext()) {
            title()
            message(textRes = body)
            positiveButton(autoDismiss = true)
        }
    }

    private fun register(
        name: String? = "",
        surname: String? = "",
        email: String? = "",
        uid: String? = null
    ): Unit = withBinding {
        it.tilName.isVisible = true
        it.tilSurname.isVisible = true
        it.tilPassword.isVisible = uid == null
        it.tilRepeatPassword.isVisible = uid == null

        it.tilName.editText?.setText(name)
        it.tilSurname.editText?.setText(surname)
        it.tilUsername.editText?.setText(email)

        it.tvRegister.setText(R.string.login_login_label)
        it.tvRegister.onClick {
            login()
        }

        it.buttonGoogle.isVisible = uid == null

        it.buttonLogin.setText(R.string.login_button_register)
        it.buttonLogin.onClick {
            if (tilPassword.editText?.text.toString() != tilRepeatPassword.editText?.text.toString()) {
                tilRepeatPassword.error = getString(R.string.login_error_label)
                return@onClick
            } else {
                tilRepeatPassword.error = null
            }

            lifecycleScope.launch {
                val userId = uid
                    ?: firebaseAuthWithEmailPassword(
                        it.tilUsername.editText?.text.toString(),
                        it.tilPassword.editText?.text.toString()
                    )

                if (userId == null) {
                    showErrorMessage(R.string.login_error_generic)
                    return@launch
                }

                User(
                    userId,
                    it.tilName.editText?.text.toString(),
                    it.tilSurname.editText?.text.toString(),
                    it.tilUsername.editText?.text.toString(),
                    null,
                    false
                ).let {  user ->
                    userRepository.addUser(user)

                    preferenceManager.currentUser = user

                    findNavController().navigate(R.id.menuHome)
                }
            }
        }
    }

    private fun login(): Unit = withBinding {
        it.tilName.isVisible = false
        it.tilSurname.isVisible = false
        it.tilRepeatPassword.isVisible = false

        it.tvRegister.setText(R.string.login_register_label)
        it.tvRegister.onClick {
            register()
        }

        it.buttonLogin.setText(R.string.login_button_login)
        it.buttonLogin.onClick {
            lifecycleScope.launch {
                val uid = firebaseSignInWithEmailPassword(
                    it.tilUsername.editText?.text.toString(),
                    it.tilPassword.editText?.text.toString()
                )

                if (uid == null) {
                    showErrorMessage(R.string.login_error_generic)
                } else {
                    val user = userRepository.getUserByUID(uid)

                    if (user == null) {
                        register() //This makes no sense though
                    } else {
                        preferenceManager.currentUser = user

                        findNavController().navigate(R.id.menuHome)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            RC_SIGN_IN -> {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val result = account.getResult(ApiException::class.java)

                    lifecycleScope.launch {
                        firebaseAuthWithGoogleToken(result?.idToken!!)
                    }
                } catch (e: ApiException) {
                    showErrorMessage(R.string.login_error_generic)
                } catch (e: NullPointerException) {
                    showErrorMessage(R.string.login_error_generic)
                }
            }
        }
    }

    companion object {

        private const val RC_SIGN_IN = 8756

    }

}