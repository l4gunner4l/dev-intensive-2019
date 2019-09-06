package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.data.CacheManager
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
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
        //TODO
        return 7
    }

    private fun lastMessageDate(): Date? {
        //TODO
        return Date()
    }

    private fun lastMessageShort(): Pair<String, String> {
        //TODO
        return "No messages" to "Nikola"
    }
    private fun isSingle() = members.size == 1

    enum class ChatType{
        SINGLE,
        GROUP,
        ARCHIVE
    }

}


