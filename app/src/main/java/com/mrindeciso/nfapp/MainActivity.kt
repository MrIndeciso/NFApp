package com.mrindeciso.nfapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mrindeciso.nfapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        with(supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment) {
            viewBinding.bottomAppBar.setupWithNavController(this.navController)
        }

        hideBottomAppBar()
    }

    private fun hideBottomAppBar() {
        findNavController(R.id.fragmentContainer).addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
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