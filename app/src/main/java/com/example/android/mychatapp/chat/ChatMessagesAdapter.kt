package com.example.android.mychatapp.chat

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mychatapp.R
import kotlinx.android.synthetic.main.item_message.view.*

class ChatMessagesAdapter(mContext: Activity):RecyclerView.Adapter<ChatMessagesAdapter.ChatMessagesViewHolder>() {
    class ChatMessagesViewHolder(v: View):RecyclerView.ViewHolder(v)

    val messages = mutableListOf<ChatFragment.UserWithMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message,parent,false)
        return ChatMessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatMessagesViewHolder, position: Int) {
        holder.itemView.apply {
            item_message_message_text.text = messages[position].message
            item_message_message_user_name.text = messages[position].user + ":"
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addUserWithMessage(userWithMessage: ChatFragment.UserWithMessage){
        messages.add(userWithMessage)
        notifyItemInserted(messages.lastIndex)
    }
}