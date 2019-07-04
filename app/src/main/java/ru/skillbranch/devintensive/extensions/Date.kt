package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.humanizeDiff(): String {
    var interval = Date().time - this.time
    var result = ""
    val isPast:Boolean
    if (interval < 0) {interval *= -1; interval += 20; isPast = false} else isPast = true
    result += when {
            interval < SECOND -> "только что"
            interval < 45 * SECOND && interval >= SECOND -> "несколько секунд"
            interval < 75 * SECOND && interval >= 45 * SECOND -> "минуту"
            interval < 45 * MINUTE && interval >= 75 * SECOND -> TimeUnits.MINUTE.plural((interval/MINUTE).toInt())
            interval < 75 * MINUTE && interval >= 45 * MINUTE -> "час"
            interval < 22 * HOUR && interval >= 75 * MINUTE -> TimeUnits.HOUR.plural((interval/HOUR).toInt())
            interval < 26 * HOUR && interval >= 22 * HOUR -> "день"
            interval < 360 * DAY && interval >= 26 * HOUR -> TimeUnits.DAY.plural((interval/DAY).toInt())
            else -> return if (isPast) "более года назад" else "более чем через год"
    }
    return if (isPast) "$result назад" else "через $result"

}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits{
    SECOND, MINUTE, HOUR, DAY;
    fun plural(value: Int):String{
        return when (this) {

            SECOND -> "$value ${when {
                value <2 -> "секунду"
                value in 2..4 -> "секунды"
                else -> "секунд"}}"

            MINUTE -> "$value ${when {
                value <2 -> "минуту"
                value in 2..4 -> "минуты"
                else -> "минут"}}"

            HOUR -> "$value ${when {
                value <2 -> "час"
                value in 2..4 -> "часа"
                else -> "часов"}}"

            DAY -> "$value ${when {
                value <2 -> "день"
                value in 2..4 -> "дня"
                else -> "дней"}}"
        }
    }
}