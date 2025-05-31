package com.ismoil.tasbeh.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ismoil.tasbeh.R
import com.ismoil.tasbeh.adapter.ListAdapter
import com.ismoil.tasbeh.databinding.FragmentSuccseccBinding
import com.ismoil.tasbeh.room.AppDataBase
import com.ismoil.tasbeh.room.entity.Zikr
import com.ismoil.tasbeh.utils.ThemeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuccseccFragment : Fragment() , ListAdapter.CallBack{

    private var database: AppDataBase? = null
    private lateinit var adapter: ListAdapter
    private val successList = mutableListOf<Zikr>()

    private lateinit var binding : FragmentSuccseccBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        database = AppDataBase.getInstance(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccseccBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val theme = ThemeUtils.loadTheme(requireContext())


        binding.root.setBackgroundResource(theme.backgroundImage)


        adapter = ListAdapter(successList, this)
        binding.rvSuccess.adapter = adapter

        loadSuccessZikrs()



    }

    private fun loadSuccessZikrs() {
        lifecycleScope.launch {
            val data = withContext(Dispatchers.IO) {
                database?.zikrDao()?.getZikrs()?.filter { it.succsecc }
            }

            data?.let {
                successList.clear()
                successList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun itemDelete(zikr: Zikr, position: Int) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database?.zikrDao()?.deleteZikr(zikr)
            }

            // UI update main thread
            successList.removeAt(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, successList.size)
            Toast.makeText(requireContext(), "Zikr o‘chirildi", Toast.LENGTH_SHORT).show()
        }
    }


}
