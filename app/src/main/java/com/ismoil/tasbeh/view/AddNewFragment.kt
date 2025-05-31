package com.ismoil.tasbeh.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ismoil.tasbeh.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ismoil.tasbeh.R
import com.ismoil.tasbeh.databinding.FragmentAddNewBinding
import com.ismoil.tasbeh.utils.ThemeUtils

class AddNewFragment : Fragment() {

    private lateinit var binding : FragmentAddNewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewBinding.inflate(layoutInflater,container,false)

        val fragments = listOf(
            ListFragment(),
            SuccseccFragment()
        )

        val titles = listOf(
            "Kunlik Zikr Jadvali",
            "Yakunlangan Zikrlar"
        )


          binding.viewPager.adapter = ViewPagerAdapter(this, fragments)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val theme = ThemeUtils.loadTheme(requireContext())


        binding.root.setBackgroundResource(theme.backgroundImage)
        binding.tabLayout.setBackgroundResource(theme.backgroundImage)

    }
}
