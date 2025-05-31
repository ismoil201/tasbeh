package com.ismoil.tasbeh.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ismoil.tasbeh.R
import com.ismoil.tasbeh.adapter.ThemeAdapter
import com.ismoil.tasbeh.databinding.FragmentThemeBinding
import com.ismoil.tasbeh.model.Theme


class ThemeFragment : Fragment(), ThemeAdapter.CallBack {


    lateinit var  binding: FragmentThemeBinding
    private var themeList = mutableListOf<Theme>()
    private lateinit var adapter  : ThemeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentThemeBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initTheme()

        adapter = ThemeAdapter(themeList, this)

        binding.recyclerView.adapter = adapter
    }

    private fun initTheme(){
        themeList.add(Theme(R.drawable.counter__1_,R.drawable.img_7,
            R.drawable.button_selector))
        themeList.add(Theme(R.drawable.counter_pink_tayor,
            R.drawable.img,R.drawable.button_selector_pink))

        themeList.add(Theme(R.drawable.counter_light_green_tayor,
            R.drawable.img_2,R.drawable.button_selector_light_green))


        themeList.add(Theme(R.drawable.counter_orenge_tayor,
            R.drawable.img_1,R.drawable.button_selector_orenge))


        themeList.add(Theme(R.drawable.counter_grey_tayor,
            R.drawable.img_5,R.drawable.button_selector_grey))


        themeList.add(Theme(R.drawable.counter_light_blue_tayor,
            R.drawable.img_4,R.drawable.button_selector_light_blue))



        themeList.add(Theme(R.drawable.counter_yellow_tayor,
            R.drawable.img_12,R.drawable.button_selector_yellow))




        themeList.add(Theme(R.drawable.counter_green_tayor,
            R.drawable.img_3,R.drawable.button_selector_green))



        themeList.add(Theme(R.drawable.counter_purple_tayor,
            R.drawable.img_10,R.drawable.button_selector_purple))

        themeList.add(Theme(R.drawable.counter_blue_tayor,
            R.drawable.img_6,R.drawable.button_selector_blue))




    }

    override fun itemClick(theme: Theme, position: Int) {
        // Theme'ni saqlash
        com.ismoil.tasbeh.utils.ThemeUtils.saveTheme(requireContext(), theme)

        // Foydalanuvchiga bildirishnoma
        android.widget.Toast.makeText(requireContext(), "Mavzu saqlandi!", android.widget.Toast.LENGTH_SHORT).show()

        // Fragment yoki Activity qayta chizilishini xohlasangiz:
        // requireActivity().recreate()

        // Orqaga qaytish
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }


}