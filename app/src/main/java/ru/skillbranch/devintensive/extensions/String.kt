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

fun String.isValidate():Boolean {
    return """\D+""".toRegex().matches(this)
}