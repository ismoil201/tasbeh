package com.ismoil.tasbeh

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ismoil.tasbeh.databinding.DeleteDialogBinding
import com.ismoil.tasbeh.databinding.FragmentMainBinding
import com.ismoil.tasbeh.room.AppDataBase
import com.ismoil.tasbeh.room.entity.Zikr

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var database: AppDataBase? = null
    private lateinit var zikrs: MutableList<Zikr>
    private var currentZikr: Zikr? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        database = AppDataBase.getInstance(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadZikrs()

        binding.btnCounter.setOnClickListener {
            currentZikr?.let {
                if(!it.succsecc)
                clickMainButton(it) }
        }

        binding.btnReset.setOnClickListener {
            currentZikr?.let { showResetDialog(it) }
        }

        binding.btnStatistic.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_statisticFragment)
        }

        binding.btnList.setOnClickListener {


            findNavController().navigate(R.id.addNewFragment)
        }

        binding.btnTheme.setOnClickListener {
            Toast.makeText(requireContext(), "Hozir mavjud emas!!!", Toast.LENGTH_SHORT).show()
        }

        binding.btnProfile.setOnClickListener {
            val options = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
            findNavController().navigate(R.id.action_mainFragment_to_profileFragment,null, options)
        }
    }

    private fun loadZikrs() {
        zikrs = (database?.zikrDao()?.getZikrs() ?: emptyList()).toMutableList()

        // Filter: toping, succsecc = false bo'lgan birinchi zikrni
        currentZikr = zikrs.firstOrNull { !it.succsecc }

        currentZikr?.let { zikr ->
            binding.tvZikrNomi.text = zikr.zirk.zikrName
            binding.tvZikrMaxSon.text ="Bajarish: "+ zikr.maxCount.toString()
            binding.tvCount.text = zikr.currentCount.toString()
            binding.seekBar.progress = zikr.currentCount
            binding.seekBar.max = zikr.maxCount
        } ?: run {
            // Barcha zikrlar tugagan bo‘lsa
            binding.tvZikrNomi.text = "Zikr qo'shing"
            Toast.makeText(requireContext(), "Iltimos, list bo'limiga zikr qo'shing!!!", Toast.LENGTH_SHORT).show()

            binding.tvCount.text = "0"
            binding.tvZikrMaxSon.text ="0"
            binding.seekBar.progress = 0
        }
    }

//    private fun clickMainButton(zikr: Zikr) {
//        if (zikr.currentCount < zikr.maxCount) {
//            zikr.currentCount++
//
//            // Ovoz chalinadi
//            MediaPlayer.create(context, R.raw.button_1).apply {
//                start()
//                setOnCompletionListener { release() }
//            }
//
//            binding.tvCount.text = zikr.currentCount.toString()
//            binding.seekBar.progress = zikr.currentCount
//
//            // Tugadimi?
//            if (zikr.currentCount >= zikr.maxCount) {
//                zikr.succsecc = true
//            }
//            zikr.countPresent = ((zikr.currentCount.toFloat() / zikr.maxCount) * 100).toInt()
//
//            // Update DB
//            database?.zikrDao()?.updateZikr(zikr)
//        }else{
//
//        }
//    }

    private fun clickMainButton(zikr: Zikr) {
        if (zikr.currentCount < zikr.maxCount) {
            zikr.currentCount++

            // Ovoz chalinadi
            MediaPlayer.create(context, R.raw.button_1).apply {
                start()
                setOnCompletionListener { release() }
            }

            binding.tvCount.text = zikr.currentCount.toString()
            binding.seekBar.progress = zikr.currentCount

            // Tugadimi?
            if (zikr.currentCount >= zikr.maxCount) {
                zikr.succsecc = true
                zikr.countPresent = ((zikr.currentCount.toFloat() / zikr.maxCount) * 100).toInt()

                // DB ni yangilab, yangi Zikr ni yuklaymiz
                database?.zikrDao()?.updateZikr(zikr)
                loadZikrs() // 👈 yangi zikrni topadi va currentZikr ni yangilaydi
                return // oldingi currentZikr bilan davom etmaslik uchun
            }

            zikr.countPresent = ((zikr.currentCount.toFloat() / zikr.maxCount) * 100).toInt()

            // Update DB
            database?.zikrDao()?.updateZikr(zikr)
        }
    }


    private fun showResetDialog(zikr: Zikr) {
        val deleteDialogBinding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setView(deleteDialogBinding.root)

        deleteDialogBinding.apply {
            btnDelete.setOnClickListener {
                zikr.currentCount = 0
                zikr.succsecc = false

                binding.tvCount.text = "0"
                binding.seekBar.progress = 0

                database?.zikrDao()?.updateZikr(zikr)
                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





//todo
//class MainFragment : Fragment() {
//
//    private var _binding: FragmentMainBinding? = null
//    private val binding get() = _binding!!
//    private var count: Int = 0
//    private var database: AppDataBase ?= null
//    private var zikrs =mutableListOf<Zikr>()
//
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        database = AppDataBase.getInstance(context)
//    }
//
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentMainBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        if (zikrs.isEmpty()){
//            database?.zikrDao()?.getZikrs()?.let { zikrs.addAll(it) }
//        }else{
//            zikrs.clear()
//            database?.zikrDao()?.getZikrs()?.let { zikrs.addAll(it) }
//        }
//
//        zikrs.let { zikr ->
//
//            if (zikr.filter { zikr.first().succsecc != true}){
//                binding.seekBar.progress = zikr.first().currentCount
//                binding.seekBar.max = zikr.first().maxCount
//
//            }else{
//                zikr.first().succsecc = true
//            }
//
//        }
//
//
//        binding.btnCounter.setOnClickListener {
//            clickMainButton(zikrs.filter {zikr.first().succsecc != true})
//        }
//
//        count = binding.tvCount.text.toString().toIntOrNull() ?: 0
//
//
//        binding.btnReset.setOnClickListener { deleteCount() }
//
//        binding.btnStatistic.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_statisticFragment)
//
//        }
//
//        binding.btnTheme.setOnClickListener {
//        }
//
//
//        binding.btnList.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_listFragment)
//
//        }
//    }
//
//    private fun clickMainButton(zikr: Zikr){
//
//        if (zikr.maxCount != zikr.currentCount){
//            zikr.currentCount++
//            val mediaPlayer = MediaPlayer.create(context, R.raw.button_1)
//            mediaPlayer.start()
//
//            binding.tvCount.text = zikr.currentCount.toString()
//        }
//
//
//
//    }
//
//
//    private fun deleteCount(zikr: Zikr){
//        val deleteDialogBinding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
//        val dialog = AlertDialog.Builder(requireContext()).create()
//        dialog.setView(deleteDialogBinding.root)
//
//        deleteDialogBinding.apply {
//
//            btnDelete.setOnClickListener {
//                //todo update zikrdao
//               count = 0
//                binding.tvCount.text = zikr.countZikr.toString()
//
//                dialog.dismiss()
//            }
//            btnCancel.setOnClickListener {
//                dialog.dismiss()
//
//            }
//            dialog.show()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//}