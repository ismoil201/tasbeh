package com.ismoil.tasbeh.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ismoil.tasbeh.R
import com.ismoil.tasbeh.databinding.FragmentProfileBinding
import com.ismoil.tasbeh.utils.ThemeUtils


class ProfileFragment : Fragment() {


    private lateinit var  binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theme = ThemeUtils.loadTheme(requireContext())


        binding.root.setBackgroundResource(theme.backgroundImage)
        binding.btnClose.setBackgroundResource(theme.buttonBg)


        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.mainFragment);
        }
        val themes = view.findViewById<View>(R.id.themes)
        themes.setOnClickListener {
            findNavController().navigate(R.id.themeFragment)
        }



    }


}