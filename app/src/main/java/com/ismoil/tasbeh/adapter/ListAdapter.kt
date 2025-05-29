package com.ismoil.tasbeh.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ismoil.tasbeh.MainActivity
import com.ismoil.tasbeh.databinding.ItemZikrBinding
import com.ismoil.tasbeh.room.entity.Zikr

class ListAdapter(var list: List<Zikr>, var callback:CallBack): RecyclerView.Adapter<ListAdapter.Vh>() {

    inner class Vh(var binding:ItemZikrBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun  onBind(zikr : Zikr, position: Int){
            binding.apply {
                textTime.text = zikr.date
                textCounter.text = zikr.currentCount.toString()
                textZikr.text = zikr.zirk.zikrName

                tvProsent.text = zikr.countPresent.toString()+"%"

                //todo bu prosentni hisoblash kerak  bu yerda countni o'zi
                seekBar.max = zikr.maxCount
                seekBar.progress = zikr.currentCount

                binding.btnDelete.setOnClickListener {
                    callback.itemDelete(zikr, position)
                }
            }

        }
    }

    public interface CallBack{
        fun itemDelete(zikr: Zikr, position: Int)
//        fun  itemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

        return Vh(ItemZikrBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }
}


