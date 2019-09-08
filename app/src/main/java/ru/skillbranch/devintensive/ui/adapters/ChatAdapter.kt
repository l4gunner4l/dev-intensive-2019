package ru.skillbranch.devintensive.ui.adapters

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_archive.*
import kotlinx.android.synthetic.main.item_chat_group.*
import kotlinx.android.synthetic.main.item_chat_single.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.TextDrawable
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.ui.archive.ArchiveActivity

class ChatAdapter(val listener : (ChatItem)->Unit) : RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder>() {
    var items : List<ChatItem> = listOf()

    override fun getItemViewType(position: Int): Int {
        return when(items[position].chatType) {
            ChatType.SINGLE -> SINGLE_TYPE
            ChatType.ARCHIVE -> ARCHIVE_TYPE
            ChatType.GROUP -> GROUP_TYPE
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType){
            SINGLE_TYPE -> SingleViewHolder(inflater.inflate(R.layout.item_chat_single, parent, false))
            GROUP_TYPE -> GroupViewHolder(inflater.inflate(R.layout.item_chat_group, parent, false))
            ARCHIVE_TYPE -> ArchiveViewHolder(inflater.inflate(R.layout.item_chat_archive, parent, false))
            else -> throw RuntimeException("There is no type $viewType")
        }

    }

    override fun getItemCount() = items.size// + if (ChatRepository.getArchiveChatsCount()>0) 1 else 0

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        Log.d("M_ChatAdapter", "onBindViewHolder() item id="+items[position].id)
        if (holder is ArchiveViewHolder)
            Log.d("M_ChatAdapter","holder is ArchiveViewHolder, position=$position")
        holder.bind(items[position], listener)
    }

    fun updateData(data: List<ChatItem>){
        Log.d("M_ChatAdapter","update data adapter - new data ${data.size} hash: ${data.hashCode()}" +
                " old data ${items.size} hash: ${items.hashCode()}")
        val diffCallback = object  : DiffUtil.Callback(){
            override fun areItemsTheSame(oldPos: Int, newPos: Int) = items[oldPos].id == data[newPos].id
            override fun areContentsTheSame(oldPos: Int, newPos: Int) = items[oldPos].hashCode() == data[newPos].hashCode()
            override fun getOldListSize() = items.size// + if (archivedChats>0) 1 else 0
            override fun getNewListSize() = data.size

        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        Log.d("M_ChatAdapter","${items.size} - items size")
        diffResult.dispatchUpdatesTo(this)
    }



    inner class SingleViewHolder(convertView: View)
        : ChatItemViewHolder(convertView), ItemTouchViewHolder {

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }

        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit){
            // ava
            if (item.avatar == null){
                Glide.with(itemView).clear(iv_avatar_single)
                iv_avatar_single.setImageDrawable(
                    TextDrawable
                        .builder()
                        .buildRound(item.initials, R.color.color_accent))
                iv_avatar_single.setupBitmap()
            } else {
                Glide.with(itemView).load(item.avatar).into(iv_avatar_single)
            }
            // online
            sv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE
            // date
            with(tv_date_single){
                visibility = if (item.lastMessageDate!=null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }
            // counter
            with(tv_counter_single){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }
            // title
            tv_title_single.text = item.title
            // message
            tv_message_single.text = item.shortDescription

            itemView.setOnClickListener{
                listener.invoke(item)
            }

        }

    }

    inner class ArchiveViewHolder(convertView: View)
        : ChatItemViewHolder(convertView) {

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ArchiveActivity::class.java)
                itemView.context.startActivity(intent)
            }

        }

        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
            tv_message_author_archive.text = item.author
            tv_message_archive.text = item.shortDescription

            with(tv_date_archive){
                visibility = if (item.lastMessageDate!=null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }
            with(tv_counter_archive){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }
        }

    }

    inner class GroupViewHolder(convertView: View)
        : ChatItemViewHolder(convertView), ItemTouchViewHolder {

        override fun onItemSelected() { itemView.setBackgroundColor(Color.LTGRAY) }

        override fun onItemCleared() { itemView.setBackgroundColor(Color.WHITE) }

        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit){
            // ava
            iv_avatar_group.setImageDrawable(
                TextDrawable
                    .builder()
                    .buildRound(item.initials, R.color.color_accent))
            iv_avatar_group.setupBitmap()
            // date
            with(tv_date_group){
                visibility = if (item.lastMessageDate!=null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }
            // counter
            with(tv_counter_group){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }
            // author
            with(tv_message_author_group){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = item.author
            }
            tv_title_group.text = item.title
            tv_message_group.text = item.shortDescription

            itemView.setOnClickListener{
                listener.invoke(item)
            }

        }

    }


    abstract inner class ChatItemViewHolder(convertView: View)
        : RecyclerView.ViewHolder(convertView), LayoutContainer {

        override val containerView: View?
            get() = itemView
        abstract fun bind(item: ChatItem, listener: (ChatItem) -> Unit)

    }


    companion object{
        private const val ARCHIVE_TYPE = 0
        private const val SINGLE_TYPE = 1
        private const val GROUP_TYPE = 2
    }


}