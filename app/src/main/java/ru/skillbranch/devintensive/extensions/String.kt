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

fun String.isValidGitHubUrl():Boolean {
    return if (this == "") true
    else if (!"""(https://)?(www.)?github.com/(\w*[^/])""".toRegex().matches(this)) false
    else this.getGitHubName() !in exceptions
}

private fun String.getGitHubName():String{
    for (i in (this.length-1) downTo 0) if (this[i]=='/') return this.takeLast(this.length - i - 1)
    return ""
}

private val exceptions = setOf("enterprise", "features", "topics", "collections", "trending", "events", "marketplace",
    "pricing", "nonprofit", "customer-stories", "security", "login", "join")
