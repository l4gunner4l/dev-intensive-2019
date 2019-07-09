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
        println("<p class=\"title\">Образовательное    IT-сообщество Skill    Branch</p>".stripHtml())
        //Образовательное IT-сообщество Skill Branch
        println("<p>Образовательное   &&&''    IT-сообщество Skill Branch</p>".stripHtml())
        //Образовательное IT-сообщество Skill Branch
    }
}
