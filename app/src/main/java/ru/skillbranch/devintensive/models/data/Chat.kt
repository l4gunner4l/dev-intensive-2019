package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.data.CacheManager
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.utils.DataGenerator
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {

    fun toChatItem(): ChatItem {

        return if (isSingle()){
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName?:""} ${user.lastName?:""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )
        } else {
            ChatItem(
                id,
                null,
                Utils.toInitials(title, "") ?: "??",
                title,
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                ChatType.GROUP,
                lastMessageShort().second
            )
        }

    }

    fun unreadableMessageCount(): Int {
        return 7
    }

    private fun lastMessageDate(): Date? {
        return messages.last().date
    }

    /*private fun lastMessageShort(): Pair<String, String> {
        val lastFrom = messages.last().from?.firstName ?: "-"
        val lastMsg= if (messages.lastOrNull() is TextMessage) messages.last().text
        else messages.last().image
        return lastMsg to lastFrom
    }*/
    private fun lastMessageShort(): Pair<String, String> = "msg" to "from me"

    private fun isSingle() = members.size == 1

    enum class ChatType{
        SINGLE,
        GROUP,
        ARCHIVE
    }

}


