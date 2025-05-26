package com.ismoil.tasbeh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismoil.tasbeh.databinding.ItemZikrBinding
import com.ismoil.tasbeh.room.entity.Zikr

class ListAdapter(var list: List<Zikr>, var callback:CallBack): RecyclerView.Adapter<ListAdapter.Vh>() {

    inner class Vh(var binding:ItemZikrBinding): RecyclerView.ViewHolder(binding.root){

        fun  onBind(zikr : Zikr, position: Int){
            binding.apply {
                textTime.text = zikr.date
                textCounter.text = zikr.countZikr.toString()
                textZikr.text = zikr.zirk.zikrName
                //todo bu prosentni hisoblash kerak  bu yerda countni o'zi
                tvProsent.text = zikr.countZikr.toString()
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


