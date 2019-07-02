package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils
import java.util.*



fun User.toUserView():UserView{
    val nickName = Utils.transliteration("$firstName $lastName")
    val status = when {
                isOnline -> "В сети"
                lastVisit == null -> "Еще ни разу не был"
                else -> "Последний раз был ${lastVisit!!.humanizeDiff(Date())}"
    }
    val initials = Utils.toInitials(firstName, lastName)

    return UserView(id, "$firstName $lastName", nickName, avatar, status, initials)
}

private fun Date.humanizeDiff(now:Date): String{
    return
}
