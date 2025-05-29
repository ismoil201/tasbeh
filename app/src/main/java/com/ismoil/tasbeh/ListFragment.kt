package com.ismoil.tasbeh

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.ismoil.tasbeh.adapter.ListAdapter
import com.ismoil.tasbeh.databinding.AddZikrDialogBinding
import com.ismoil.tasbeh.databinding.FragmentListBinding
import com.ismoil.tasbeh.model.Zikrlar
import com.ismoil.tasbeh.room.AppDataBase
import com.ismoil.tasbeh.room.entity.Zikr
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class ListFragment : Fragment(),ListAdapter.CallBack {


    private  var _binding : FragmentListBinding ? = null
    private val binding get() = _binding!!
    private  lateinit var listAdapter:ListAdapter
    private var database:AppDataBase ?= null
    private var zikrList = mutableListOf<Zikrlar>() // <- bu Zikrlar ro'yxati spinnerda chiqishi uchun
    private  var zikrs = mutableListOf<Zikr>()


    override fun onAttach(context: Context) {
        super.onAttach(context)

        database = AppDataBase.getInstance(context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentListBinding.inflate(inflater,container,false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addZikrList()




        if(zikrs.isEmpty()){
            database?.zikrDao()?.getZikrs()?.let { allZikrs ->
                zikrs.addAll(allZikrs.filter { !it.succsecc })
            }
        }else{
            zikrs.clear()
            database?.zikrDao()?.getZikrs()?.let { allZikrs ->
                zikrs.addAll(allZikrs.filter { !it.succsecc })
            }
        }

        listAdapter = ListAdapter(zikrs,this)

        addZikr( listAdapter )


        binding.rvZikr.adapter = listAdapter


    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private  fun addZikrList(){
        zikrList.add(Zikrlar("Subhanalloh – سبحان الله" ,
            "\uD83D\uDD4B Zikrlar (Allohni yod etish)","Ma’nosi: Alloh barcha nuqsonlardan pokdir."))
        zikrList.add(Zikrlar("Alhamdulillah – الحمد لله", "",
            "Ma’nosi: Barcha hamdu sano Allohgadir."))
        zikrList.add(Zikrlar("Allohu Akbar – الله أكبر","",
            "Ma’nosi: Alloh buyukdir."))
        zikrList.add(Zikrlar("La ilaha illalloh – لا إله إلا الله","",
            "Ma’nosi: Allohdan o‘zga sig‘inilishga loyiq iloh yo‘q."))
        zikrList.add(Zikrlar("La hawla wa la quwwata illa billah – لا حول ولاقوة إلا بالله","",
            "Ma’nosi: Hech qanday kuch va qudrat yo‘q, faqat Allohning yordamidangina."))

        zikrList.add(Zikrlar("Astaghfirulloh – أستغفر الله","\uD83C\uDF27\uFE0F Istig‘for (Gunohlardan mag‘firat so‘rash)",
            "Ma’nosi: Allohdan mag‘firat so‘rayman."))
        zikrList.add(Zikrlar("Subhanaaka va bihamdika astag'firuka va atuvbu ilayk",""
        ,"Ma'nosi: Ya Alloh! Sen har qanday nuqsondan Poksan, hamdu sanoga faqat o'zing loyiqsan." +
                    " Senga istig'vor aytaman, tavba qilaman!"))
        zikrList.add(Zikrlar("G'ufronaka ","","Ma'nosi: Ya Alloh! gunovlarimni kechir!"))
        zikrList.add(Zikrlar("Astag'firullohallaziy laa illaha illa huval hayyul qoyyum, atuvbu ilayx",
            "","Ma'nosi: Undan O'zga iloh yo'q, doim tirik, har narsaga guvoh bo'luvchi" +
                    " Allohga istig'vor aytaman, tavba qilaman!"))
        zikrList.add(Zikrlar("Astag'firulloha va atuvbu ilayh","",
        "Ma'nosi: Allohdan kechirim so'rayman, unga tavba qilaman!"))
        zikrList.add(Zikrlar("Allohumma solli ‘ala sayyidina Muhammadinin-nabiyyil-ummiyyi va ‘ala alihi va sahbihi va sallim.",
            "Salavot✨",""))
        zikrList.add(Zikrlar("Allahumma salli 'ala Muhammadin wa 'ala aali Muhammadin.",""
        ,"Ey Alloh! Muhammadga va Uning oilasiga salovot ayla."))

    }
    private fun initZikr(binding: AddZikrDialogBinding) {
        val spinnerAdapter = object : ArrayAdapter<Zikrlar>(
            requireContext(),
            R.layout.spinner_item,
            zikrList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return createItemView(position, convertView, parent)
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                return createItemView(position, convertView, parent)
            }

            private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.spinner_item, parent, false)

                val zikr = getItem(position)
                view.findViewById<TextView>(R.id.tvZikrName).text = zikr?.zikrName
                view.findViewById<TextView>(R.id.tvZikrMeaning).text = zikr?.infoZikr
                return view
            }
        }

        binding.spinnerZikr.adapter = spinnerAdapter

        binding.spinnerZikr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedObject = parent?.getItemAtPosition(position) as? Zikrlar
                selectedObject?.let {
                    Log.d("Zikr", "Tanlangan zikr: ${it.zikrName}")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


//private fun initZikr(binding: AddZikrDialogBinding) {
  //  val adapter = ArrayAdapter(
//        requireContext(),
//        android.R.layout.simple_spinner_item,
//        zikrList.map { it.zikrName }  // Faqat nomlarini ko‘rsatamiz
//    ).also {
//        it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//    }
//
//    binding.spinnerZikr.adapter = adapter
//
//    binding.spinnerZikr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//          val selectedObject = parent?.getItemAtPosition(position) as? Zikrlar
//                selectedObject?.let {
//                    Log.d("Zikr", "Tanlangan zikr: ${it.zikrName}")
//                }
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>?) {}
//    }
//}


    private fun addZikr(adapter: ListAdapter) {
        binding.btnAddZikr.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val addDialogBinding = AddZikrDialogBinding.inflate(LayoutInflater.from(context))
            dialog.setView(addDialogBinding.root)

            addDialogBinding.apply {
                initZikr(addDialogBinding)

                btnSave.setOnClickListener {
                    val maxCountText = etCountMax.text.toString()
                    if (maxCountText.isNotEmpty()) {
                        val maxCount = maxCountText.toInt()
                        val zikrSelected = spinnerZikr.selectedItem as Zikrlar

                        if (maxCount != 0) {
                            val date = getCurrentDateTime()
                            val dateInString = date.toString("MM/dd HH:mm")

                            val newZikr = Zikr(
                                zirk = zikrSelected,
                                maxCount = maxCount,
                                date = dateInString,
                                countPresent = 0,
                                currentCount = 0,
                                succsecc = false
                            )

                            // Coroutine orqali fon oqimida bazaga yozish
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    database?.zikrDao()?.addZikr(newZikr)
                                }

                                // Bazaga qo‘shilgandan so'ng, listga qo‘shamiz va adapterni yangilaymiz (main threadda)
                                zikrs.add(newZikr)

                                adapter.notifyItemInserted(zikrs.size - 1)
                                dialog.dismiss()
                            }

                        } else {
                            Toast.makeText(requireContext(), "Maydonlarni to'ldiring❗️", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Maydonlarni to'ldiring❗️", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            dialog.show()
        }
    }


    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
    override fun itemDelete(zikr: Zikr, position: Int) {
        lifecycleScope.launch {
            // Avval bazadan o'chiramiz
            withContext(Dispatchers.IO) {
                database?.zikrDao()?.deleteZikr(zikr)
            }

            // Keyin UI list va adapterni yangilaymiz — bu Main threadda
            zikrs.removeAt(position)
            listAdapter.notifyItemRemoved(position)
            listAdapter.notifyItemRangeChanged(position, zikrs.size)
            Toast.makeText(requireContext(), "Zikr o‘chirildi", Toast.LENGTH_SHORT).show()

        }
    }

}