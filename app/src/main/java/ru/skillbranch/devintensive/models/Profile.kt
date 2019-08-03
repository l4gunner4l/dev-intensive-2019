package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils

data class Profile(
    val firstName:String,
    val lastName:String,
    val about:String,
    val repository:String,
    val rating:Int = 0,
    val respect:Int = 0
) {
    val nickname:String = Utils.transliteration("$firstName $lastName", "_")
    val rank:String = "Junior Android Developer"
    val initials:String = Utils.toInitials(firstName, lastName) ?: ""

    fun toMap(): Map<String, Any> = mapOf(
        "nickname" to nickname,
        "rank" to rank,
        "initials" to initials,
        "firstName" to firstName,
        "lastName" to lastName,
        "about" to about,
        "repository" to repository,
        "rating" to rating,
        "respect" to respect
    )
}