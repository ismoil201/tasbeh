package com.ismoil.tasbeh

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ismoil.tasbeh.adapter.ListAdapter
import com.ismoil.tasbeh.room.AppDataBase
import com.ismoil.tasbeh.room.entity.Zikr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuccseccFragment : Fragment() , ListAdapter.CallBack{

    private var database: AppDataBase? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter
    private val successList = mutableListOf<Zikr>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        database = AppDataBase.getInstance(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_succsecc, container, false)
        recyclerView = view.findViewById(R.id.rvSuccess)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListAdapter(successList,this)

        recyclerView.adapter = adapter

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
