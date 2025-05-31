package com.ismoil.tasbeh.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.ismoil.tasbeh.databinding.ItemThemeBinding
import com.ismoil.tasbeh.model.Theme
import com.ismoil.tasbeh.room.entity.Zikr

class ThemeAdapter(var themeList: List<Theme>, var callback:CallBack): RecyclerView.Adapter<ThemeAdapter.Vh>() {

    inner class Vh(var binding: ItemThemeBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(theme: Theme, position: Int) {
            binding.apply {
                val context = root.context

                ivImageBg.setImageDrawable(ContextCompat.getDrawable(context, theme.backgroundImage))
                ivCounterImage.setImageDrawable(ContextCompat.getDrawable(context, theme.counterImage))
                vButtonBg.setBackgroundResource(theme.buttonBg)

                btnSave.setOnClickListener {
                    callback.itemClick(theme, position)
                }
            }
        }

    }

    public interface CallBack{
//        fun itemDelete(zikr: Zikr, position: Int)
        fun  itemClick(theme :Theme, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

        return Vh(ItemThemeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return  themeList.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(themeList[position],position)
    }
}


