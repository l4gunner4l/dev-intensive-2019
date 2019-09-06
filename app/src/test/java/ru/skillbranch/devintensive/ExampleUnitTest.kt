package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*

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
        println("валидно - " + "https://github.com/johnDoe".isValidGitHubUrl())
        println("валидно - " + "https://www.github.com/johnDoe".isValidGitHubUrl())
        println("валидно - " + "www.github.com/johnDoe".isValidGitHubUrl())
        println("валидно - " + "github.com/johnDoe".isValidGitHubUrl())
        println("валидно - " + "github.com/john-Doe".isValidGitHubUrl())
        println("валидно - " + "github.com/ds-fd".isValidGitHubUrl())
        println("невалидно - " + "github.com/johnDo-e".isValidGitHubUrl())
        println("невалидно - " + "github.com/johnDo-".isValidGitHubUrl())
        println("невалидно - " + "https://anyDomain.github.com/johnDoe".isValidGitHubUrl())
        println("невалидно - " + "https://github.com/".isValidGitHubUrl())
        println("невалидно - " + "https://github.com".isValidGitHubUrl())
        println("невалидно - " + "https://github.com/johnDoe/tree".isValidGitHubUrl())
        println("невалидно - " + "https://github.com/johnDoe/tree/something".isValidGitHubUrl())
        println("невалидно - " + "https://github.com/enterprise".isValidGitHubUrl())
        println("невалидно - " + "https://github.com/pricing".isValidGitHubUrl())
        println("невалидно - " + "https://github.com/join".isValidGitHubUrl())
    }



}
