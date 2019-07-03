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
    if (interval > 0){
        return when {
            interval < SECOND -> "только что"
            interval < 45 * SECOND && interval >= SECOND -> "несколько секунд назад"
            interval < 75 * SECOND && interval >= 45 * SECOND -> "минуту назад"
            interval < 45 * MINUTE && interval >= 75 * SECOND -> when { interval/MINUTE<2 -> "${interval/MINUTE} минуту назад"
                                                                        interval/MINUTE in 2..4 -> "${interval/MINUTE} минуты назад"
                                                                        else -> "${interval/MINUTE} минуты назад" }
            interval < 75 * MINUTE && interval >= 45 * MINUTE -> "час назад"
            interval < 22 * HOUR && interval >= 75 * MINUTE -> when { interval/HOUR<2 -> "${interval/HOUR} час назад"
                                                                      interval/HOUR in 2..4 -> "${interval/HOUR} часа назад"
                                                                      else -> "${interval/HOUR} часов назад" }
            interval < 26 * HOUR && interval >= 22 * HOUR -> "день назад"
            interval < 360 * DAY && interval >= 26 * HOUR -> when { interval/DAY<2 -> "${interval/DAY} день назад"
                                                                    interval/DAY in 2..4 -> "${interval/DAY} дня назад"
                                                                    else -> "${interval/DAY} дней назад" }
            else -> "более года назад"
        }
    } else {
        interval = this.time - Date().time + 20
        return when {
            interval < SECOND -> "только что"
            interval < 45 * SECOND && interval >= SECOND -> "через несколько секунд"
            interval < 75 * SECOND && interval >= 45 * SECOND -> "через минуту"
            interval < 45 * MINUTE && interval >= 75 * SECOND -> when { interval/MINUTE<2 -> "через ${interval/MINUTE} минуту"
                                                                        interval/MINUTE in 2..4 -> "через ${interval/MINUTE} минуты"
                                                                        else -> "через ${interval/MINUTE} минуты" }
            interval < 75 * MINUTE && interval >= 45 * MINUTE -> "через час"
            interval < 22 * HOUR && interval >= 75 * MINUTE -> when { interval/HOUR<2 -> "через ${interval/HOUR} час"
                                                                      interval/HOUR in 2..4 -> "через ${interval/HOUR} часа"
                                                                      else -> "через ${interval/HOUR} часов" }
            interval < 26 * HOUR && interval >= 22 * HOUR -> "через день"
            interval < 360 * DAY && interval >= 26 * HOUR -> when { interval/DAY<2 -> "через ($interval/$DAY) ${interval/DAY} день"
                                                                    interval/DAY in 2..4 -> "через ${interval/DAY} дня"
                                                                    else -> "через ${interval/DAY} дней" }
            else -> "более чем через год"
        }
    }

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
    SECOND, MINUTE, HOUR, DAY
}