package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.utils.Utils
import java.util.*
import java.util.concurrent.TimeUnit

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
        println(Date().time - Date().add(-1, TimeUnits.SECOND).time)
    }

    @Test
    fun test_git(){
        println("Amazing_Petr" + "- " + Utils.transliteration("Amazing Петр","_"))

        println("iVan     Stereotizhov" + "- " + Utils.transliteration("иВан     Стереотижов"))
        println("iVan Stereotizhov" + "- " + Utils.transliteration("иВан Стереотижов"))
        println("Amazing_PeZhr" + "- " + Utils.transliteration("Amazing ПеЖр", "_"))
        println("aAbBvVgGdDeEeEzhZhzZiIiIkKlL" + "- " + Utils.transliteration("аАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛ"))
        println("mMnNoOpPrRsStTuUfFhHcCshShsh'Sh'" + "- " + Utils.transliteration("мМнНоОпПрРсСтТуУфФхХцЦшШщЩ"))
        println("Zhizha ZhiZhnaYa" + "- " + Utils.transliteration("Жижа ЖиЖнаЯ"))
        println("Sobaka is a dog" + "- " + Utils.transliteration("Собака dog", " is a "))
    }
}
