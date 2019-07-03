package ru.skillbranch.devintensive.models

class UserView (
    val id:String,
    val fullName:String,
    val nickName:String,
    var avatar:String? = null,
    var status:String = "offline",
    val initials:String?
){
    fun printUserView() = println("""
${"\n"}
Users info:
id - ${this.id}
fullName - ${this.fullName}
nickName - ${this.nickName}
avatar - ${this.avatar}
status - ${this.status}
initials - ${this.initials}
        """.trimIndent())
}