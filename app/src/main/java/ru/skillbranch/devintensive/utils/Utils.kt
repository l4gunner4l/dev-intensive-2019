package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?>{
        if (fullName != null && !fullName.isEmpty()) {
            val fullNameList: List<String> = fullName.trim().split(" ")

            val firstName: String? = fullNameList.getOrNull(0)
            val lastName: String? = fullNameList.getOrNull(1)

            return when {
                firstName=="" -> Pair(null, lastName)
                lastName=="" -> Pair(firstName, null)
                else -> Pair(firstName, lastName)
            }

        }
        return Pair(null, null)
    }

    fun toInitials(firstName:String?, lastName:String?):String?{
        return if (firstName == null || firstName.trim() == ""){
            if (lastName == null || lastName.trim() == "") null
            else "${lastName.trim()[0].toUpperCase()}"
        } else {
            if (lastName == null || lastName.trim() == "") "${firstName!!.trim()[0].toUpperCase()}"
            else "${firstName.trim()[0].toUpperCase()}${lastName.trim()[0].toUpperCase()}"
        }
    }

    fun transliteration(fullName: String, divider:String = "."): String {
        return ""
    }
}