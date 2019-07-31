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
    fun test_fun() {
        println(" //валиден = " + "https://github.com/johnDoe".isValidGitHub())
        println(" //валиден = " + "https://www.github.com/johnDoe".isValidGitHub())
        println(" //валиден = " + "www.github.com/johnDoe".isValidGitHub())
        println(" //валиден = " + "github.com/johnDoe".isValidGitHub())
        println("")
        println(" //невалиден = " + "https://anyDomain.github.com/johnDoe".isValidGitHub())
        println(" //невалиден = " + "https://github.com/".isValidGitHub())
        println(" //невалиден = " + "https://github.com".isValidGitHub())
        println(" //невалиден = " + "https://github.com/johnDoe/tree".isValidGitHub())
        println(" //невалиден = " + "https://github.com/johnDoe/tree/something".isValidGitHub())
        println(" //невалиден = " + "https://github.com/enterprise".isValidGitHub())
        println(" //невалиден = " + "https://github.com/pricing".isValidGitHub())
        println(" //невалиден = " + "https://github.com/join".isValidGitHub())

    }
}
