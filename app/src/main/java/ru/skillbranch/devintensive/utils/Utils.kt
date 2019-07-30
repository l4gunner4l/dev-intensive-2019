package ru.skillbranch.devintensive.utils

object Utils {

    //"""(https://)?(www.)?github.com/(\w*[^/])"""

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
            if (lastName == null || lastName.trim() == "") "${firstName.trim()[0].toUpperCase()}"
            else "${firstName.trim()[0].toUpperCase()}${lastName.trim()[0].toUpperCase()}"
        }
    }

    fun transliteration(fullName: String, divider:String = " "): String {
        var result = ""
        for (letterInName in fullName.trim()) {
            if (letterInName.equals(' ')) result += divider
            else result += letters[letterInName] ?: letterInName
        }
        return result
    }

    private val letters: Map<Char, String> = mapOf(
        'а' to "a", 'А' to "A",
        'б' to "b", 'Б' to "B",
        'в' to "v", 'В' to "V",
        'г' to "g", 'Г' to "G",
        'д' to "d", 'Д' to "D",
        'е' to "e", 'Е' to "E",
        'ё' to "e", 'Ё' to "E",
        'ж' to "zh",'Ж' to "Zh",
        'з' to "z", 'З' to "Z",
        'и' to "i", 'И' to "I",
        'й' to "i", 'Й' to "I",
        'к' to "k", 'К' to "K",
        'л' to "l", 'Л' to "L",
        'м' to "m", 'М' to "M",
        'н' to "n", 'Н' to "N",
        'о' to "o", 'О' to "O",
        'п' to "p", 'П' to "P",
        'р' to "r", 'Р' to "R",
        'с' to "s", 'С' to "S",
        'т' to "t", 'Т' to "T",
        'у' to "u", 'У' to "U",
        'ф' to "f", 'Ф' to "F",
        'х' to "h", 'Х' to "H",
        'ц' to "c", 'Ц' to "C",
        'ч' to "ch", 'Ч' to "Ch",
        'ш' to "sh", 'Ш' to "Sh",
        'щ' to "sh'", 'Щ' to "Sh'",
        'ъ' to "", 'Ъ' to "",
        'ы' to "i", 'Ы' to "I",
        'ь' to "", 'Ь' to "",
        'э' to "e", 'Э' to "E",
        'ю' to "yu", 'Ю' to "Yu",
        'я' to "ya", 'Я' to "Ya"
        )

}