package com.ismoil.tasbeh.model

 class Zikrlar(var zikrName: String, var theme:String,var infoZikr:String) {
    override fun toString(): String {
        return " $zikrName, Mavzu: $theme, Ma'lumot: $infoZikr"


    }
}