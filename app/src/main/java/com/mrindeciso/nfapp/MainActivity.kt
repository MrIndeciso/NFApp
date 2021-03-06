package com.mrindeciso.nfapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.mrindeciso.lib.models.User
import com.mrindeciso.lib.preferences.PreferenceManager
import com.mrindeciso.nfapp.databinding.ActivityMainBinding
import com.mrindeciso.nfapp.ui.mvvm.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        with(supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment) {
            viewBinding.bottomAppBar.setupWithNavController(navController)
        }

        mainActivityViewModel.refreshUser().observe(this, {
            if (it) recreate()
        })

        preferenceManager.currentUser?.let {
            if (it.permission_level == User.PermissionLevel.ADMINISTRATOR) {
                viewBinding.bottomAppBar.menu.findItem(R.id.menuAdministration).isVisible = true
            }

            checkForFCMToken(it)
        }

        hideBottomAppBar()
    }

    private fun checkForFCMToken(user: User) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            it.result?.let {
                if (it != user.fcmToken) {
                    mainActivityViewModel.updateFCMToken(user, it).observe(this) {
                        Log.i("MainActivity", "Updated FCM Token")
                    }
                }
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic("newPost").addOnCompleteListener {
            it.result?.let {
                Log.i("MainActivity", "Subscribed to topic newPost")
            }
        }
    }

    private fun hideBottomAppBar() {
        findNavController(R.id.fragmentContainer).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newUserFragment -> {
                    viewBinding.bottomAppBar.isVisible = false
                }
                else -> {
                    viewBinding.bottomAppBar.isVisible = true
                }
            }
        }
    }

}