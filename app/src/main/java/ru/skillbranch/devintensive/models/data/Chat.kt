package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
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

    private fun unreadableMessageCount() = messages.count { !it.isReaded }

    private fun lastMessageDate(): Date? {
        return messages.lastOrNull()?.date
    }

    private fun lastMessageShort(): Pair<String, String> {
        val lastMsg = messages.lastOrNull() ?: return "Сообщений пока нет" to ""
        val lastFrom = lastMsg.from.firstName ?: "error0"
        val lastMsgText =
        when (lastMsg) {
            is TextMessage -> lastMsg.text ?: "error2"
            is ImageMessage -> "$lastFrom - отправил фото"
            else -> "error4"
        }
        return lastMsgText to lastFrom
    }

    private fun isSingle() = members.size == 1

    enum class ChatType{
        SINGLE,
        GROUP,
        ARCHIVE
    }

}


