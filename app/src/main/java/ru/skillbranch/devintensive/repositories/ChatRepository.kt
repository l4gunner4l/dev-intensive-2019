package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.CacheManager
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.Chat

object ChatRepository {
    private val chats = CacheManager.loadChats()

    fun loadChats() : MutableLiveData<List<Chat>> {
        return chats
    }

    fun update(chat: Chat) {
        val copy = chats.value!!.toMutableList()
        val index = chats.value!!.indexOfFirst { it.id == chat.id }
        if (index == -1) return
        copy[index] = chat
        chats.value = copy
    }

    fun find(chatId: String): Chat? {
        val index = chats.value!!.indexOfFirst { it.id == chatId }
        return chats.value!!.getOrNull(index)
    }


    fun getArchiveChatsCount(): Int {
        return chats.value!!.filter { it.isArchived }.size
    }

    fun getShortDescription(): String? {
        return chats.value!!.filter { it.isArchived }.lastOrNull()?.lastMessageShort()?.first ?: "message test"
    }
    fun getLastDate(): String? {
        return chats.value!!.filter { it.isArchived }.lastOrNull()?.lastMessageDate()?.shortFormat() ?: "date test"
    }

    fun getLastAuthor(): String? {
        return chats.value!!.filter { it.isArchived }.lastOrNull()?.lastMessageShort()?.second ?: "author test"
    }

    fun getArchiveUnreadMessageCount(): Int {
        return chats.value!!.filter { it.isArchived }.sumBy { it.unreadableMessageCount() }
    }
}