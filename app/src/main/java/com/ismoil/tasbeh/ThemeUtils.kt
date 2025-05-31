// ThemeUtils.kt
package com.ismoil.tasbeh.utils

import android.content.Context
import com.ismoil.tasbeh.R
import com.ismoil.tasbeh.model.Theme

object ThemeUtils {

    fun saveTheme(context: Context, theme: Theme) {
        val shared = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        shared.edit().apply {
            putInt("backgroundImage", theme.backgroundImage)
            putInt("counterImage", theme.counterImage) // allaqachon tayyor versiyasi
            putInt("buttonBg", theme.buttonBg)
            apply()
        }
    }

    fun loadTheme(context: Context): Theme {
        val shared = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        return Theme(
            backgroundImage = shared.getInt("backgroundImage", R.drawable.img_7),
            counterImage = shared.getInt("counterImage", R.drawable.counter__1_),
            buttonBg = shared.getInt("buttonBg", R.drawable.button_selector)
        )
    }
}
