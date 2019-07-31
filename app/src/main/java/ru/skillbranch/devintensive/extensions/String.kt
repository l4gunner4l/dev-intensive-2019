package ru.skillbranch.devintensive.extensions

fun String.truncate(length:Int = 16): String{
    val firstString = this.trim()
    return if (length < firstString.length) "${this.substring(0, length).trimEnd()}..."
           else firstString
}

fun String.stripHtml():String{
    return this.trim()
        .replace("<.*?>".toRegex(),"")
        .replace("[&<>'\"]".toRegex(),"")
        .replace("\\s+".toRegex(), " ")
}

private val exceptions = setOf("enterprise", "features", "topics", "collections", "trending", "events", "marketplace",
                               "pricing", "nonprofit", "customer-stories", "security", "login", "join")

fun String.isValidGitHub():Boolean {

    return """(https://)?(www.)?github.com/(\w*[^/])""".toRegex().matches(this)
}