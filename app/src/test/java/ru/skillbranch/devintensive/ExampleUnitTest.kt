package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_fun(){

        println(TimeUnits.SECOND.plural(1))
        println(TimeUnits.SECOND.plural(4))
        println(TimeUnits.SECOND.plural(19))
        println(TimeUnits.SECOND.plural(21))
        println(TimeUnits.SECOND.plural(24))
        println(TimeUnits.SECOND.plural(222))

        println(TimeUnits.MINUTE.plural(1))
        println(TimeUnits.MINUTE.plural(4))
        println(TimeUnits.MINUTE.plural(19))
        println(TimeUnits.MINUTE.plural(21))
        println(TimeUnits.MINUTE.plural(24))
        println(TimeUnits.MINUTE.plural(222))

        println(TimeUnits.DAY.plural(1))
        println(TimeUnits.DAY.plural(4))
        println(TimeUnits.DAY.plural(19))
        println(TimeUnits.DAY.plural(21))
        println(TimeUnits.DAY.plural(24))
        println(TimeUnits.DAY.plural(222))

        println(TimeUnits.HOUR.plural(1))
        println(TimeUnits.HOUR.plural(4))
        println(TimeUnits.HOUR.plural(19))
        println(TimeUnits.HOUR.plural(21))
        println(TimeUnits.HOUR.plural(24))
        println(TimeUnits.HOUR.plural(222))
    }
}
