package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User constructor(
    val id:String,
    var firstName:String?,
    var lastName:String?,
    var avatar:String?,
    var rating:Int = 0,
    var respect:Int = 0,
    var lastVisit:Date? = Date(),
    var isOnline:Boolean = false
    ) {

    constructor(id: String, firstName:String?, lastName: String?) : this(id, firstName, lastName, null){
        /*some action*/
    }

    constructor(id: String) : this(id, "Ivan", "Ivanov", null)

    init {
        println("User ${this.firstName} had created.")
    }

    fun printUser() = println("""
${"\n"}
Users info:
id - ${this.id}
firstName - ${this.firstName}
lastName - ${this.lastName}
avatar - ${this.avatar}
rating - ${this.rating}
rating - ${this.respect}
lastVisit - ${this.lastVisit}
isOnline - ${this.isOnline}
        """.trimIndent())

    companion object Factory{
        private var lastId : Int = -1
        fun makeUser(fullName: String?): User?{
            lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User("$lastId", firstName, lastName)
        }
    }
}