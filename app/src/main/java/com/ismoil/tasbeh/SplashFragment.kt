package com.ismoil.tasbeh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Splashdan keyin 2 soniyada boshqa Activityga o'tish



        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(
                R.id.action_splashFragment_to_mainFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true) // 👈 bu joy muhim
                    .build()
            )

        }, 2500)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

}