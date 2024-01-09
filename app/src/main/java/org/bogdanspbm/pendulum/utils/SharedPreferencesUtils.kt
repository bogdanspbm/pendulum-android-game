package org.bogdanspbm.pendulum.utils

import android.content.Context

fun getGameRecord(context: Context): Int {
    val sharedPreferences = context.getSharedPreferences("statistics", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("game_record", 0)
}

fun saveGameRecord(context: Context, record:Int) {
    val sharedPreferences = context.getSharedPreferences("statistics", Context.MODE_PRIVATE)
    sharedPreferences.edit().putInt("game_record", record).apply()
}